package com.kayvoh.must;

/**
 * Created by Nderitu Kelvin on 2/8/2017.
 */

public class KayList {
    private String name;
    private String regNo;
    private String img;
    private String status;

    public KayList(String name, String regNo, String img, String status) {
        this.name = name;
        this.regNo = regNo;
        this.img = img;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}