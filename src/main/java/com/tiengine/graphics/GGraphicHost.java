package com.tiengine.graphics;

import java.util.LinkedList;
import java.util.List;

public class GGraphicHost {
    List<GSprite> __sprites = new LinkedList<>();
    public void putSprite(GSprite sprite) {
        __sprites.add(sprite);
    }
    public void removeSprite(GSprite sprite) {
        __sprites.remove(sprite);
    }

    List<GTextArea> __texts = new LinkedList<>();
    public void putTextArea(GTextArea text) {
        __texts.add(text);
    }
    public void removeTextArea(GTextArea text) {
        __texts.remove(text);
    }

    //public void
}
