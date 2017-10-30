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


    private Context mContext;
    private ResourcesUtil resourcesUtil;

    private final String ROOT_MAPS = "/maps";
    public final String otitan_map = "/otitan.map";
    public final String otms = "/otms";


    public synchronized ResourcesUtil getInstance(Context context) {
        if (resourcesUtil == null) {
            try {
                resourcesUtil = new ResourcesUtil(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resourcesUtil;
    }

    public ResourcesUtil(Context context){
        mContext = context;
    }


    /** 获取手机内部存储地址和外部存储地址 */
    private String[] getMemoryPath() {
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        String[] paths = null;
        try {
            paths = (String[])sm.getClass().getMethod("getVolumePaths").invoke(sm);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**获取基础离线地图数据path*/
    public String getBaseTitlePath(){
        String basePath = otitan_map + "/title.tpk";
        return getFilePath(basePath);
    }

    /** 取文件可用地址 */
    public String getFilePath(String path) {
        String dataPath = "文件可用地址";
        String[] memoryPath = getMemoryPath();
        for(int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + path);
            if (file.exists() && file.isFile()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path;
                break;
            }
        }
        return dataPath;
    }
    /**获取文件夹的名字*/
    public String getFolderPath(String foldername){
        String dataPath = "文件夹可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + foldername);
            if (file.exists() && file.isDirectory()) {
                dataPath = memoryPath[i] + ROOT_MAPS + foldername;
                break;
            } else {
                if (foldername.equals("")) {
                    file.mkdirs();
                }
            }
        }
        return dataPath;
    }
    /**获取otms下的文件夹*/
    public List<File> getOtmsFiles(){
        List<File> folds = new ArrayList<>();
        String otmspath = getFolderPath(otms);
        File[] files = new File(otmspath).listFiles();
        if(files == null ){
            return folds;
        }
        for (File file : files){
            if(file.exists() && file.isDirectory()){
                folds.add(file);
            }
        }
        return folds;
    }
    /**获取otms文件夹下的对应文件夹内的离线数据*/
    public HashMap<String,File> getOtmsFoldesFile(String path){
        HashMap<String,File> hashMap = new HashMap<>();
        File[] files = new File(path).listFiles();
        if(files == null){
            return hashMap;
        }
        if(files.length == 0){
            hashMap.put(path,null);
            return hashMap;
        }
        for (File file : files){
            if(file.exists() && file.isFile() && file.getName().endsWith(".geodatabase")){
                hashMap.put(path,file);
            }
        }
        return hashMap;
    }




}
