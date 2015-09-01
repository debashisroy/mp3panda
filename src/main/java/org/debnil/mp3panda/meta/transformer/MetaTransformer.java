package org.debnil.mp3panda.meta.transformer;

import com.google.common.base.Strings;
import org.debnil.mp3panda.mp3.AudioMetadataField;
import org.debnil.mp3panda.mp3.MP3File;
import org.debnil.mp3panda.util.AudioUtils;
import org.debnil.mp3panda.util.GroupNotFoundException;
import org.debnil.mp3panda.util.RegexUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void transform(MP3File mp3file) {
        Map<String, String> groupMap = new HashMap<>();

        for (String fieldName : mp3file.getMetadata().keySet()) {
            AudioMetadataField field = mp3file.getMetadata().get(fieldName);

            if (fields.containsKey(fieldName)) {
                TransformerField fieldTransformer = fields.get(fieldName);

                Pattern pattern = Pattern.compile(fieldTransformer.source);
                Matcher matcher = pattern.matcher(Strings.nullToEmpty(field.getCurrentValue()));

                if (matcher.matches()) {
                    Set<String> groupNames = RegexUtil.getNamedGroupCandidates(fieldTransformer.source);

                    for (String groupName : groupNames) {
                        String value = matcher.group(groupName);
                        groupMap.put(groupName, value);
                    }
                }
            }
        }

        for (String fieldName : mp3file.getMetadata().keySet()) {
            AudioMetadataField field = mp3file.getMetadata().get(fieldName);

            boolean error = false;
            if (fields.containsKey(fieldName)) {
                try {
                    String targetValue = AudioUtils.transform(groupMap, fields.get(fieldName).target);
                    field.setNewValue(targetValue);
                } catch (GroupNotFoundException e) {
                    error = true;
                    field.setNewValue(field.getCurrentValue());
                }
            }

            field.setError(error);
        }
    }
}
