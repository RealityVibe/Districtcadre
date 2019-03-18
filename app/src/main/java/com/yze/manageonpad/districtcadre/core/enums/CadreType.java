package com.yze.manageonpad.districtcadre.core.enums;
/**
 * @author yze
 *
 * 2019/2/27.
 */
public enum CadreType {
    /**
     * 乡镇干部
     */
    COUNTY(0, "乡镇干部"),
    /**
     * 区级干部
     */
    DIRECT(1, "区级干部"),
    /**
     * 后备干部
     */
    BACKUP(2, "后备干部"),
    /**
     * 调研员
     */
    RESEARCHER(3, "调研员");


    private int code;
    private String name;

    CadreType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
