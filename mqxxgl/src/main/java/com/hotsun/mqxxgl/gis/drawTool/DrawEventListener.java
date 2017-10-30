package com.hotsun.mqxxgl.gis.drawTool;

import java.util.EventListener;

/**
 * 事件监听
 */
public interface DrawEventListener extends EventListener {

	void handleDrawEvent(DrawEvent event);
}
