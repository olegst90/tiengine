package com.tiengine.graphics;

/**
 * Created by olegst on 27.06.18.
 */

public class GDrawable {
    public class Rectangle {
        int x,y,w,h;
        public Rectangle(int x, int y, int w, int h) {
            this.x = x; this.y = y; this.w = w; this.h = h;
        }
        public Rectangle() {

        }
    }
    Rectangle __position = new Rectangle();

    public void setPosition(Rectangle position) {
        __position = position;
    }

    public void setPosition(int x, int y) {
        __position.x = x;
        __position.y = y;
    }

    public void render() {

    }
}
