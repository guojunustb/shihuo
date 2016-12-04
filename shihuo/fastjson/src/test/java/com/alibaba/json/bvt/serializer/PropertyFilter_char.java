package com.alibaba.json.bvt.serializer;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.PropertyFilter;
import com.alibaba.json.serializer.SerializeWriter;

public class PropertyFilter_char extends TestCase {

    public void test_0() throws Exception {
        PropertyFilter filter = new PropertyFilter() {

            public boolean apply(Object source, String name, Object value) {
                return false;
            }
        };

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(filter);

        A a = new A();
        serializer.write(a);

        String text = out.toString();
        Assert.assertEquals("{}", text);
    }

    public void test_1() throws Exception {
        PropertyFilter filter = new PropertyFilter() {

            public boolean apply(Object source, String name, Object value) {
                if ("id".equals(name)) {
                    Assert.assertTrue(value instanceof Character);
                    return true;
                }
                return false;
            }
        };

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(filter);

        A a = new A();
        serializer.write(a);

        String text = out.toString();
        Assert.assertEquals("{\"id\":\"0\"}", text);
    }

    public void test_2() throws Exception {
        PropertyFilter filter = new PropertyFilter() {

            public boolean apply(Object source, String name, Object value) {
                if ("name".equals(name)) {
                    return true;
                }
                return false;
            }
        };

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(filter);

        A a = new A();
        a.setName("chennp2008");
        serializer.write(a);

        String text = out.toString();
        Assert.assertEquals("{\"name\":\"chennp2008\"}", text);
    }

    public void test_3() throws Exception {
        PropertyFilter filter = new PropertyFilter() {

            public boolean apply(Object source, String name, Object value) {
                if ("name".equals(name)) {
                    return true;
                }
                return false;
            }
        };

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(filter);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "chennp2008");
        serializer.write(map);

        String text = out.toString();
        Assert.assertEquals("{\"name\":\"chennp2008\"}", text);
    }

    public void test_4() throws Exception {
        PropertyFilter filter = new PropertyFilter() {

            public boolean apply(Object source, String name, Object value) {
                if ("name".equals(name)) {
                    return false;
                }
                return true;
            }
        };

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.getPropertyFilters().add(filter);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", (char) '3');
        map.put("name", "chennp2008");
        serializer.write(map);

        String text = out.toString();
        Assert.assertEquals("{\"id\":\"3\"}", text);
    }

    public static class A {

        private char   id = '0';
        private String name;

        public char getId() {
            return id;
        }

        public void setId(char id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}