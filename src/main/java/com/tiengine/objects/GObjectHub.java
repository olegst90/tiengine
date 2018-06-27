package com.tiengine.objects;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public class GObjectHub {
    List<GObject> __pool = new ArrayList<GObject>();
    Map<Integer, List<GObject>> __tagged_pool = new HashMap<Integer,List<GObject>>();

    public void putObject(GObject object, int tag) {
        object.setTag(tag);
        __pool.add(object);
        List<GObject> cluster = __tagged_pool.get(new Integer(tag));

        if (cluster == null) {
            cluster = new ArrayList<GObject>();
            __tagged_pool.put(new Integer(tag), cluster);
        }

        cluster.add(object);
    }

    public void removeObject(GObject object) {
        __pool.remove(object);
        __tagged_pool.get(new Integer(object.getTag())).remove(object);
    }

    public void removeCluster(int tag) {
        List<GObject> cluster = __tagged_pool.get(tag);

        if (cluster == null) {
            throw new IllegalArgumentException("no tag");
        }

        Iterator<GObject> i = cluster.iterator();
        while(i.hasNext())
            __pool.remove(i.next());

        __tagged_pool.remove(tag);
    }

    public void interactInCluster(int tag) {
        List<GObject> cluster = __tagged_pool.get(tag);
        for (int i = 0; i < cluster.size() - 1; i++)
            for (int j = i + 1; j < cluster.size(); j++) {
                cluster.get(i).interact(cluster.get(j));
                cluster.get(j).interact(cluster.get(i));
            }
    }

    public void interactBetweenClusters(int tag1, int tag2, boolean mutual) throws IllegalArgumentException {
        if (tag1 == tag2) throw new IllegalArgumentException("Bad value");
        List<GObject> cluster1 = __tagged_pool.get(tag1);
        List<GObject> cluster2 = __tagged_pool.get(tag2);
        for (int i = 0; i < cluster1.size(); i++)
            for (int j = 0; j < cluster2.size(); j++) {
                cluster1.get(i).interact(cluster2.get(j));
                if (mutual)
                    cluster2.get(j).interact(cluster1.get(i));
            }
    }

    public int objectCount() {
        return __pool.size();
    }

    public int objectCount(int tag) {
        return __tagged_pool.get(tag).size();
    }
}
