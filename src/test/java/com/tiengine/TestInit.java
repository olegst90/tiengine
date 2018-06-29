package com.tiengine;

import com.tiengine.controls.GButton;
import com.tiengine.controls.GControlFactory;
import com.tiengine.graphics.GDynamicSprite;
import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GSprite;
import com.tiengine.graphics.GStillSprite;
import com.tiengine.graphics.GTextArea;
import com.tiengine.utils.GResourceFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

/**
 * Created by olegst on 28.06.18.
 */

public class TestInit {
    static void init() {
        //test factories
        GControlFactory.setControlFactory(new GControlFactory.Interface() {
            @Override
            public GButton newButton() {
                return new GButton();
            }
        });
        GGraphicFactory.setGraphicFactory(new GGraphicFactory.Interface() {
            @Override
            public GStillSprite newStillSprite() {
                return new GStillSprite() {
                    @Override
                    public void move(float x, float y, float z) {
                    }
                    @Override
                    public void moveAbs(float x, float y, float z) {
                    }
                    @Override
                    public void rotate(float x, float y, float z, float angle) {
                    }
                    @Override
                    public void rotateAbs(float x, float y, float z, float angle) {
                    }
                    @Override
                    public int prepare(Buffer buffer, int offset) {
                        return 0;
                    }
                    @Override
                    public void render() {
                    }
                };
            }
            @Override
            public GDynamicSprite newDynamicSprite() {
                return new GDynamicSprite() {
                    @Override
                    public void move(float x, float y, float z) {
                    }
                    @Override
                    public void moveAbs(float x, float y, float z) {
                    }
                    @Override
                    public void rotate(float x, float y, float z, float angle) {
                    }
                    @Override
                    public void rotateAbs(float x, float y, float z, float angle) {
                    }
                    @Override
                    public int prepare(Buffer buffer, int offset) {
                        return 0;
                    }
                    @Override
                    public void render() {
                    }
                };
            }
            @Override
            public GTextArea newTextArea() {
                return new GTextArea() {
                    @Override
                    public void move(float x, float y, float z) {
                    }
                    @Override
                    public void moveAbs(float x, float y, float z) {
                    }
                    @Override
                    public void rotate(float x, float y, float z, float angle) {
                    }
                    @Override
                    public void rotateAbs(float x, float y, float z, float angle) {
                    }
                    @Override
                    public int prepare(Buffer buffer, int offset) {
                        return 0;
                    }
                    @Override
                    public void render() {
                    }
                };
            }
        });
        GResourceFactory.setResourceFactory(new GResourceFactory.Interface() {
            @Override
            public InputStream loadScript(String name) throws FileNotFoundException{
                return new FileInputStream(name);
            }
            @Override
            public int loadTexture(String name) {return 0;}

            @Override
            public void reloadTextures() throws IOException {}
        });
    }

}
