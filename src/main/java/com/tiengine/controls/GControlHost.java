package com.tiengine.controls;

import com.tiengine.graphics.GGraphicHost;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olegst on 28.06.18.
 */

public class GControlHost {
    public static class State {
        List<GControl> __controls = new LinkedList<>();
    }

    State __state = new State();
    public void setState(State state) {__state = state;}
    public State getState() {return __state;}

    GGraphicHost __graphic_host;
    public GControlHost(GGraphicHost graphicHost) {
        __graphic_host = graphicHost;
    }



    public void putControl(GControl c) {
        __state.__controls.add(c);
        c.attachToGraphicHost(__graphic_host);
    }

    public void removeControl(GControl c) {
        __state.__controls.remove(c);
    }
}
