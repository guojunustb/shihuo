package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.annotation.JSONField;

public class AnnotationTest3 extends TestCase {

    public void test_supperField() throws Exception {
        C c = new C();
        c.setId(123);
        c.setName("jobs");

        String str = JSON.toJSONString(c);
        Assert.assertEquals("{\"ID\":123,\"name\":\"jobs\"}", str);
    }

    public static class S {

        @JSONField(name = "ID")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

    public static class C extends S {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}