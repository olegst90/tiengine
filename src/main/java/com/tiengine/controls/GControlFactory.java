package com.tiengine.controls;

/**
 * Created by olegst on 27.06.18.
 */

public class GControlFactory {
    public interface Interface {
        public GButton newButton();
    }

    private static Interface __interface;
    public static void setControlFactory(Interface iface) {
        __interface = iface;
    }
    public static Interface controlFactory() {
        return __interface;
    }
}
