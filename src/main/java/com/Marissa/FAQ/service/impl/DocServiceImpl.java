package com.Marissa.FAQ.service.impl;

import com.Marissa.FAQ.repository.DocRepository;
import com.Marissa.FAQ.repository.po.Doc;
import com.Marissa.FAQ.service.DocService;
import com.Marissa.FAQ.utils.CommonUtils;
import com.google.common.collect.Lists;
import com.mchange.v1.util.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DocServiceImpl implements DocService {
    @Autowired
    private DocRepository docRepository;

    @Override
    public void addDocMsg(Doc doc) {
        docRepository.save(doc);
    }

    @Override
    public void delete(int id) {
        docRepository.deleteBatch(CommonUtils.convertToList(id));
    }

    @Override
    public List<Doc> getPageList(Pageable pageable) {
//        return docRepository.getDocByPage(pageable);
        Iterable<Doc> geted = docRepository.findAll();
        List<Doc> list = Lists.newArrayList();
        geted.forEach(single ->{list.add(single);});
        return list;
    }
}