package org.debnil.mp3panda.meta.transformer;

import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by debashis.roy on 9/1/2015.
 */
public class MetaTransformationUtil {
    private static final String TRANSFORMER_FILE = "meta-transformers.json";
    public static final String USER_HOME = System.getProperty("user.home") + "/.mp3panda";

    public static Map<String, MetaTransformer> loadTransformationRules() {
        File defaultFile = new File(MetaTransformationUtil.class.getResource(TRANSFORMER_FILE).getPath());
        File userFile = new File(USER_HOME + '/' + TRANSFORMER_FILE);

        List<MetaTransformer> defaultRules = loadTransformationRulesFromFile(defaultFile);
        List<MetaTransformer> userRules = loadTransformationRulesFromFile(userFile);

        defaultRules.addAll(userRules);
        Map<String, MetaTransformer> rules = defaultRules.stream().collect(Collectors.toMap((k) -> k.name, (k) -> k));
        return rules;
    }

    private static List<MetaTransformer> loadTransformationRulesFromFile(File file) {
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return Lists.newArrayList(mapper.readValue(file, MetaTransformer[].class));
            } catch (Throwable t) {
                t.printStackTrace();
                new RuntimeException("Failed to load file.");
            }
        }
        return Collections.emptyList();
    }
}
