package com.alibaba.json.bvt.parser.deser;

import java.util.TreeMap;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;

public class TreeMapDeserializerTest extends TestCase {
    public void test_0 () throws Exception {
        TreeMap treeMap = JSON.parseObject("{}", TreeMap.class);
        Assert.assertEquals(0, treeMap.size());
    }
}
