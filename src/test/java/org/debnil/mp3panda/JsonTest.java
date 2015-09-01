package org.debnil.mp3panda;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by debashis.roy on 9/1/2015.
 */
public class JsonTest {

    @Test
    public void testJackson() throws IOException {
        Tester tester = new Tester("www.indiamp3.com - format");
        tester.addField("FILE_NAME", new TesterField("aa", "a"));
        tester.addField("TITLE", new TesterField("bb", "b"));

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(os, tester);

        String s = new String(os.toByteArray());
        System.out.println(s);

        try {
            Tester t = mapper.readValue(new ByteArrayInputStream(s.getBytes()), Tester.class);
            Assert.assertEquals(tester.name, t.name);
        } catch (Throwable t) {
            t.printStackTrace();
            Assert.fail();
        }
    }

    public static class Tester {
        public String name;
        public Map<String, TesterField> fields = new LinkedHashMap<>();

        public Tester() {
        }

        public Tester(String name) {
            this.name = name;
        }

        public void addField(String fieldName, TesterField field) {
            this.fields.put(fieldName, field);
        }
    }

    public static class TesterField {
        public String source;
        public String target;

        public TesterField() {
        }

        public TesterField(String source, String target) {
            this.source = source;
            this.target = target;
        }
    }
}
