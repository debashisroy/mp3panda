package org.debnil.mp3panda.meta.transformer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by debashis.roy on 9/1/2015.
 */
public class MetaTransformer {
    public String name;
    public Map<String, TransformerField> fields = new LinkedHashMap<>();

    public MetaTransformer() {
    }

    public MetaTransformer(String name) {
        this.name = name;
    }

    public void addField(String fieldName, TransformerField field) {
        this.fields.put(fieldName, field);
    }

    public static class TransformerField {
        public String source;
        public String target;

        public TransformerField() {
        }

        public TransformerField(String source, String target) {
            this.source = source;
            this.target = target;
        }
    }
}
