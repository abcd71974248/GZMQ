package com.hotsun.mqxxgl.gis.view;

import com.esri.arcgisruntime.geometry.Point;

/**
 * Created by li on 2017/10/26.
 *
 */

public interface ILocationView {

    Point getCurGpsPoint();

    Point getCurPoint();
}
