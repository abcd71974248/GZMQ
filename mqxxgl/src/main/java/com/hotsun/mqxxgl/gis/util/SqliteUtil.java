package com.hotsun.mqxxgl.gis.util;

import android.content.Context;
import com.hotsun.mqxxgl.gis.model.Gjpoint;
import java.io.File;
import jsqlite.Database;

/**
 * Created by li on 2017/11/8.
 * SqliteUtil 操作本地数据库
 */

public class SqliteUtil {

    /**
     * 添加离线轨迹数据
     */
    public static boolean addLocalPoint(Context context, Gjpoint gjpoint) {
        try {
            ResourcesUtil resourcesUtil = ResourcesUtil.getInstance();
            File databaseName = resourcesUtil.getDbSqlite(context);
            Class.forName("jsqlite.JDBCDriver").newInstance();
            Database db = new jsqlite.Database();
            db.open(databaseName.getPath(), jsqlite.Constants.SQLITE_OPEN_READWRITE);
            String sql = "insert into point values(null," + gjpoint.getLon() + "," + gjpoint.getLat()
                    + ",'" + gjpoint.getSbh() + "','" + gjpoint.getTime() + "'," + gjpoint.getState()
                    + ",geomfromtext('POINT(" + gjpoint.getLon() + " " + gjpoint.getLat() + ")',2343))";
            db.exec(sql, null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
