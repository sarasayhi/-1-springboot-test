package com.Marissa.FAQ.service.impl;

import com.Marissa.FAQ.repository.DocContentRepository;
import com.Marissa.FAQ.repository.po.DocContent;
import com.Marissa.FAQ.service.DocContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DocContentServiceImpl implements DocContentService {
    @Autowired
    private DocContentRepository docContentRepository;

    @Override
    public void addDocContentMsg(DocContent docContent) {
        docContentRepository.save(docContent);
    }
}