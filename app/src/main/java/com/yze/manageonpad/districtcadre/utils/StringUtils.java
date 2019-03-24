package com.yze.manageonpad.districtcadre.utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 网关里面cn.com.servyou.xqy.base.utils.http.HttpFastUtil要用这个类，吐血，还是再加回来，吐血
 *
 */
public class StringUtils{

    public static final String EMPTY_STRING = "";
    /**
     * 左补空格或零
     *
     * @param strSrcLength
     * @param strSrc
     * @param flag
     *            1: "0" 2: " "
     * @return strReturn
     */
    public static String leftPading(int strSrcLength, String strSrc, String flag) {
        if (strSrc==null) {
            throw new IllegalArgumentException("strSrc is not null!");
        }
        String strReturn = "";
        String strtemp = "";
        int curLength = strSrc.trim().length();
        if (curLength > strSrcLength) {
            strReturn = strSrc.trim().substring(0, strSrcLength);
        } else if (curLength == strSrcLength) {
            strReturn = strSrc.trim();
        } else {
            for (int i = 0; i < (strSrcLength - curLength); i++) {
                strtemp = strtemp + flag;
            }
            strReturn = strtemp + strSrc.trim();
        }
        return strReturn;
    }

    /**
     * 右补空格或零（文字，"0"�?0�?
     *
     * @param strSrcLength
     * @param strSrc
     * @param flag
     *            1: "0" 2: " "
     * @return strReturn
     */
    public static String rightPading(int strSrcLength, String strSrc, String flag) {
        if (strSrc==null) {
            throw new IllegalArgumentException("strSrc is not null!");
        }
        String strReturn = "";
        String strtemp = "";
        int curLength = strSrc.trim().length();
        if (curLength > strSrcLength) {
            strReturn = strSrc.trim().substring(0, strSrcLength);
        } else if (curLength == strSrcLength) {
            strReturn = strSrc.trim();
        } else {
            for (int i = 0; i < (strSrcLength - curLength); i++) {
                strtemp = strtemp + flag;
            }
            strReturn = strSrc.trim() + strtemp;
        }
        return strReturn;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isEquals(String s1, String s2) {
        return s1 == null && s2 == null?true:(s1 != null && s2 != null?s1.equals(s2):false);
    }

    public static String nullIfEmpty(String string) {
        return isEmpty(string) ? null : string;
    }

    public static String emptyIfNull(String string) {
        return string == null ? "" : string;
    }

    public static String[] split(String strings, String delims) {
        if (strings == null) {
            return new String[0];
        } else {
            StringTokenizer tokens = new StringTokenizer(strings, delims);
            String[] result = new String[tokens.countTokens()];
            int i = 0;
            while (tokens.hasMoreTokens()) {
                result[i++] = tokens.nextToken();
            }
            return result;
        }
    }

    public static String subString(String str, int beg, int end) {
        String result = null;
        if (beg<0 || end<0) {
            return null;
        }

        if (!isEmpty(str)) {
            int len = str.length();
            if (end > len) {
                return null;
            }
            result = str.substring(beg, end);
        }

        return result;
    }

    /**
     * 剪切文本。如果进行了剪切，则在文本后加上"..."
     *
     * @param s
     *            剪切对象。
     * @param len
     *            编码小于256的作为一个字符，大于256的作为两个字符。
     * @return
     */
    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }
        // 最大计数（如果全是英文）
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for (; count < maxCount && i < slen; i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        if (i < slen) {
            if (count > maxCount) {
                i--;
            }
            if (!isEmpty(append)) {
                if (s.codePointAt(i - 1) < 256) {
                    i -= 2;
                } else {
                    i--;
                }
                return s.substring(0, i) + append;
            } else {
                return s.substring(0, i);
            }
        } else {
            return s;
        }
    }

    public static String getClassName(String wholeName) {
        int lastIndex = wholeName.lastIndexOf(".");
        return wholeName.substring(lastIndex + 1);
    }


    public static boolean isNullOrEmpty(Object str) {
        if(str == null){
            return true;
        }
        if(str instanceof String){
            String temp=String.valueOf(str);
            temp=temp.trim();
            return temp.length() == 0;
        }
        return false;
    }
    //#task move from framework-core
    public static Integer getInteger(String source, Integer defaultValue) {
        try {
            return Integer.parseInt(source.trim());
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static Integer getInteger(String source) {
        return getInteger(source, 0);
    }

    /**
     * 描述:   判断是否是整数或小数
     *
     * @param number
     * @return
     */
    public static boolean isDecimal(String number){
        Pattern pattern = Pattern.compile("[-+]{0,1}\\d+(\\.\\d+)?");
        Matcher isNum = pattern.matcher(number);
        isNum.matches();
        return isNum.matches();
    }

}
