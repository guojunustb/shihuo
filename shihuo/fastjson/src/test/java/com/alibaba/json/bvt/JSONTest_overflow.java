package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.serializer.SerializeConfig;

public class JSONTest_overflow extends TestCase {

    public void test_overflow() throws Exception {
        Entity entity = new Entity();
        entity.setSelf(entity);

        String text = JSON.toJSONString(entity, SerializeConfig.getGlobalInstance());
        Entity entity2 = JSON.parseObject(text, Entity.class);
        Assert.assertTrue(entity2 == entity2.getSelf());
    }
    
    public void test_overflow_1() throws Exception {
        Entity entity = new Entity();
        entity.setSelf(entity);

        String text = JSON.toJSONStringZ(entity, SerializeConfig.getGlobalInstance());
        Entity entity2 = JSON.parseObject(text, Entity.class);
        Assert.assertTrue(entity2 == entity2.getSelf());
    }

    public static class Entity {

        private Entity self;

        public Entity getSelf() {
            return self;
        }

        public void setSelf(Entity self) {
            this.self = self;
        }

    }
}
