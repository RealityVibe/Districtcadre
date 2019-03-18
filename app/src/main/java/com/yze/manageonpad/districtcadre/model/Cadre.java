package com.yze.manageonpad.districtcadre.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */

public class Cadre extends DataSupport implements Serializable {
    private int id;
    private String xm;
    private String bmbh;
    private String xrzw;
    private String xb;
    private String csny;
    private String jg;
    private String xl;
    private String xw;
    private String qrzxw;
    private String cjgzsj;
    private String rdsj;
    private String rxzsj;
    private String fg;
    private String bz;

    /*后备干部*/
    private int bh;//编号
    private String dp;//党派
    private String sf;//身份
    private String cjrs;//民主推荐情况-参加人数
    private String dps;//民主推荐情况-得票数
    private String dpl;//民主推荐情况-得票率
    private String scsfhb;//上次是否为后备干部

    // 调研员
    public Cadre(String xm, String bmbh) {
        this.xm = xm;
        this.bmbh = bmbh;
    }

    /*
     * 后备干部
     * */
    public Cadre(int bh, String xm, String xb, String csny, String cjgzsj,
                 String xl, String dp, String sf, String xrzw, String cjrs, String dps, String dpl, String scsfhb) {
        this.xm = xm;
        this.xrzw = xrzw;
        this.xb = xb;
        this.csny = csny;
        this.xl = xl;
        this.cjgzsj = cjgzsj;
        this.bh = bh;
        this.dp = dp;
        this.sf = sf;
        this.cjrs = cjrs;
        this.dps = dps;
        this.dpl = dpl;
        this.scsfhb = scsfhb;
    }

/*    public Cadre(String bh, String xm, String xb, String csny, String cjgzsj,
                 String xl, String dp, String sf, String xrzw, String cjrs, String dps, String dpl, String scsfhb){

    }*/

    /*
     * 镇街/区级干部
     * */
    public Cadre(String xm, String bmbh, String xrzw, String xb, String csny, String jg, String xl, String xw, String qrzxw, String cjgzsj, String rdsj, String rxzsj, String fg, String bz) {
        this.xm = xm;
        this.bmbh = bmbh;
        this.xrzw = xrzw;
        this.xb = xb;
        this.csny = csny;
        this.jg = jg.replace("-", "");/*
        this.xl = xl.replaceAll("，","");
        this.xl = this.xl.replaceAll("，","");*/
        this.xl = xl.replaceAll(",|，|、", " ");
//        this.xl = xl.replace('、', ' ');
        this.xw = xw;
        this.cjgzsj = cjgzsj;
        this.rdsj = rdsj;
        this.rxzsj = rxzsj;
        this.fg = fg;
        this.bz = bz;
        this.qrzxw = qrzxw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getXrzw() {
        return xrzw;
    }

    public void setXrzw(String xrzw) {
        this.xrzw = xrzw;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getCsny() {
        return csny;
    }

    public void setCsny(String csny) {
        this.csny = csny;
    }

    public String getJg() {
        return jg.replace("-", "");
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
//        this.xl = xl;
    }

    public String getXw() {
        return xw;
    }

    public void setXw(String xw) {
        this.xw = xw;
    }

    public String getCjgzsj() {
        return cjgzsj;
    }

    public void setCjgzsj(String cjgzsj) {
        this.cjgzsj = cjgzsj;
    }

    public String getRdsj() {
        return rdsj;
    }

    public void setRdsj(String rdsj) {
        this.rdsj = rdsj;
    }

    public String getRxzsj() {
        return rxzsj;
    }

    public void setRxzsj(String rxzsj) {
        this.rxzsj = rxzsj;
    }

    public String getFg() {
        return fg;
    }

    public void setFg(String fg) {
        this.fg = fg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public int getBh() {
        return bh;
    }

    public void setBh(int bh) {
        this.bh = bh;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getCjrs() {
        return cjrs;
    }

    public void setCjrs(String cjrs) {
        this.cjrs = cjrs;
    }

    public String getDps() {
        return dps;
    }

    public void setDps(String dps) {
        this.dps = dps;
    }

    public String getDpl() {
        return dpl;
    }

    public void setDpl(String dpl) {
        this.dpl = dpl;
    }

    public String getScsfhb() {
        return scsfhb;
    }

    public void setScsfhb(String scsfhb) {
        this.scsfhb = scsfhb;
    }

    public String getQrzxw() {
        return qrzxw;
    }

    public void setQrzxw(String qrzxw) {
        this.qrzxw = qrzxw;
    }
}
