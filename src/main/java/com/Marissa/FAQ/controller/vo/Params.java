package com.Marissa.FAQ.controller.vo;

public class Params {
    private int page = 1;
    private int size = 1;
    private int id;

    public Params() {}

    public Params(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
