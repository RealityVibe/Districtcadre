package com.yze.manageonpad.districtcadre.core.enums;

import java.util.Calendar;
import java.util.Date;

/**
 * 数字枚举
 *
 * @author by yze on 2018/5/8
 * @see
 */
public enum NumEnum {
    /**
     * 数字-1
     */
    NUM_1_NEG(-1),

    /**
     * 数字0
     */
    NUM_0(0),

    /**
     * 数字1
     */
    NUM_1(1),

    /**
     * 数字2
     */
    NUM_2(2),

    /**
     * 数字3
     */
    NUM_3(3),

    /**
     * 数字4
     */
    NUM_4(4),

    /**
     * 数字5
     */
    NUM_5(5),

    /**
     * 数字6
     */
    NUM_6(6),

    /**
     * 数字7
     */
    NUM_7(7),

    /**
     * 数字8
     */
    NUM_8(8),

    /**
     * 数字9
     */
    NUM_9(9),

    /**
     * 数字10
     */
    NUM_10(10),

    /**
     * 数字11
     */
    NUM_11(11),

    /**
     * 数字12
     */
    NUM_12(12),

    /**
     * 数字14
     */
    NUM_14(14),

    /**
     * 数字15
     */
    NUM_15(15),

    /**
     * 数字16
     */
    NUM_16(16),

    /**
     * 数字20
     */
    NUM_20(20),

    /**
     * 数字21
     */
    NUM_21(21),

    /**
     * 数字22
     */
    NUM_22(22),

    /**
     * 数字23
     */
    NUM_23(23),

    /**
     * 数字24
     */
    NUM_24(24),

    /**
     * 数字25
     */
    NUM_25(25),

    /**
     * 数字26
     */
    NUM_26(26),

    /**
     * 数字27
     */
    NUM_27(27),

    /**
     * 数字29
     */
    NUM_29(29),

    /**
     * 数字30
     */
    NUM_30(30),

    /**
     * 数字32
     */
    NUM_32(32),

    /**
     * 数字35
     */
    NUM_35(35),

    /**
     * 数字37
     */
    NUM_37(37),

    /**
     * 数字38
     */
    NUM_38(38),

    /**
     * 数字40
     */
    NUM_40(40),

    /**
     * 数字41
     */
    NUM_41(41),

    /**
     * 数字45
     */
    NUM_45(45),

    /**
     * 数字60
     */
    NUM_60(60),

    /**
     * 数字64
     */
    NUM_64(64),

    /**
     * 数字75
     */
    NUM_75(75),

    /**
     * 数字77
     */
    NUM_77(77),

    /**
     * 数字99
     */
    NUM_99(99),

    /**
     * 数字100
     */
    NUM_100(100),

    /**
     * 数字120
     */
    NUM_120(120),

    /**
     * 数字128
     */
    NUM_128(128),

    /**
     * 数字200
     */
    NUM_200(200),

    /**
     * 数字256
     */
    NUM_256(256),

    /**
     * 数字512
     */
    NUM_512(512),

    /**
     * 数字800
     */
    NUM_800(800),

    /**
     * 数字999
     */
    NUM_999(999),

    /**
     * 数字1000
     */
    NUM_1000(1000),

    /**
     * 数字2000
     */
    NUM_2000(2000),

    /**
     * 数字2018
     */
    NUM_2018(2018),

    /**
     * 数字9999
     */
    NUM_9999(9999),

    /**
     * 数字10000
     */
    NUM_10000(10000),

    /**
     * 数字20000
     */
    NUM_20000(20000);

    private int value;

    NumEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static int getAgeByBirth(String birthday) {
        int age = 0;
        try {
            String[] birthdays = birthday.split("-");
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            if (Integer.valueOf(birthdays[0]) > now.get(Calendar.YEAR)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - Integer.valueOf(birthdays[0]);
                if (now.get(Calendar.MONTH) < Integer.valueOf(birthdays[1])) {
                    age -= 1;
                } else if(now.get(Calendar.MONTH) == Integer.valueOf(birthdays[1]) && now.get(Calendar.DAY_OF_MONTH) < Integer.valueOf(birthdays[2])){
                    age -= 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}
