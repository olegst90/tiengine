package com.tiengine.utils;

/**
 * Created by olegst on 28.06.18.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GResourceFactory {
    public interface Interface {
        InputStream loadScript(String name) throws FileNotFoundException;

        // load texture and get it's persistent id
        // which will survive context loss
        int loadTexture(String name) throws IOException;

        // reload textures after context loss
        void reloadTextures() throws IOException;
    }

    private static Interface __interface;
    public static void setResourceFactory(Interface iface) {
        __interface = iface;
    }
    public static Interface resourceFactory() {
        return __interface;
    }
}
