package org.debnil.mp3panda;

import org.jaudiotagger.tag.FieldKey;

import java.io.File;
import java.util.Map;

/**
 * Created by debnil on 22-Aug-15.
 */
public class MP3File {
    protected File file;
    protected Map<FieldKey, String> metadata;

    public MP3File(File file) {
        this.file = file;
        try {
            metadata = AudioUtils.load(file);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
