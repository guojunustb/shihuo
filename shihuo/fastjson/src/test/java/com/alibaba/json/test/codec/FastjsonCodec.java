package com.alibaba.json.test.codec;

import java.util.Collection;

import com.alibaba.json.JSON;
import com.alibaba.json.parser.DefaultExtJSONParser;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.SerializeWriter;
import com.alibaba.json.serializer.SerializerFeature;

public class FastjsonCodec implements Codec {

    private ParserConfig    config = ParserConfig.getGlobalInstance();

    public String getName() {
        return "fastjson";
    }

    public <T> T decodeObject(String text, Class<T> clazz) {
        DefaultExtJSONParser parser = new DefaultExtJSONParser(text, config);
        parser.config(Feature.DisableCircularReferenceDetect, true);
        return parser.parseObject(clazz);
    }

    public <T> Collection<T> decodeArray(String text, Class<T> clazz) throws Exception {
        DefaultExtJSONParser parser = new DefaultExtJSONParser(text, config);
        parser.config(Feature.DisableCircularReferenceDetect, true);
        return parser.parseArray(clazz);
    }

    public final Object decodeObject(String text) {
        DefaultJSONParser parser = new DefaultJSONParser(text, config);
        parser.config(Feature.DisableCircularReferenceDetect, true);
        return parser.parse();
    }

    public final Object decode(String text) {
        DefaultJSONParser parser = new DefaultJSONParser(text, config);
        parser.config(Feature.DisableCircularReferenceDetect, true);
        return parser.parse();
    }

    // private JavaBeanSerializer serializer = new JavaBeanSerializer(Long_100_Entity.class);

    public String encode(Object object) throws Exception {
        SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
//        out.config(SerializerFeature.DisableCheckSpecialChar, true);

        JSONSerializer.write(out, object);

        String text = out.toString();

        out.close();

        return text;
    }

    @SuppressWarnings("unchecked")
    public <T> T decodeObject(byte[] input, Class<T> clazz) throws Exception {
        return (T) JSON.parseObject(input, clazz);
    }

}
