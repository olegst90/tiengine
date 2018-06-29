package com.tiengine.graphics;

/**
 * Created by olegst on 27.06.18.
 */

public class GGraphicFactory {
    public interface Interface {
        public GStillSprite newStillSprite();
        public GDynamicSprite newDynamicSprite();
        public GTextArea newTextArea();
    }

    private static Interface __interface;
    public static void setGraphicFactory(Interface iface) {
        __interface = iface;
    }
    public static Interface graphicFactory() {
        return __interface;
    }
}
