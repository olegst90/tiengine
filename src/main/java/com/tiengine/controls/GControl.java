package com.tiengine.controls;


import com.tiengine.graphics.GGraphicHost;

/**
 * Created by olegst on 28.06.18.
 */

public abstract class GControl {
    abstract void attachToGraphicHost(GGraphicHost host);
    abstract void detachFromGraphicHost(GGraphicHost host);
}
