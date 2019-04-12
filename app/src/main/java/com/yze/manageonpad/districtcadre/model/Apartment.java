package com.yze.manageonpad.districtcadre.model;

import org.litepal.crud.DataSupport;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class Apartment extends DataSupport {
    private int bmbh; // 部门编号
    private String bmmz; // 部门名字
    private String bmlx; // 部门类型

    public Apartment(int bmbh, String bmmz, String bmlx) {
        this.bmbh = bmbh;
        this.bmmz = bmmz;
        this.bmlx = bmlx;
    }

    public int getBmbh() {
        return bmbh;
    }

    public void setBmbh(int bmbh) {
        this.bmbh = bmbh;
    }

    public String getBmmz() {
        return bmmz;
    }

    public void setBmmz(String bmmz) {
        this.bmmz = bmmz;
    }

    public String getBmlx() {
        return bmlx;
    }

    public void setBmlx(String bmlx) {
        this.bmlx = bmlx;
    }
}
