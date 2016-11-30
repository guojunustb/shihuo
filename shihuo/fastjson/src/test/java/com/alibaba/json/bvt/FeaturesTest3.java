package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.annotation.JSONField;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class FeaturesTest3 extends TestCase {

    public void test_0() throws Exception {
        SerializeConfig config = new SerializeConfig();
        config.setAsmEnable(false);
        
        String text = JSON.toJSONString(new Entity(), config);
        Assert.assertEquals("{\"value\":0}", text);
    }
    
    public void test_1() throws Exception {
        SerializeConfig config = new SerializeConfig();
        config.setAsmEnable(true);
        
        String text = JSON.toJSONString(new Entity(), config);
        Assert.assertEquals("{\"value\":0}", text);
    }

    public static class Entity {

        private Integer value;

        @JSONField(serialzeFeatures = { SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero })
        public Integer getValue() {
            return value;
        }


    }
}
