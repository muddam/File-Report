package com.fileReport.utility;

import java.util.ArrayList;
import java.util.HashMap;


public class CommonHash {


    public static <KeyClass, ValueClass> void hashInsert(HashMap<KeyClass,ArrayList<ValueClass>> map, Object key, Object value){

        try {
            KeyClass kc = (KeyClass) key;
            ValueClass vc = (ValueClass) value;

            ArrayList<ValueClass> collectionVc;
            if (!map.containsKey(kc)) {
                collectionVc = new ArrayList<>();
                collectionVc.add(vc);
                map.put(kc, collectionVc);

            } else {
                collectionVc = map.get(kc);
                collectionVc.add(vc);
                map.put(kc, collectionVc);

            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

    }

