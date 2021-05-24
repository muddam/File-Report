package com.fileReport.utility;

import com.fileReport.service.Processor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CommonHashTest {
    HashMap<Integer, ArrayList<String>> map;
    String key;
    String value;
    ArrayList<String> dummy = new ArrayList<>();

    @BeforeEach
    void setUp() {
         map = new HashMap<>();
         ArrayList<String> valueList = new ArrayList<>();
         valueList.add("Hello");
         map.put(1,valueList);
         key = "key";
         value = "value1";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void hashInsert() {
        CommonHash.hashInsert(map,1,value);
        CommonHash.hashInsert(map,2,value);
        assert map.containsKey(1);

        CommonHash.hashInsert(map,key,34);

    }
}