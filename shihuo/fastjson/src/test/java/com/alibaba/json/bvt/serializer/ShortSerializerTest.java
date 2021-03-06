package com.alibaba.json.bvt.serializer;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializerFeature;


public class ShortSerializerTest extends TestCase {
    
    public void test_null() throws Exception {
        VO vo = new VO();
        
        Assert.assertEquals("{\"value\":null}", JSON.toJSONString(vo, SerializerFeature.WriteMapNullValue));
    }

    private static class VO {

        private Short value;

        public Short getValue() {
            return value;
        }

        public void setValue(Short value) {
            this.value = value;
        }

    }
}
