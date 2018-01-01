package com.Marissa.FAQ.repository;

import com.Marissa.FAQ.repository.po.Tag;
import com.Marissa.FAQ.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    private String name = "单元测试用2";
    @Test
    public void add(){
        Tag tag = new Tag(name,0,0,0);
        tagRepository.save(tag);
    }

    @Test
    public void checkName(){
        int i = tagRepository.checkExist(name);
        System.out.println("checkName:" + i);
    }

    @Test
    public void getlist(){
        Pageable pageable = new PageRequest(1,1, Sort.Direction.DESC,"id");
        List<Tag> list = tagRepository.getAll(pageable);
        System.out.println("getlist: " + CommonUtils.transformToString(list));
    }

    @Test
    public void delete(){tagRepository.deleteBatch(CommonUtils.convertToList(30)); }

    @Test
    public void update(){
        Tag tag = new Tag(name,0,0,0);
        tag.setId(30);
        tagRepository.save(tag);
    }

    @Test
    public void findByKey(){
        Tag tag = tagRepository.findByName(name);
        System.out.println("findByKey: " + CommonUtils.transformToString(tag));
    }

    @Test
    public void updateDocCnt(){
        tagRepository.updateDocCnt(CommonUtils.convertToList(name));
        System.out.println("updateDocCnt finish! ");
    }

    @Test
    public void updateSearchCnt(){
        tagRepository.updateSearchCnt(name);
        System.out.println("updateSearchCnt finish! ");
    }

    @Test
    public void getTotal(){
        System.out.println("getTotal: " + tagRepository.getTotal());
    }
}