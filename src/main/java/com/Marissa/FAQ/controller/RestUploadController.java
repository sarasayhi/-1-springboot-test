package com.Marissa.FAQ.controller;

import com.Marissa.FAQ.controller.vo.Params;
import com.Marissa.FAQ.repository.po.Doc;
import com.Marissa.FAQ.service.DocService;
import com.alibaba.fastjson.JSON;
import com.mysql.fabric.xmlrpc.base.Param;
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
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
@Api(value = "文档操作",tags = "文档")
@RestController
public class RestUploadController {
    @Resource
    private DocService docService;

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    @PostMapping(value = "list/doc",produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取文档列表",notes = "获取文档列表")
    public ResponseEntity<List<Doc>> getPageList(@RequestBody String params){
        Params page = JSON.parseObject(params,Params.class);
        Pageable pageable = new PageRequest(page.getPage(),page.getSize(), Sort.Direction.DESC,"id");
        List<Doc> list = docService.getPageList(pageable);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping(value = "del/doc",produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除文档",notes = "删除文档")
    public ResponseEntity<?> deleteDoc(@RequestBody String params){
        Params params1 = JSON.parseObject(params,Params.class);
        docService.delete(params1.getId());
        return new ResponseEntity<>("删除成功",HttpStatus.OK);
    }
    // Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("files") MultipartFile[] uploadfiles,
                                             @RequestParam("tags")String[] tags) {
        HttpStatus status = HttpStatus.OK;
        String msg = "";
        logger.debug("多个文件上传!");

        String uploadedFileName = Arrays.stream(uploadfiles).map(
                x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x))
                .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) msg = "请选择上传文件!";
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles),tags);
        } catch (IOException e) {
            msg = "上传文件出错！";
            status = HttpStatus.BAD_REQUEST;
        }
        msg = "上传成功 - " + uploadedFileName;
        return new ResponseEntity<>(msg, status);
    }

    //save file
    private void saveUploadedFiles(List<MultipartFile> files,String[] tags) throws IOException {
        //1.保存tags到标签表 更新数量
        //上传成功 返回标签加存储路径
        //建立索引 id
        File root = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!root.exists()) root = new File("");
        File uploadFile = new File(root.getAbsolutePath(),"static/upload/");
        if(!uploadFile.exists()) uploadFile.mkdirs();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) continue; //next pls

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
            logger.info("{0}filePath:  "+ path.toString());
            Files.write(path, bytes);
            //将文件信息存入doc信息表中
//            String name, String content, String tags, int collectCnt, Date createTime, Date updateTime, int userId
            Doc doc = new Doc(file.getOriginalFilename(),"empty now",tags[i],0,new Date(),new Date(),0,0,file.getName());
            docService.addDocMsg(doc);
            //将二进制流数据存入数据表中 id值 id 数字块 数据
        }
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
