package com.tiengine.graphics;

import java.util.LinkedList;
import java.util.List;

public class GGraphicHost {
    public static class State {
        List<GSprite> __sprites = new LinkedList<>();
        List<GTextArea> __texts = new LinkedList<>();
    }

    State __state = new State();

    public void setState(State state) {__state = state;}
    public State getState() {return __state;}

    public void putSprite(GSprite sprite) {
        __state.__sprites.add(sprite);
    }
    public void removeSprite(GSprite sprite) {__state.__sprites.remove(sprite);}


    public void putTextArea(GTextArea text) {
        __state.__texts.add(text);
    }
    public void removeTextArea(GTextArea text) {
        __state.__texts.remove(text);
    }

    //public void
}
