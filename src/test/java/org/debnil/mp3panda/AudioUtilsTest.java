package org.debnil.mp3panda;

import org.jaudiotagger.tag.FieldKey;
import org.junit.Test;

import java.io.File;
import java.util.Map;

/**
 * Created by debnil on 05-Aug-15.
 */
public class AudioUtilsTest {

    @Test
    public void testLoad() throws Exception {
        File source = new File("src/test/resources/INX - Never Tear Us Apart.mp3");
        File target = new File("src/test/resources/INX - Never Tear Us Apart - modified.mp3");

        Map<FieldKey, String> fields = AudioUtils.load(source);

        for (FieldKey key : fields.keySet()) {
            String value = fields.get(key);

            try {
                int intval = Integer.parseInt(value);
                value = Integer.toString(intval + 1);
            } catch (Throwable t) {
                value = "NEW - " + value;
            }

            fields.put(key, value);
        }

        AudioUtils.update(source, target, fields);
    }

    @Test
    public void testRegexMatcher() {
        String result;
        result = AudioUtils.regexTransform("Halkat Jawani - www.Songs.PK", "(?<title>.+) - .*", "pre_${12}.post${title}");
        System.out.println(result);

        result = AudioUtils.regexTransform("Halkat Jawani - www.Songs.PK", "(?<title>.+) - .*", "pre_${title}.post${title}");
        System.out.println(result);

        result = AudioUtils.regexTransform("Halkat Jawani - www.Songs.PK", "(?<title>.+) - .*", "${title}");
        System.out.println(result);

        result = AudioUtils.regexTransform("Halkat Jawani - www.Songs.PK", "(?<title>.+) - .*", "Ho Ho");
        System.out.println(result);
    }
}
