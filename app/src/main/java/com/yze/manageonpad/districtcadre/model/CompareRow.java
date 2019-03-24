package com.yze.manageonpad.districtcadre.model;

public class CompareRow {

    /**
     * 对比的项目
     */
    private String compareItem;

    /**
     * 左侧数据
     */
    private String leftData;

    /**
     * 右侧数据
     */
    private String rightData;

    public CompareRow(String compareItem, String leftData, String rightData) {
        this.leftData = leftData;
        this.rightData = rightData;
        this.compareItem = compareItem;
    }

    public String getLeftData() {
        return leftData;
    }

    public void setLeftData(String leftData) {
        this.leftData = leftData;
    }

    public String getRightData() {
        return rightData;
    }

    public void setRightData(String rightData) {
        this.rightData = rightData;
    }

    public String getCompareItem() {
        return compareItem;
    }

    public void setCompareItem(String compareItem) {
        this.compareItem = compareItem;
    }
}
