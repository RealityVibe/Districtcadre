package com.yze.manageonpad.districtcadre.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.Cadre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */
public class JSONUtils {

    private List<Cadre> cadreList = new ArrayList<Cadre>();
    private List<Apartment> apartmentList = new ArrayList<Apartment>();


    //从JSON获取cadreList
    public List<Cadre> parseCadresFromJSON(String jsonData, Context c, String bmbh) {
        try {
            InputStream is = c.getAssets().open(jsonData);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "utf-8");

            if (text != null) {
                JSONObject object = new JSONObject(text);
                JSONArray array = object.getJSONArray("cadres");
                for (int i = 0; i < array.length(); ++i) {
                    JSONObject tmpobject = array.getJSONObject(i);
                    Cadre tmp_cadre;
                    int count = 0;
                    if (tmpobject.getString("bmbh").equals(bmbh)) {
                        if (!tmpobject.getString("xm").equals("null")) {
                            String tmp_xm = transformNullString(tmpobject, "xm");
                            String tmp_bmbh = transformNullString(tmpobject, "bmbh");
                            String tmp_xrzw = transformNullString(tmpobject, "xrzw");
                            String tmp_xb = transformNullString(tmpobject, "xb");
                            String tmp_csny = transformNullString(tmpobject, "csny");
                            String tmp_jg = transformNullString(tmpobject, "jg");
                            String tmp_xl = transformNullString(tmpobject, "qrzjy");
                            String tmp_xw = "";
                            tmp_xw = transformNullString(tmpobject, "zzxl");
                            String tmp_qrzxw = "";
                            tmp_qrzxw = transformNullString(tmpobject, "qrzxl");
                            String tmp_cjgzsj = transformNullString(tmpobject, "cjgzsj");
                            String tmp_rdsj = null;
                            if (!transformNullString(tmpobject, "zzmm").equals("中共党员") && !transformNullString(tmpobject, "zzmm").equals(""))
                                    tmp_rdsj = transformNullString(tmpobject, "zzmm");
                            else {
                                tmp_rdsj = transformNullString(tmpobject, "rdsj");
                            }
                            String tmp_rxzsj = transformNullString(tmpobject, "rxzsj");
                            String tmp_fg = transformNullString(tmpobject, "fg");
                            String tmp_bz = transformNullString(tmpobject, "bz");
                            tmp_cadre = new Cadre(tmp_xm, tmp_bmbh, tmp_xrzw, tmp_xb, tmp_csny, tmp_jg,
                                    tmp_xl, tmp_xw, tmp_qrzxw, tmp_cjgzsj, tmp_rdsj, tmp_rxzsj, tmp_fg, tmp_bz);
                        } else {
                            tmp_cadre = new Cadre("--", tmpobject.getString("bmbh"));
                        }
                        cadreList.add(tmp_cadre);
                        if (count++ > 30)
                            break;
                    }
                }
                return cadreList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //从JSON获取cadreList
    public static List<Cadre> parseCadresFromJSON(String jsonData, Context c) {
        List<Cadre> cadreList = new ArrayList<>();
        try {
            String text = getContentString(jsonData, c);

            if (text != null) {
                JSONObject object = new JSONObject(text);
                JSONArray array = object.getJSONArray("cadres");
                for (int i = 0; i < array.length(); ++i) {
                    JSONObject tmpobject = array.getJSONObject(i);
                    Cadre tmp_cadre;
                    int count = 0;
                    if (!tmpobject.getString("xm").equals("null")) {

                        String tmp_xm = transformNullString(tmpobject, "xm");
                        String tmp_bmbh = transformNullString(tmpobject, "bmbh");
                        String tmp_xrzw = transformNullString(tmpobject, "xrzw");
                        String tmp_xb = transformNullString(tmpobject, "xb");
                        String tmp_csny = transformNullString(tmpobject, "csny");
                        String tmp_jg = transformNullString(tmpobject, "jg");
                        String tmp_xl = transformNullString(tmpobject, "qrzjy");
                        String tmp_xw = "";
                        tmp_xw = transformNullString(tmpobject, "zzxl");
                        String tmp_qrzxw = "";
                        tmp_qrzxw = transformNullString(tmpobject, "qrzxl");
                        String tmp_cjgzsj = transformNullString(tmpobject, "cjgzsj");
                        String tmp_rdsj = null;
                        if (!transformNullString(tmpobject, "zzmm").equals("中共党员") && !transformNullString(tmpobject, "zzmm").equals(""))
                            tmp_rdsj = transformNullString(tmpobject, "zzmm");
                        else {
                            tmp_rdsj = transformNullString(tmpobject, "rdsj");
                        }
                        String tmp_rxzsj = transformNullString(tmpobject, "rxzsj");
                        String tmp_fg = transformNullString(tmpobject, "fg");
                        String tmp_bz = "";
                        tmp_cadre = new Cadre(tmp_xm, tmp_bmbh, tmp_xrzw, tmp_xb, tmp_csny, tmp_jg,
                                tmp_xl, tmp_xw, tmp_qrzxw, tmp_cjgzsj, tmp_rdsj, tmp_rxzsj, tmp_fg, tmp_bz);
                    } else {
                        tmp_cadre = new Cadre("", tmpobject.getString("bmbh"));
                    }
                    cadreList.add(tmp_cadre);
                }
                return cadreList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 获取后备干部名单
     * */
    public static List<Cadre> parseBackupFromJson(String jsonData, Context c, String bmlx) throws Exception {
        String text = getContentString(jsonData, c);
        List<Cadre> cadreList = new ArrayList<>();
        if (text != null) {
            JSONObject object = new JSONObject(text);
            JSONArray array = object.getJSONArray("bk_person_info");
            for (int i = 0; i < array.length(); ++i) {
                JSONObject tb = array.getJSONObject(i);
                String scsf = "";
                if (!tb.isNull("scsfhbgb"))
                    if (!tb.getString("scsfhbgb").equals("null"))
                        scsf = tb.getString("scsfhbgb");
                cadreList.add(new Cadre(tb.getInt("ID"), tb.getString("xm"), tb.getString("xb")
                        , tb.getString("csny"), tb.getString("cjgzsj"), tb.getString("qrzjy"), tb.getString("dp")
                        , tb.getString("sf"), tb.getString("xgzdwjzw"), tb.getString("cjtjrs")
                        , tb.getString("dps"), tb.getString("dpl"), scsf));
            }
            return cadreList;
        }
        return null;
    }

    //    private static String getContentString(String jsonData, Context c) throws IOException {
    private static String getDataFromMemory(String jsonData, Context c) throws IOException {

        // 从raw目录读取
        InputStream is = c.getResources().openRawResource(R.raw.sourcedata);
        BufferedReader read = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while ((line = read.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String txt = sb.toString();
        return new String(txt.getBytes("UTF-8"), "UTF-8");
    }

    /**
     * 从外存读取文件
     *
     * @param jsonData
     * @param c
     * @return
     * @throws IOException
     */
    public static String getContentString(String jsonData, Context c) throws IOException {
//    public static String getDataFromMemory(String jsonData, Context c) throws IOException {
        String result = "";
        FileInputStream f = null;
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
//         1105修改点
//        f = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/docs/" + jsonData);
        f = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + jsonData);
//        f = new FileInputStream(String.valueOf(c.getAssets().open("sourcedata.json")));
        //new BufferedReader(new InputStreamReader(new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/docs/sourcedata.json"))).readLine()
        BufferedReader bis = new BufferedReader(new InputStreamReader(f));
        String line = "";
        while ((line = bis.readLine()) != null) {
            result += line;
        }
        bis.close();
        f.close();
        return new String(result.getBytes("UTF-8"), "UTF-8");
    }

    public static void updateDataResource(String newData, Context c) throws IOException {
        String result = "";
        FileOutputStream f = null;
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) c, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
//        f = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Android/docs/sourcedata.json");
        f = new FileOutputStream(Environment.getExternalStorageDirectory() + "/sourcedata.json");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(f));
        bw.write(newData);
        bw.close();
    }

    /*
     * 获取调研员名单
     * */
    public static List<String> getResearcherList(String jsonData, Context c, String type) throws Exception {
        List<String> investList = new ArrayList<String>();
//        try {
        String text = getContentString(jsonData, c);
        if (text != null) {
            JSONObject object = new JSONObject(text);
            JSONArray array = object.getJSONArray(type);
            for (int i = 0; i < array.length(); ++i) {
                JSONArray persons = array.getJSONObject(i).getJSONArray("persons");
                String bmnm = array.getJSONObject(i).getString("BmName");
                StringBuffer personSB = new StringBuffer();
                personSB.append(bmnm);
                for (int j = 0; j < persons.length(); ++j) {
                    String p = persons.getString(j);
                    personSB.append("," + p);
                }
                investList.add(personSB.toString());
            }
            return investList;
        }
        return null;
    }

    /*
     * 获取单位
     * */
    public static List<Apartment> parseApartmentsFromJSON(String jsonData, Context c, String bmlx) throws IOException, JSONException {
        List<Apartment> apartmentList = new ArrayList<Apartment>();
        String text = getContentString(jsonData, c);
        if (bmlx.equals("1")) {
            apartmentList.add(new Apartment(0, "---", "1"));
//            loadApartNum++;
        }
        if (text != null) {
               /* Gson gson = new Gson();
                apartmentList = gson */
            JSONObject object = new JSONObject(text);
            JSONArray array = object.getJSONArray("apartments");
            for (int i = 0; i < array.length(); ++i) {
                JSONObject tmpobject = array.getJSONObject(i);
                if (tmpobject.get("bmlx").equals(bmlx)) {
//                    loadApartNum++;
                    Apartment tmp_apartment = new Apartment(Integer.valueOf(tmpobject.getString("bmbh"))
                            , tmpobject.getString("bmmz"), tmpobject.getString("bmlx"));
                    apartmentList.add(tmp_apartment);
                }
            }
            return apartmentList;
        }
        return null;
    }

    //处理null值为""
    private static String transformNullString(JSONObject object, String key) {
        if (object.isNull(key)) {
            return "";
        }
        String str = null;
        try {
            str = object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (str == null) {
            return "";
        } else {
            return str.toString();
        }
    }

    //删除文件夹和文件夹里面的文件


    //flie：要删除的文件夹的所在位置
    public void deleteFile(Context mContext, File file) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
       /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri uri = getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID+ ".fileprovider",
                    file);
        } else{
            Uri uri = Uri.fromFile(file);
        }*/

        System.out.print("GestureLock fail func");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(mContext, f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }


    public List<Cadre> reSortList(List<Cadre> cadres) {
        List<Cadre> sortedList = new ArrayList<Cadre>();
        sortedList.addAll(cadres);
        sortedList.set(7, cadres.get(11));
        sortedList.set(10, cadres.get(12));
        sortedList.set(11, cadres.get(13));
        sortedList.set(12, cadres.get(14));
        sortedList.set(13, cadres.get(10));
        sortedList.set(14, cadres.get(7));
        return sortedList;
    }
}
