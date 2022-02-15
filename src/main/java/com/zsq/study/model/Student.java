package com.zsq.study.model;

public class Student {
    private Integer lid;

    private String strname;

    private Integer lage;

    public Student(Integer lid, String strname, Integer lage) {
        this.lid = lid;
        this.strname = strname;
        this.lage = lage;
    }

    public Student() {
        super();
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public String getStrname() {
        return strname;
    }

    public void setStrname(String strname) {
        this.strname = strname == null ? null : strname.trim();
    }

    public Integer getLage() {
        return lage;
    }

    public void setLage(Integer lage) {
        this.lage = lage;
    }
}
