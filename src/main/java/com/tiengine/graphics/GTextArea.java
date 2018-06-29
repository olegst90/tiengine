package com.tiengine.graphics;

/**
 * Created by olegst on 27.06.18.
 */

public abstract class GTextArea implements GDrawable {
    String __text;
    public void setText(String text) {
        __text = text;
    }
}
