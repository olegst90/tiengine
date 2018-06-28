package com.tiengine.controls;

import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GGraphicHost;
import com.tiengine.graphics.GSprite;
import com.tiengine.graphics.GTextArea;

/**
 * Created by olegst on 6/27/18.
 */

public class GButton extends GControl {
    GTextArea __text;
    GSprite __sprite;

    public GButton() {
        __text = GGraphicFactory.graphicFactory().newTextArea();
        __sprite = GGraphicFactory.graphicFactory().newSprite();
    }

    @Override
    public void attachToGraphicHost(GGraphicHost host) {
         host.putSprite(__sprite);
         host.putTextArea(__text);
    }

    @Override
    public void detachFromGraphicHost(GGraphicHost host) {
        host.removeSprite(__sprite);
        host.removeTextArea(__text);
    }

    public void setText(String text) {
        __text.setText(text);
    }

    public interface GButtonCallback {
        void call();
    }

    GButtonCallback __cb;
    public void setCallback(GButtonCallback cb) {
        __cb = cb;
    }

    public void press() {
        __cb.call();
    }
}
