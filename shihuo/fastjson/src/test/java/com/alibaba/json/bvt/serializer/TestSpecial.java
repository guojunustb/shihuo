package com.alibaba.json.bvt.serializer;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;

public class TestSpecial extends TestCase {
    public void test_0 () throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "\n\r\t");
        System.out.println(JSON.toJSONString(map, SerializerFeature.WriteTabAsSpecial));
    }
}
