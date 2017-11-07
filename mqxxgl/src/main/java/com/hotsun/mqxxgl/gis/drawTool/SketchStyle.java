package com.hotsun.mqxxgl.gis.drawTool;

import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.symbol.TextSymbol;

/**
 * Created by li on 2017/11/7.
 * SketchStyle 
 */

public class SketchStyle {
    private static final int DEFAULT_VERTEX_COLOR = -65536;
    private static final int DEFAULT_VERTEX_OUTLINE_COLOR = -1;
    private static final int DEFAULT_MID_VERTEX_COLOR = -1;
    private static final int DEFAULT_MID_VERTEX_OUTLINE_COLOR = -23645;
    private static final int DEFAULT_SELECTED_VERTEX_COLOR = -65536;
    private static final int DEFAULT_SELECTED_MID_VERTEX_COLOR = -1;
    private static final int DEFAULT_SELECTED_VERTEX_OUTLINE_COLOR = -1;
    private static final int DEFAULT_SELECTED_MID_VERTEX_OUTLINE_COLOR = -23645;
    private static final int DEFAULT_FILL_COLOR = 1090495395;
    private static final int DEFAULT_LINE_COLOR = -65536;
    private static final int DEFAULT_SELECTION_HALO_COLOR = -16711681;
    private int mSelectionColor = -16711681;
    private boolean mShowNumbersForVertices = false;
    private MarkerSymbol mVertexSymbol;
    private MarkerSymbol mSelectedVertexSymbol;
    private LineSymbol mLineSymbol;
    private FillSymbol mFillSymbol;
    private MarkerSymbol mFeedbackVertexSymbol;
    private LineSymbol mFeedbackLineSymbol;
    private FillSymbol mFeedbackFillSymbol;
    private MarkerSymbol mMidVertexSymbol;
    private MarkerSymbol mSelectedMidVertexSymbol;
    private TextSymbol mVertexTextSymbol;

    public SketchStyle() {
        SimpleLineSymbol var1 = new SimpleLineSymbol(-65536, 1.0F, SimpleLineSymbol.STYLE.SOLID);
        this.mVertexSymbol = new SimpleMarkerSymbol(-65536, 14,SimpleMarkerSymbol.STYLE.SQUARE);
        ((SimpleMarkerSymbol) this.mVertexSymbol).setOutline(new SimpleLineSymbol( -1, 1.0F,SimpleLineSymbol.STYLE.SOLID));
        this.mSelectedVertexSymbol = new SimpleMarkerSymbol(-65536, 14,SimpleMarkerSymbol.STYLE.SQUARE);
        ((SimpleMarkerSymbol) this.mSelectedVertexSymbol).setOutline(new SimpleLineSymbol(-1, 1.0F,SimpleLineSymbol.STYLE.SOLID));
        this.mLineSymbol = new SimpleLineSymbol(-65536, 1.0F,SimpleLineSymbol.STYLE.SOLID);
        this.mFillSymbol = new SimpleFillSymbol(1090495395,SimpleFillSymbol.STYLE.SOLID);
        this.mFillSymbol.setOutline(var1);
        this.mFeedbackVertexSymbol = new SimpleMarkerSymbol(-65536, 28,SimpleMarkerSymbol.STYLE.CIRCLE);
        ((SimpleMarkerSymbol) this.mFeedbackVertexSymbol).setOutline(var1);
        this.mFeedbackLineSymbol = new SimpleLineSymbol(-65536, 1.0F,SimpleLineSymbol.STYLE.DASH);
        this.mFeedbackFillSymbol = new SimpleFillSymbol(1090495395,SimpleFillSymbol.STYLE.SOLID);
        this.mFeedbackFillSymbol.setOutline(var1);
        this.mMidVertexSymbol = new SimpleMarkerSymbol(-1, 10,SimpleMarkerSymbol.STYLE.CIRCLE);
        ((SimpleMarkerSymbol) this.mMidVertexSymbol).setOutline(new SimpleLineSymbol( -23645, 1.0F,SimpleLineSymbol.STYLE.SOLID));
        this.mSelectedMidVertexSymbol = new SimpleMarkerSymbol( -1, 10,SimpleMarkerSymbol.STYLE.CIRCLE);
        ((SimpleMarkerSymbol) this.mSelectedMidVertexSymbol).setOutline(new SimpleLineSymbol( -23645, 1.0F,SimpleLineSymbol.STYLE.SOLID));
        this.mVertexTextSymbol = new TextSymbol(10, "0", -1, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.MIDDLE);
    }

    public int getSelectionColor() {
        return this.mSelectionColor;
    }

    public void setSelectionColor(int selectionColor) {
        if (selectionColor != this.mSelectionColor) {
            this.mSelectionColor = selectionColor;
        }
    }
    public boolean isShowNumbersForVertices() {
        return this.mShowNumbersForVertices;
    }

    public void setShowNumbersForVertices(boolean showNumbersForVertices) {
        if (showNumbersForVertices != this.mShowNumbersForVertices) {
            this.mShowNumbersForVertices = showNumbersForVertices;
        }

    }

    public FillSymbol getFillSymbol() {
        return this.mFillSymbol;
    }

    public void setFillSymbol(FillSymbol fillSymbol) {
        this.mFillSymbol = fillSymbol;
    }

    public LineSymbol getLineSymbol() {
        return this.mLineSymbol;
    }

    public void setLineSymbol(LineSymbol lineSymbol) {
        this.mLineSymbol = lineSymbol;
    }

    public Symbol getVertexSymbol() {
        return this.mVertexSymbol;
    }

    public void setVertexSymbol(MarkerSymbol vertexSymbol) {
        this.mVertexSymbol = vertexSymbol;
    }

    public FillSymbol getFeedbackFillSymbol() {
        return this.mFeedbackFillSymbol;
    }

    public void setFeedbackFillSymbol(FillSymbol feedbackFillSymbol) {
        this.mFeedbackFillSymbol = feedbackFillSymbol;
    }

    public LineSymbol getFeedbackLineSymbol() {
        return this.mFeedbackLineSymbol;
    }

    public void setFeedbackLineSymbol(LineSymbol feedbackLineSymbol) {
        this.mFeedbackLineSymbol = feedbackLineSymbol;
    }

    public MarkerSymbol getFeedbackVertexSymbol() {
        return this.mFeedbackVertexSymbol;
    }

    public void setFeedbackVertexSymbol(MarkerSymbol feedbackVertexSymbol) {
        this.mFeedbackVertexSymbol = feedbackVertexSymbol;
    }

    public MarkerSymbol getMidVertexSymbol() {
        return this.mMidVertexSymbol;
    }

    public void setMidVertexSymbol(MarkerSymbol midVertexSymbol) {
        this.mMidVertexSymbol = midVertexSymbol;
    }

    public MarkerSymbol getSelectedMidVertexSymbol() {
        return this.mSelectedMidVertexSymbol;
    }

    public void setSelectedMidVertexSymbol(MarkerSymbol selectedMidVertexSymbol) {
        this.mSelectedMidVertexSymbol = selectedMidVertexSymbol;
    }

    public MarkerSymbol getSelectedVertexSymbol() {
        return this.mSelectedVertexSymbol;
    }

    public void setSelectedVertexSymbol(MarkerSymbol selectedVertexSymbol) {
        this.mSelectedVertexSymbol = selectedVertexSymbol;
    }

    public TextSymbol getVertexTextSymbol() {
        return this.mVertexTextSymbol;
    }

    public void setVertexTextSymbol(TextSymbol vertexTextSymbol) {
        this.mVertexTextSymbol = vertexTextSymbol;
    }
}
