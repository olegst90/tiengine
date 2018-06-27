package com.tiengine.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GObject {
    static Logger logger = LoggerFactory.getLogger(GObject.class);
    int __tag;
    void setTag(int tag) {__tag = tag;}
    int getTag() {return __tag;}
    public void interact(GObject other) {
        logger.info("{}:{} -> {}:{}", __tag, hashCode(), other.getTag(), other.hashCode());
    }
}
