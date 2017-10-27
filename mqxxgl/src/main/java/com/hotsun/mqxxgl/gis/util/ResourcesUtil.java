package com.hotsun.mqxxgl.gis.util;

import android.content.Context;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by li on 2017/10/27.
 * 获取本地文件工具类
 */

public class ResourcesUtil {


    private Context mContext;
    private ResourcesUtil resourcesUtil;

    private final String ROOT_MAPS = "/gzfp";
    private final String otitan_map = "/otitan.map";


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
        String basePath = otitan_map + "title.tpk";
        return getFilePath(basePath);
    }

    /** 取文件可用地址 */
    private String getFilePath(String path) {
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


}
