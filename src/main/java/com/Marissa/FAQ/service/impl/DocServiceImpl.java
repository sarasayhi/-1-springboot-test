package com.Marissa.FAQ.service.impl;

import com.Marissa.FAQ.repository.DocRepository;
import com.Marissa.FAQ.repository.po.Doc;
import com.Marissa.FAQ.service.DocService;
import com.Marissa.FAQ.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocServiceImpl implements DocService {
    @Autowired
    private DocRepository docRepository;

    @Override
    public void save(Doc doc) {
        if(docRepository.checkExist(doc.getName()) == 0)
            docRepository.save(doc);
    }

    @Override
    public void delete(int id) {
        docRepository.deleteBatch(CommonUtils.convertToList(id));
    }

    @Override
    public List<Doc> getPageList(Pageable pageable) {
        int total = docRepository.getTotal();
        int before = pageable.getPageSize()*(pageable.getPageNumber()-1);
        if(total <= before){
         return null;
        } else{
            return docRepository.getDocByPage(pageable);
        }
    }

    @Override
    public List<Doc> findByKey(String key) {
        return docRepository.findByKey(key);
    }

    @Override
    public Doc getByName(String name) {
        return docRepository.getByName(name);
    }

    @Override
    public int checkExist(String name) {
        return docRepository.checkExist(name);
    }
}