package org.debnil.mp3panda.util;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by debnil on 27-Aug-15.
 */
public class RegexUtil {
    public static Set<String> getNamedGroupCandidates(String regex) {
        Set<String> namedGroups = new TreeSet<>();
        Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);

        while (m.find()) {
            namedGroups.add(m.group(1));
        }

        return namedGroups;
    }
}
