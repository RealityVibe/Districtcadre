package com.yze.manageonpad.districtcadre.utils;

import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.model.CadresParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static List<Cadre> getCadreListByApartment(CadresParams cadresParams, String bmbh) {
        int[] apartCadreNums = cadresParams.getPrescentNum();
        String[][] cadresMatrix = cadresParams.getCadreMatrix();
        Map<String, Cadre> cadresMap = cadresParams.getCadreMap();

        List<Cadre> cadreList = new ArrayList<Cadre>();
        for (int i = 0; i < apartCadreNums[Integer.valueOf(bmbh)]; ++i) {
            String index = bmbh + cadresMatrix[Integer.valueOf(bmbh)][i];
            cadreList.add(cadresMap.get(index));
        }

        for (int i = cadreList.size() - 1; i > -1; i--) {
            if (cadreList.get(i).getCsny() == null) {
                cadreList.remove(i);
            }
        }
        return cadreList;
    }

    //名字搜索
    public static List<Cadre> getCadresListByName(String name, Map<String, Cadre> cadreMap) {
        List<Cadre> cadreList = new ArrayList<Cadre>();

        for (Map.Entry<String, Cadre> entry : cadreMap.entrySet()) {
            if(entry.getKey().contains(name)){
                cadreList.add(entry.getValue());
            }
        }

        return cadreList;
    }
}
