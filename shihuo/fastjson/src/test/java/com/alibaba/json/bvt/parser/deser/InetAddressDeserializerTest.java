package com.alibaba.json.bvt.parser.deser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.parser.DefaultExtJSONParser;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.parser.deserializer.InetAddressDeserializer;
import com.alibaba.json.parser.deserializer.StringDeserializer;

public class InetAddressDeserializerTest extends TestCase {

    public void test_null() throws Exception {
        String input = "null";
        DefaultExtJSONParser parser = new DefaultExtJSONParser(input, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);

        InetAddressDeserializer deser = new InetAddressDeserializer();

        Assert.assertNull(deser.deserialze(parser, null, null));
    }
    
    public void test_string_null() throws Exception {
        String input = "null";
        DefaultExtJSONParser parser = new DefaultExtJSONParser(input, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
        
        StringDeserializer deser = new StringDeserializer();
        
        Assert.assertNull(deser.deserialze(parser, null, null));
    }

    public void test_error_0() throws Exception {
        String input = "'[&中国-^]'";
        DefaultExtJSONParser parser = new DefaultExtJSONParser(input, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);

        InetAddressDeserializer deser = new InetAddressDeserializer();

        Throwable error = null;

        Object value = null;
        try {
            value = deser.deserialze(parser, null, null);
        } catch (Throwable ex) {
            error = ex;
        }

        Assert.assertNotNull(error);
    }
}
