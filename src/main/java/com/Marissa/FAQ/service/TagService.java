package com.Marissa.FAQ.service;

import java.util.List;

public interface TagService {
    void save(List<String> names);

    void updateDocCnt(List<String> names);

    void updateSearchCnt(String name);
}
