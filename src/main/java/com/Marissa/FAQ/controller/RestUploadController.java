package com.Marissa.FAQ.controller;
//import com.fasterxml.classmate.TypeBindings.withAdditionalBinding;
import com.Marissa.FAQ.controller.vo.Params;
import com.Marissa.FAQ.repository.po.Doc;
import com.Marissa.FAQ.service.DocService;
import com.Marissa.FAQ.service.TagService;
import com.Marissa.FAQ.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
@Api(value = "文档操作",tags = "文档")
@RestController
public class RestUploadController {
    @Resource
    private DocService docService;

    @Resource
    private TagService tagService;

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    @PostMapping(value = "list/doc",produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取文档列表",notes = "获取文档列表")
    public ResponseEntity getPageList(@RequestBody String params){
        Params page = CommonUtils.transformToParams(params);
        if(page == null) return new ResponseEntity<>("error",HttpStatus.OK);
        Pageable pageable = new PageRequest(page.getPage(),page.getSize(), Sort.Direction.DESC,"id");
        List<Doc> list = docService.getPageList(pageable);
        return new ResponseEntity<>(list,HttpStatus.OK);
//        return new ResponseEntity<>(list == null?"暂无数据":list,HttpStatus.OK);
    }

    @PostMapping(value = "del/doc",produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除文档",notes = "删除文档")
    public ResponseEntity<?> deleteDoc(@RequestBody String params){
        Params params1 = CommonUtils.transformToParams(params);
        if(params1 == null) return new ResponseEntity<>("删除失败",HttpStatus.OK);
        docService.delete(params1.getId());
        return new ResponseEntity<>("删除成功",HttpStatus.OK);
    }
    // Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("files") MultipartFile[] files,
                                             @RequestParam("tags")String[] tags) {
        HttpStatus status = HttpStatus.OK;
        String msg = "";
        logger.debug("多个文件上传!");

        String uploadedFileName = Arrays.stream(files).map(
                x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x))
                .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) msg = "请选择上传文件!";
        int cnt = 0;
        for (int i = 0; i < files.length; i++) {
            try {
                // 上传文件到指定路径
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                Doc doc = new Doc(fileName,"empty now",tags[i], 0,
                        new Date(),new Date(),0,0,fileName.substring(','));
                List<String> names;

                if (file.isEmpty() || docService.checkExist(fileName) > 0) continue; //next pls

                byte[] bytes = file.getBytes();
                String filePath = getPathPrefix() + fileName;
                Files.write(Paths.get(filePath), bytes);
                logger.info("{0}filePath:  "+ Paths.get(filePath).toString());

                //将文件信息存入doc信息表中
                //String name, String content, String tags, int collectCnt, Date createTime, Date updateTime, int userId
                docService.save(doc);
                cnt++;

                //保存标签
                if(tags[i].indexOf(',') > -1){
                    String[] arrayStr = tags[i].split(",");
                    names = CommonUtils.convertToList(arrayStr);
                } else{
                    names = CommonUtils.convertToList(tags[i]);
                }
                tagService.save(names);

                //建立索引
                Doc doc1 = docService.getByName(file.getOriginalFilename());

                //将二进制流数据存入数据表中 id值 id 数字块 数据
            }
            catch (IOException e) {
                logger.error("上传第"+i+"个文件出错！");
            }
        }
        msg = "上传成功 - " + cnt + "个文件，上传失败 - " + (files.length - cnt) +"个文件 "+uploadedFileName;
        return new ResponseEntity<>(msg, status);
    }

    private String getPathPrefix(){
        try{
            File root = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!root.exists()) root = new File("");
            File uploadFile = new File(root.getAbsolutePath(),"static/upload/");
            if(!uploadFile.exists()) uploadFile.mkdirs();
            return uploadFile.getAbsolutePath() + File.separator;
        } catch (IOException e){
            logger.error("获取上传路径失败");
        }
        return null;
    }
}

/*
try {
String path1 = new DefaultResourceLoader().getResource("file:")
.getFile().getAbsolutePath() + File.separator +"upload"+ File.separator ;
LogUtil.info("path1: " + path1);
} catch (IOException e) {
e.printStackTrace();
}*/
