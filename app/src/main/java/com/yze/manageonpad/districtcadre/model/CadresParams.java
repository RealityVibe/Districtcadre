package com.yze.manageonpad.districtcadre.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yze
 * <p>
 * 装载所有的json来的参数
 */
public class CadresParams implements Serializable {
    private List<Apartment> allNameList;
    private List<Apartment> daNameList;
    private List<Apartment> caNameList;
    private String[][] cadreMatrix;
    private int[] prescentNum;
    private Map<String, Cadre> cadreMap;

    public CadresParams(List<Apartment> daNameList, List<Apartment> caNameList, String[][] cadreMatrix, int[] prescentNum, Map<String, Cadre> cadreMap) {
        this.daNameList = daNameList;
        this.caNameList = caNameList;
        this.cadreMatrix = cadreMatrix;
        this.prescentNum = prescentNum;
        this.cadreMap = cadreMap;
        this.allNameList = new ArrayList<>();
        allNameList.addAll(caNameList);
        allNameList.addAll(daNameList);
    }

    public List<Apartment> getAllNameList() {
        return allNameList;
    }

    public void setAllNameList(List<Apartment> allNameList) {
        this.allNameList = allNameList;
    }

    public List<Apartment> getDaNameList() {
        return daNameList;
    }

    public void setDaNameList(List<Apartment> daNameList) {
        this.daNameList = daNameList;
    }

    public List<Apartment> getCaNameList() {
        return caNameList;
    }

    public void setCaNameList(List<Apartment> caNameList) {
        this.caNameList = caNameList;
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
