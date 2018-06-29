package com.tiengine.graphics;

import java.nio.Buffer;

/**
 * Created by olegst on 27.06.18.
 */

public interface GDrawable {
    void move(float x, float y, float z);
    void moveAbs(float x, float y, float z);

    void rotate(float x, float y, float z, float angle);
    void rotateAbs(float x, float y, float z, float angle);

    // if buffer is null, return size it'll take
    // otherwise fill the buffer and return size
    int prepare(Buffer buffer, int offset);

    void render();
}
