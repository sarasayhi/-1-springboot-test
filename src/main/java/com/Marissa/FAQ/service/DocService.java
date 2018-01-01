package com.Marissa.FAQ.service;

import com.Marissa.FAQ.repository.po.Doc;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DocService {
    void save(Doc doc);

    void delete(int id);

    List<Doc> getPageList(Pageable pageable);

    List<Doc> findByKey(String key);

    Doc getByName(String name);

    int checkExist(String name);
}
