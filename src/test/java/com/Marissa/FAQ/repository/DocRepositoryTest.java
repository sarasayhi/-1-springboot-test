package com.Marissa.FAQ.repository;

import com.Marissa.FAQ.repository.po.Doc;
import com.Marissa.FAQ.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocRepositoryTest {
    @Autowired
    DocRepository docRepository;

//    @Before
//    public void setUp() throws Exception {
////        (String name, String content, String tags, int collectCnt,
//// Date createTime, Date updateTime, int userId, int deleted, String ext)
//            Doc doc = new Doc("单元测试用","无","测试",0,new Date(),new Date(),0,0,"pdf");
//            add(doc);
//    }

    private String name = "单元测试用2";
    @Test
    public void add(){
        Doc doc = new Doc(name,"无","测试",0,new Date(),new Date(),0,0,"pdf");
        docRepository.save(doc);
    }

    @Test
    public void checkName(){
        int i = docRepository.checkExist(name);
        System.out.println("checkName:" + i);
    }

    @Test
    public void getId(){
        int id = docRepository.getId(name);
        System.out.println("getId: " + id);
    }

    @Test
    public void getByName(){
        Doc doc = docRepository.getByName(name);
        System.out.println("getByName: " + CommonUtils.transformToString(doc));
    }

    @Test
    public void getlist(){
        Pageable pageable = new PageRequest(1,1, Sort.Direction.DESC,"id");
        List<Doc> list = docRepository.getDocByPage(pageable);
        System.out.println("getlist: " + CommonUtils.transformToString(list));
    }

    @Test
    public void delete(){docRepository.deleteBatch(CommonUtils.convertToList(30)); }

    @Test
    public void update(){
        Doc doc = new Doc(name,"无11","测试",0,new Date(),new Date(),0,0,"pdf");
        doc.setId(30);
        docRepository.save(doc);
    }

    @Test
    public void findByKey(){
        String key = "测试";
        List<Doc> list = docRepository.findByKey(key);
        System.out.println("findByKey: " + CommonUtils.transformToString(list));
    }

//    @After
//    public void cleanUp() throws Exception {
//        delete(getId("单元测试用"));
//    }
}
