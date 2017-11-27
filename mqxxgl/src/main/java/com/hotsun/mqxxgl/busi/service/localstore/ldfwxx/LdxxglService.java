package com.hotsun.mqxxgl.busi.service.localstore.ldfwxx;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.util.SqliteDBHelper;
import java.util.ArrayList;
import java.util.List;


public class LdxxglService {


    /**
     * 增加楼栋信息
     * @param context
     * @param sql
     * @return
     */
    public static boolean addLdData(Context context,String sql){

        return SqliteDBHelper.addLdData(context,sql);
    }

    /**
     * 楼栋信息地图采集
     * @param context
     * @param ldid
     * @return
     */
    public static boolean collectLdxxMap(Context context,String ldid){


        String strSQL = "insert into ZT_FW_LDXX0 (LDID, ZBXXSFCJDM,FWXXSFCJDM) values(" + ldid + ",'1','0')";

        return SqliteDBHelper.addLdData(context,strSQL);
    }


    /**
      * 查找楼栋信息
      * @return 返回ldxx的lsit
      */
    public static List<FwLdxx> findLdxxList(Context context){
        List<FwLdxx> fwLdxxList=new ArrayList<FwLdxx>();

        String sqlStr="select * from FW_LDXX";
        SQLiteDatabase db= SqliteDBHelper.openDatabase(context,"LDFWGL.db");
        Cursor cursor=db.rawQuery(sqlStr,null);
        while(cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex("LDID"));
            String cunid=cursor.getString(cursor.getColumnIndex("CUNID"));
            String zuid=cursor.getString(cursor.getColumnIndex("ZUID"));
            String ldmc=cursor.getString(cursor.getColumnIndex("LDMC"));
            String ldaddr=cursor.getString(cursor.getColumnIndex("LDADDR"));
            String cellnum=cursor.getString(cursor.getColumnIndex("CELLNUM"));
            String floornum=cursor.getString(cursor.getColumnIndex("FLOORNUM"));
            String fwjgdm=cursor.getString(cursor.getColumnIndex("FWJGDM"));
            String xjrq=cursor.getString(cursor.getColumnIndex("XJRQ"));
            String zxlxdm=cursor.getString(cursor.getColumnIndex("ZXLXDM"));
            String zxrq=cursor.getString(cursor.getColumnIndex("ZXRQ"));
            String bz=cursor.getString(cursor.getColumnIndex("BZ"));
            String qrztdm=cursor.getString(cursor.getColumnIndex("QRZTDM"));
            String qrsj=cursor.getString(cursor.getColumnIndex("QRSJ"));
            FwLdxx ldxx=new FwLdxx();
            ldxx.setLdid(id);
            ldxx.setCunid(cunid);
            ldxx.setZuid(zuid);
            ldxx.setLdmc(ldmc);
            ldxx.setLdaddr(ldaddr);
            ldxx.setCellnum(cellnum);
            ldxx.setFloornum(floornum);
            ldxx.setFwjgdm(fwjgdm);
            ldxx.setXjrq(xjrq);
            ldxx.setZxlxdm(zxlxdm);
            ldxx.setZxrq(zxrq);
            ldxx.setBz(bz);
            ldxx.setQrztdm(qrztdm);
            ldxx.setQrsj(qrsj);
            fwLdxxList.add(ldxx);
        }
        cursor.close();
        db.close();
        return fwLdxxList;
    }

}