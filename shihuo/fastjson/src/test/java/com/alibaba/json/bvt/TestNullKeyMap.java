package com.alibaba.json.bvt;

import java.util.HashMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;


public class TestNullKeyMap extends TestCase {
    public void test_0 () throws Exception {
        HashMap map = new HashMap();
        map.put(null, 123);
        
        String text = JSON.toJSONString(map);
        
        Assert.assertEquals("{null:123}", text);
        
        HashMap map2 = JSON.parseObject(text, HashMap.class);
        Assert.assertEquals(map.get(null), map2.get(null));
       
    }
}
