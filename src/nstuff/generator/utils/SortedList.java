package nstuff.generator.utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan.Ochincenko on 24.02.15.
 */
public class SortedList {

    private ArrayList list = new ArrayList();

    public Object first() {
        return list.get(0);
    }


    public void clear() {
        list.clear();
    }


    public void add(Object o) {
        list.add(o);
        Collections.sort(list);
    }

    public void remove(Object o) {
        list.remove(o);
    }


    public int size() {
        return list.size();
    }
    public ArrayList getList(){
        return list;
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }
}
