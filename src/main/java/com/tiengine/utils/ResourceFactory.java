package com.tiengine.utils;

/**
 * Created by olegst on 28.06.18.
 */
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceFactory {
    public interface Interface {
        InputStream loadScript(String name) throws FileNotFoundException;
    }

    private static Interface __interface;
    public static void setResourceFactory(Interface iface) {
        __interface = iface;
    }
    public static Interface resourceFactory() {
        return __interface;
    }
}
