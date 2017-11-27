package com.hotsun.mqxxgl.busi.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jsqlite.Callback;
import jsqlite.Database;


public class SqliteDBHelper  {




    // 步骤1：设置常数参量
    private static final String DATABASE_NAME = "mqxxgl_db";
    private static final int VERSION = 1;
    private static String TABLE_NAME = "diary";

//    // 步骤2：重载构造方法
//    public SqliteDBHelper(Context context,String table_name) {
//        super(context, DATABASE_NAME, null, VERSION);
//        TABLE_NAME=table_name;
//    }
//
//    /*
//* 参数介绍：context 程序上下文环境 即：XXXActivity.this
//* name 数据库名字
//* factory 接收数据，一般情况为null
//* version 数据库版本号
//*/
//    public SqliteDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
//                          int version) {
//        super(context, name, factory, version);
//    }
//
//    private volatile static SqliteDBHelper uniqueInstance;
//    public static SqliteDBHelper getInstance(Context context,String table_name) {
//        if (uniqueInstance == null) {
//            synchronized (SqliteDBHelper.class) {
//                if (uniqueInstance == null) {
//                    uniqueInstance = new SqliteDBHelper(context,table_name);
//                }
//            }
//        }
//        return uniqueInstance;
//    }
//
//
//
//    //数据库第一次被创建时，onCreate()会被调用
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        // 步骤3：数据库表的创建
//        String strSQL = "create table "
//                + TABLE_NAME
//                + "(ldid integer primary key autoincrement,cunid integer,zuid integer,ldmc varchar(40),ldaddr varchar(80)," +
//                "cellnum integer,floornum integer,fwjgdm varchar(2),xjrq date,zxlxdm varchar(2),zxrq date,bz varchar(30)," +
//                "qrztdm varchar(2),qrsj date,qrrxm varchar(30),adddatetime date,adduser varchar(30),chgdatetime datetime," +
//                "chguser varchar(30),lastts varchar(20),rflag integer)";
//        //步骤4：使用参数db,创建对象
//        db.execSQL(strSQL);
//    }
//
//    //数据库版本变化时，会调用onUpgrade()
//    @Override
//    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
//
//    }


    public static SQLiteDatabase openDatabase(Context context,String dataname) {

        try {
            // 获得文件的绝对路径
            String DATABASE_PATH = ResourcesUtil.getInstance().getFolderPath(context,ResourcesUtil.getInstance().db);
            String DATABASE_FILENAME=dataname;
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;

            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return database;
        } catch (Exception e) {
            Log.i("open error", e.getMessage());
        }
        return null;
    }

    public static boolean addLdData(Context context,String sql,Callback cb){
        try {
            ResourcesUtil resourcesUtil = ResourcesUtil.getInstance();
            File databaseName = resourcesUtil.getYwSqlite(context,"LDFWGL.db");
            Class.forName("jsqlite.JDBCDriver").newInstance();
            Database db = new jsqlite.Database();
            db.open(databaseName.getPath(), jsqlite.Constants.SQLITE_OPEN_READWRITE);
            db.exec(sql, cb);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean addLdData(Context context,String sql){
        try {
            ResourcesUtil resourcesUtil = ResourcesUtil.getInstance();
            File databaseName = resourcesUtil.getYwSqlite(context,"LDFWGL.db");
            Class.forName("jsqlite.JDBCDriver").newInstance();
            Database db = new jsqlite.Database();
            db.open(databaseName.getPath(), jsqlite.Constants.SQLITE_OPEN_READWRITE);
            db.exec(sql, null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
          * 查找数据库中所有的数据
          * @return 返回persons的lsit
          */
    public static List<FwLdxx> findAll(Context context){
        List<FwLdxx> persons=new ArrayList<FwLdxx>();

       SQLiteDatabase db=openDatabase(context,"LDFWGL.db");
        Cursor cursor=db.rawQuery("select * from FW_LDXX",null);
        while(cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("ldid"));
//                String name=cursor.getString(cursor.getColumnIndex("name"));
//                String number=cursor.getString(cursor.getColumnIndex("number"));
                FwLdxx person=new FwLdxx();
            person.setLdid(String.valueOf(id));
                persons.add(person);
            }
        cursor.close();
        db.close();
        return persons;
    }



}