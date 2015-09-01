package org.debnil.mp3panda;

import org.codehaus.jackson.map.ObjectMapper;
import org.debnil.mp3panda.meta.transformer.MetaTransformationUtil;
import org.debnil.mp3panda.meta.transformer.MetaTransformer;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by debashis.roy on 9/1/2015.
 */
public class MetaTransformerTest {

    @Test
    public void testJackson() throws IOException {
        MetaTransformer tester = new MetaTransformer("www.indiamp3.com - format");
        tester.addField("FILE_NAME", new MetaTransformer.TransformerField("aa", "a"));
        tester.addField("TITLE", new MetaTransformer.TransformerField("bb", "b"));

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(os, tester);

        String s = new String(os.toByteArray());
        System.out.println(s);

        try {
            MetaTransformer t = mapper.readValue(new ByteArrayInputStream(s.getBytes()), MetaTransformer.class);
            Assert.assertEquals(tester.name, t.name);
        } catch (Throwable t) {
            t.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void lestLoadTransformers(){
        Map<String, MetaTransformer> rules = MetaTransformationUtil.loadTransformationRules();

        Assert.assertNotNull(rules);
    }
}
