package com.yze.manageonpad.districtcadre.core.enums;
/**
 * @author yze
 *
 * 2019/2/27.
 */
public class ExceptionsEnum{
    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    public final static ExceptionsEnum COUNTY_MISS_ERROR = new ExceptionsEnum("1001", "乡镇干部数据缺失");
    public final static ExceptionsEnum DIRECT_MISS_ERROR = new ExceptionsEnum("1002", "区级干部数据缺失");
    public final static ExceptionsEnum BACKUP_MISS_ERROR = new ExceptionsEnum("1003", "后备干部数据缺失");
    public final static ExceptionsEnum RESEARCHER_MISS_ERROR = new ExceptionsEnum("1004", "调研员数据缺失a");
    public final static ExceptionsEnum CADRE_FILE_MISS = new ExceptionsEnum("1005", "该干部文件缺失，请补全材料后再试");

    public ExceptionsEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
