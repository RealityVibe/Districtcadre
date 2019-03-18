package com.yze.manageonpad.districtcadre.model;


/**
 * @author yze
 * <p>
 * 2019/3/1.
 */
public class TestObject {
    private int id;
    private String name;
    private String major;
    private String theclass;

    public TestObject(int id, String name, String major, String theclass) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.theclass = theclass;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTheclass() {
        return theclass;
    }

    public void setTheclass(String theclass) {
        this.theclass = theclass;
    }
}
