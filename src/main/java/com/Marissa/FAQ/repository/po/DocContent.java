package com.Marissa.FAQ.repository.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DocContent")
public class DocContent implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "content")
    private byte[] content;
    @Column(name = "name")
    private String name;

    public DocContent() {}

    public DocContent(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
