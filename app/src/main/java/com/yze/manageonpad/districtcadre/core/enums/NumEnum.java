package com.yze.manageonpad.districtcadre.core.enums;

public enum NumEnum {

    NUM_1(1),
    NUM_2(2),
    NUM_3(3),
    NUM_4(4),
    NUM_5(5);

    private int value;

    NumEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
