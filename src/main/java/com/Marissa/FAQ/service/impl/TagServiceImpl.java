package com.Marissa.FAQ.service.impl;

import com.Marissa.FAQ.repository.TagRepository;
import com.Marissa.FAQ.repository.po.Comment;
import com.Marissa.FAQ.repository.po.Tag;
import com.Marissa.FAQ.service.TagService;
import com.Marissa.FAQ.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public void save(List<String> names) {
        for(String name : names){
            Tag tag = new Tag(name,1,0,0);
            if(tagRepository.checkExist(tag.getName()) == 0)
                tagRepository.save(tag);
            else
                tagRepository.updateDocCnt(CommonUtils.convertToList(name));
        }
    }

    @Override
    public void updateDocCnt(List<String> names) {
        tagRepository.updateDocCnt(names);
    }

    @Override
    public void updateSearchCnt(String name) {
        tagRepository.updateSearchCnt(name);
    }
}