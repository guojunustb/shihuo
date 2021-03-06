package com.alibaba.json.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONObject;

public class SlashTest extends TestCase {
    public void test_0 () throws Exception {
        String text = "{\"errorMessage\":\"resource '/rpc/hello/none.json' is not found !\"}";
        JSONObject json = (JSONObject) JSON.parse(text);
        
        Assert.assertEquals("{\"errorMessage\":\"resource '/rpc/hello/none.json' is not found !\"}", json.toString());
    }
}
