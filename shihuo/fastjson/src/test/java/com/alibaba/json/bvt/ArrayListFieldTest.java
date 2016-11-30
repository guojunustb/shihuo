package com.alibaba.json.bvt;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.json.JSON;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializerFeature;

public class ArrayListFieldTest extends TestCase {
    public void test_codec_null() throws Exception {
        V0 v = new V0();

        SerializeConfig mapping = new SerializeConfig();
        mapping.setAsmEnable(false);

        String text = JSON.toJSONString(v, mapping, SerializerFeature.WriteMapNullValue);
        Assert.assertEquals("{\"value\":null}", text);

        ParserConfig config = new ParserConfig();
        config.setAsmEnable(false);
        
        V0 v1 = JSON.parseObject(text, V0.class, config, JSON.DEFAULT_PARSER_FEATURE);

        Assert.assertEquals(v1.getValue(), v.getValue());
    }

	private static class V0 {

		private ArrayList<Object> value;

		public ArrayList<Object> getValue() {
			return value;
		}

		public void setValue(ArrayList<Object> value) {
			this.value = value;
		}

	}
}
