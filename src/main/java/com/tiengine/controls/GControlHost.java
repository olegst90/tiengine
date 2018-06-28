package com.tiengine.controls;

import com.tiengine.graphics.GGraphicHost;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olegst on 28.06.18.
 */

public class GControlHost {
    GGraphicHost __graphic_host;
    public GControlHost(GGraphicHost graphicHost) {
        __graphic_host = graphicHost;
    }

    List<GControl> __controls = new LinkedList<>();

    public void putControl(GControl c) {
        __controls.add(c);
        c.attachToGraphicHost(__graphic_host);
    }

    public void removeControl(GControl c) {
        __controls.remove(c);
    }
}
