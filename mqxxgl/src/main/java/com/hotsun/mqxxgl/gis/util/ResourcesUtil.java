package com.hotsun.mqxxgl.gis.util;

import android.content.Context;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by li on 2017/10/27.
 * 获取本地文件工具类
 */

public class ResourcesUtil {

    private static ResourcesUtil resourcesUtil;

    private final String ROOT_MAPS = "/maps";
    public final String otitan_map = "/otitan.map";
    public final String otms = "/otms";
    public final String db = "/db";

    public static final String geoPath = "/otms/test";
    public static final String geoFile = "/offline.geodatabase";

    private static class LazyHolder {
        private static final ResourcesUtil INSTANCE = new ResourcesUtil();
    }

    public static final ResourcesUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * 获取手机内部存储地址和外部存储地址
     */
    private String[] getMemoryPath(Context mContext) {
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        String[] paths = null;
        try {
            paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    public void creatFolder(Context context) {
        String[] str = getMemoryPath(context);

        if (!new File(str[0] + ROOT_MAPS).exists()) {
            boolean flag = new File(str[0] + ROOT_MAPS).mkdirs();
        }
        if (!new File(str[0] + ROOT_MAPS + otitan_map).exists()) {
            boolean flag = new File(str[0] + ROOT_MAPS + otitan_map).mkdirs();
        }
        if (!new File(str[0] + ROOT_MAPS + db).exists()) {
            boolean flag = new File(str[0] + ROOT_MAPS + db).mkdirs();
        }
        if (!new File(str[0] + ROOT_MAPS + otms).exists()) {
            boolean flag = new File(str[0] + ROOT_MAPS + otms).mkdirs();
        }
    }

    /**
     * 获取基础离线地图数据path
     */
    public String getBaseTitlePath(Context mContext) {
        String basePath = otitan_map + "/title.tpk";
        return getFilePath(mContext, basePath);
    }

    /**
     * 获取db.sqlite文件
     */

    public File getDbSqlite(Context context) {
        String path = getFilePath(context, db + "/db.sqlite");
        return new File(path);
    }

    /**
     * 获取db.sqlite文件
     */
    public File getYwSqlite(Context context, String dbname) {
        String path = getFilePath(context, db + "/" + dbname);
        return new File(path);
    }

    /**
     * 取文件可用地址
     */
    public String getFilePath(Context mContext, String path) {
        String dataPath = "文件可用地址";
        String[] memoryPath = getMemoryPath(mContext);
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + path);
            if (file.exists() && file.isFile()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path;
                break;
            }
        }
        return dataPath;
    }

    /**
     * 获取文件夹的名字
     */

    public String getFolderPath(Context mContext, String foldername) {

        String dataPath = "文件夹可用地址";
        String[] memoryPath = getMemoryPath(mContext);
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + foldername);
            if (file.exists() && file.isDirectory()) {
                dataPath = memoryPath[i] + ROOT_MAPS + foldername;
                break;
            } else {
                file.mkdirs();
            }
        }
        return dataPath;
    }

    /**
     * 获取otms下的文件夹
     */
    public List<File> getOtmsFiles(Context mContext) {
        List<File> folds = new ArrayList<>();
        String otmspath = getFolderPath(mContext, otms);
        File[] files = new File(otmspath).listFiles();
        if (files == null) {
            return folds;
        }
        for (File file : files) {
            if (file.exists() && file.isDirectory()) {
                folds.add(file);
            }
        }
        return folds;
    }

    /**
     * 获取otms文件夹下的对应文件夹内的离线数据
     */
    public HashMap<String, List<File>> getOtmsFoldesFile(List<File> folds) {
        HashMap<String, List<File>> hashMap = new HashMap<>();
        for (File fold : folds) {
            File[] files = new File(fold.getPath()).listFiles();
            List<File> fileList = new ArrayList<>();
            for (File file : files) {
                if (file.exists() && file.isFile() && (file.getName().endsWith(".geodatabase") || file.getName().endsWith(".otms"))) {
                    fileList.add(file);
                }
            }
            hashMap.put(fold.getPath(), fileList);
        }
        return hashMap;
    }

}
