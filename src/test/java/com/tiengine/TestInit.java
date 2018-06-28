package com.tiengine;

import com.tiengine.controls.GButton;
import com.tiengine.controls.GControlFactory;
import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GSprite;
import com.tiengine.graphics.GTextArea;
import com.tiengine.utils.ResourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
            public GSprite newSprite() {
                return new GSprite();
            }

            @Override
            public GTextArea newTextArea() {
                return new GTextArea();
            }
        });
        ResourceFactory.setResourceFactory(new ResourceFactory.Interface() {
            @Override
            public InputStream loadScript(String name) throws FileNotFoundException{
                return new FileInputStream(name);
            }
        });
    }

}
