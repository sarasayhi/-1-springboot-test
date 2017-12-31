package com.Marissa.FAQ.repository.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Tag")
public class Tag implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "tag")
    private String tag;
    @Column(name = "docCnt")
    private int docCnt;
    @Column(name = "searchCnt")
    private int searchCnt;

    public Tag() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getDocCnt() {
        return docCnt;
    }

    public void setDocCnt(int docCnt) {
        this.docCnt = docCnt;
    }

    public int getSearchCnt() {
        return searchCnt;
    }

    public void setSearchCnt(int searchCnt) {
        this.searchCnt = searchCnt;
    }
}