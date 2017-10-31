package com.hotsun.mqxxgl.gis.model;

import com.esri.arcgisruntime.geometry.SpatialReference;

/**
 * Created by li on 2017/10/27.
 * 投影类
 */

public class MySpatialReference {

    public static SpatialReference getMapSpatialReference(){
        return  SpatialReference.create(2343);
    }

}
