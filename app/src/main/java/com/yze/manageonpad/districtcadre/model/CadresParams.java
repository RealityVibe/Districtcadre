package com.yze.manageonpad.districtcadre.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yze
 * <p>
 * 装载所有的json来的参数
 */
public class CadresParams implements Serializable {
    private List<Apartment> nameList;
    private String[][] cadreMatrix;
    private int[] prescentNum;
    private Map<String, Cadre> cadreMap;

    public CadresParams(List<Apartment> nameList, String[][] cadreMatrix, int[] prescentNum, Map<String, Cadre> cadreMap) {
        this.nameList = nameList;
        this.cadreMatrix = cadreMatrix;
        this.prescentNum = prescentNum;
        this.cadreMap = cadreMap;
    }

    public List<Apartment> getNameList() {
        return nameList;
    }

    public void setNameList(List<Apartment> nameList) {
        this.nameList = nameList;
    }

    public String[][] getCadreMatrix() {
        return cadreMatrix;
    }

    public void setCadreMatrix(String[][] cadreMatrix) {
        this.cadreMatrix = cadreMatrix;
    }

    public int[] getPrescentNum() {
        return prescentNum;
    }

    public void setPrescentNum(int[] prescentNum) {
        this.prescentNum = prescentNum;
    }

    public Map<String, Cadre> getCadreMap() {
        return cadreMap;
    }

    public void setCadreMap(Map<String, Cadre> cadreMap) {
        this.cadreMap = cadreMap;
    }
}
