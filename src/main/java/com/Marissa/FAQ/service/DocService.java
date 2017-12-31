package com.Marissa.FAQ.service;

import com.Marissa.FAQ.repository.po.Doc;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DocService {
    public void addDocMsg(Doc doc);

    public void delete(int id);

    public List<Doc> getPageList(Pageable pageable);
}
