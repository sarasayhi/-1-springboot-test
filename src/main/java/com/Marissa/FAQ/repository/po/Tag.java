package com.Marissa.FAQ.repository.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Tag")
public class Tag implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "docCnt")
    private int docCnt;
    @Column(name = "searchCnt")
    private int searchCnt;
    @Column(name = "deleted")
    private int deleted;

    public Tag() {}

    public Tag(String name, int docCnt, int searchCnt, int deleted) {
        this.name = name;
        this.docCnt = docCnt;
        this.searchCnt = searchCnt;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}