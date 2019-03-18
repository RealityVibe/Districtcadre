package com.yze.manageonpad.districtcadre.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.yze.manageonpad.districtcadre.BuildConfig;

import java.io.File;

import static android.support.v4.content.FileProvider.getUriForFile;
import static org.litepal.LitePalApplication.getContext;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */
public class FileOperationUtils {

    // 调用外部应用，打开文件
    public static void openFile(Context mContext, File file) throws Exception {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);

        // 判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // 设置文件类型为Word
            intent.setDataAndType(uri, "application/msword");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/msword");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        mContext.startActivity(intent);

    }

    public static void openDoc(String name) throws Exception {
        File docPath = new File(Environment.getExternalStorageDirectory(), "Android/docs");
        File wordFile = new File(docPath, name + ".doc");
        File wordFile3 = new File(docPath, name + ".docx");

        if (wordFile.exists()) {
            // 调用函数使用外部程序打开
            openFile(getContext(), wordFile);
        } else if (wordFile3.exists()) {
            // 调用函数使用外部程序打开
            openFile(getContext(), wordFile3);
        } else {
            Toast.makeText(getContext(), "该干部文件缺失，请补全材料后再试", Toast.LENGTH_LONG).show();
        }
    }
}
