package com.tiengine.controls;

/**
 * Created by olegst on 6/27/18.
 */

public class GControl {
    public interface GControlCallback {
        void call();
    }
    GControlCallback __cb;
    public void setCallback(GControlCallback cb) {
        __cb = cb;
        cb.call();
    }
}
