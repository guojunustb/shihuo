package com.alibaba.json.bvt.parser.deser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.TypeReference;

public class ArrayListTypeDeserializerTest extends TestCase {

    public void test_null() throws Exception {
        Assert.assertNull(JSON.parseObject("null", new TypeReference<ArrayList<Integer>>() {
        }));
        Assert.assertNull(JSON.parseObject("null", new TypeReference<Collection<Integer>>() {
        }));
        Assert.assertNull(JSON.parseObject("{\"value\":null}", VO.class).getValue());
        Assert.assertNull(JSON.parseObject("{\"value\":null}", V1.class).getValue());
    }
    
    public void test_empty() throws Exception {
        Assert.assertEquals(0, JSON.parseObject("[]", new TypeReference<ArrayList<Integer>>() {
        }).size());
        Assert.assertEquals(0, JSON.parseObject("[]", new TypeReference<Set<Integer>>() {
        }).size());
        
        Assert.assertEquals(0, JSON.parseObject("[]", new TypeReference<HashSet<Integer>>() {
        }).size());
        
        Assert.assertEquals(0, JSON.parseObject("{\"value\":[]}", VO.class).getValue().size());
    }

    public static class VO {

        private ArrayList<Integer> value;

        public ArrayList<Integer> getValue() {
            return value;
        }

        public void setValue(ArrayList<Integer> value) {
            this.value = value;
        }

    }
    
    private static class V1 {

        private ArrayList<Integer> value;

        public ArrayList<Integer> getValue() {
            return value;
        }

        public void setValue(ArrayList<Integer> value) {
            this.value = value;
        }

    }
}
