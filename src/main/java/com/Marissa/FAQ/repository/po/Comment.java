package com.Marissa.FAQ.repository.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "Comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "userId")
    private int userId;
    @Column(name = "docId")
    private int docId;
    @Column(name = "createDate")
    private Date createDate;

    public Comment() {}

    public Comment(int id, String comment, int userId, int docId, Date createDate) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.docId = docId;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
