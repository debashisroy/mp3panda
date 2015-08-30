package org.debnil.mp3panda.util;

import org.debnil.mp3panda.mp3.AudioMetadataField;
import org.debnil.mp3panda.mp3.MP3File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by debnil on 29-Aug-15.
 */
public class MetadataTransformer {
    public Map<String, FieldTransformer> transformers = new HashMap<>();

    public static class FieldTransformer {
        public String fieldName;
        public String sourceFormat;
        public String targetFormat;
    }

    public void apply(MP3File mp3file) {
        Map<String, String> groupMap = new HashMap<>();

        for (String fieldName : mp3file.getMetadata().keySet()) {
            AudioMetadataField field = mp3file.getMetadata().get(fieldName);

            if (transformers.containsKey(fieldName)) {
                FieldTransformer fieldTransformer = transformers.get(fieldName);

                Pattern pattern = Pattern.compile(fieldTransformer.sourceFormat);
                Matcher matcher = pattern.matcher(field.getCurrentValue());

                if (matcher.matches()) {
                    Set<String> groupNames = RegexUtil.getNamedGroupCandidates(fieldTransformer.sourceFormat);

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
            if (transformers.containsKey(fieldName)) {
                try {
                    String targetValue = AudioUtils.transform(groupMap, transformers.get(fieldName).targetFormat);
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
