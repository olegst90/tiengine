package com.tiengine.graphics;

import java.util.LinkedList;
import java.util.List;
//todo make abstract
public class GGraphicHost {
    public static class State {
        public List<GStillSprite> __still_sprites = new LinkedList<>();
        public List<GDynamicSprite> __dyn_sprites = new LinkedList<>();
        public List<GTextArea> __texts = new LinkedList<>();
    }

    protected State __state = new State();

    public void setState(State state) {__state = state;}
    public State getState() {return __state;}

    public void prepare() {}
    public void render() {}

    public void putStillSprite(GStillSprite sprite) {
        __state.__still_sprites.add(sprite);
    }
    public void removeStillSprite(GStillSprite sprite) {__state.__still_sprites.remove(sprite);}

    public void putDynamicSprite(GDynamicSprite sprite) {
        __state.__dyn_sprites.add(sprite);
    }
    public void removeDynamicSprite(GDynamicSprite sprite) {__state.__dyn_sprites.remove(sprite);}


    public void putTextArea(GTextArea text) {
        __state.__texts.add(text);
    }
    public void removeTextArea(GTextArea text) {
        __state.__texts.remove(text);
    }


}
