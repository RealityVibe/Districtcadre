package com.yze.manageonpad.districtcadre.utils;

import com.yze.manageonpad.districtcadre.model.Cadre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */

public class CadreUtils {
    /**
     * 乡镇部门数（可能不是）
     */
    private final static int COUNTY_APARTMENT_NUMS = 22;

    //部门搜索 空格子占位
    public static List<Cadre> getCadreListFromMap(int[] apartCadreNums, String[][] cadresMatrix, Map<String, Cadre> cadresMap, String bmbh) {
        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartCadreNums[Integer.valueOf(bmbh)]; ++i) {
            String index = bmbh + cadresMatrix[Integer.valueOf(bmbh)][i];
            cadreList.add(cadresMap.get(index));
        }
        return cadreList;
    }

    //部门搜索 空格子占位
    public static List<Cadre> getCadresByApartId(int[] apartCadreNums, String[][] cadresMatrix, Map<String, Cadre> cadresMap, String bmbh) {
        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartCadreNums[Integer.valueOf(bmbh)]; ++i) {
            String index = bmbh + cadresMatrix[Integer.valueOf(bmbh)][i];
            cadreList.add(cadresMap.get(index));
        }
        return cadreList;
    }

    //部门搜索 空格子占位 调研员
    public List<Cadre> getReseacherFromMap(int[] apartCadreNums, String[][] cadresMatrix, Map<String, Cadre> cadresMap, String bmbh) {
        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartCadreNums[Integer.valueOf(bmbh)]; ++i) {
            String index = bmbh + cadresMatrix[Integer.valueOf(bmbh)][i];
            cadreList.add(cadresMap.get(index));
        }
        return cadreList;
    }


    //部门搜索 不含空格子
    public static List<Cadre> getCadreListByApartment(int[] apartCadreNums, String[][] cadresMatrix, Map<String, Cadre> cadresMap, String bmbh) {
        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartCadreNums[Integer.valueOf(bmbh)]; ++i) {
            String index = bmbh + cadresMatrix[Integer.valueOf(bmbh)][i];
            cadreList.add(cadresMap.get(index));
        }
/*
            // 硬编码换列的位置
            if (cadreList.size() > 0) {
            if (COUNTY_APARTMENT_NUMS > Integer.valueOf(cadreList.get(0).getBmbh())) {
                cadreList = reSortList(cadreList);
            }
        }*/
        for (int i = cadreList.size() - 1; i > -1; i--) {
            if (cadreList.get(i).getCsny() == null) {
                cadreList.remove(i);
            }
        }
        return cadreList;
    }

    //名字搜索
    public static List<Cadre> getCadresListByCondition(int[] apartCadreNums, String[][] cadresMatrix, Map<String, Cadre> cadresMap, String serachParam, int apartmentsNum) {
        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartmentsNum; ++i) {
            for (int j = 0; j < apartCadreNums[i]; ++j) {
                if (cadresMatrix[i][j].contains(serachParam)) {
                    cadreList.add(cadresMap.get(String.valueOf(i) + cadresMatrix[i][j]));
                }
            }
        }
        return cadreList;
    }

//    public List<Cadre> reSortList(List<Cadre> cadres) {
//        List<Cadre> sortedList = new ArrayList<Cadre>();
//        sortedList.addAll(cadres);
//        sortedList.set(7, cadres.get(11));
//        sortedList.set(10, cadres.get(12));
//        sortedList.set(11, cadres.get(13));
//        sortedList.set(12, cadres.get(14));
//        sortedList.set(13, cadres.get(10));
//        sortedList.set(14, cadres.get(7));
//        return sortedList;
//    }
}
