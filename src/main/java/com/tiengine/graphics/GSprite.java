package com.tiengine.graphics;

/**
 * Created by olegst on 27.06.18.
 */

public abstract class GSprite implements GDrawable {
    int __texId;
    public void setTexture(int id) {
        __texId = id;
    }
}
