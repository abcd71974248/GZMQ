package com.hotsun.mqxxgl.gis.drawTool;

/**
 * Created by li on 2017/11/7.
 * DrawToolImpl
 */

public class DrawToolImpl {

    private DrawTool drawTool;
    private SketchStyle mSketchStyle;

    public DrawToolImpl(DrawTool drawTool){
        this.mSketchStyle = new SketchStyle();
        this.drawTool = drawTool;
    }



}
