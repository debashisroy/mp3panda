package org.debnil.mp3panda.util;

import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;
import org.debnil.mp3panda.mp3.MP3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.lyrics3.Lyrics3v2Field;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by debnil on 05-Aug-15.
 */
public class AudioUtils {
    public static final String MP3_EXTENSION = "mp3";

    private static final String PREFIX = "${";
    private static final String POSTFIX = "}";

    public static final FieldKey[] FIELDS = {FieldKey.TITLE, FieldKey.ALBUM, FieldKey.ARTIST, FieldKey.ALBUM_ARTIST,
            FieldKey.COMMENT,
            FieldKey.YEAR, FieldKey.TRACK, FieldKey.GENRE,
            FieldKey.ENCODER,
            FieldKey.COMPOSER, FieldKey.CONDUCTOR, FieldKey.GROUPING, FieldKey.KEY};

    protected List<MP3File> populateFileList(File directory, boolean recursive) {
        if (directory.exists() && directory.isDirectory()) {
            List<File> files = new ArrayList<>();
            loadFiles(files, directory, recursive);
            List<MP3File> fileList = files.stream().map(file -> new MP3File(file)).collect(Collectors.toList());
            return fileList;
        }
        return Collections.EMPTY_LIST;
    }

    private void loadFiles(List<File> files, File directory, boolean recursive) {
        List<File> fileList = Arrays.asList(directory.listFiles());
        fileList.stream().filter(file -> file.isFile() && MP3_EXTENSION.equalsIgnoreCase(Files.getFileExtension(file.getName()))).forEachOrdered(file -> files.add(file));
        if (recursive) {
            fileList.stream().filter(file -> file.isDirectory()).forEachOrdered(dir -> loadFiles(files, dir, recursive));
        }
    }


    public static Map<FieldKey, String> load(File audioFile) throws Exception {
        AudioFile f = AudioFileIO.read(audioFile);
        Tag tag = f.getTag();

        Map<FieldKey, String> fieldValues = new HashMap<>();
        for (FieldKey key : FIELDS) {
            String value = null;
            TagField lyricsField = tag.getFirstField(key);
            if (lyricsField instanceof AbstractID3v2Frame) {
                value = ((AbstractID3v2Frame) lyricsField).getContent();
            } else if (lyricsField instanceof Lyrics3v2Field) {
                value = ((Lyrics3v2Field) lyricsField).getBody().getLongDescription();
            }

            fieldValues.put(key, value);
        }

        return fieldValues;
    }

    public static void update(File source, File target, Map<FieldKey, String> fieldValues) throws Exception {
        try (FileInputStream input = new FileInputStream(source); FileOutputStream output = new FileOutputStream(target)) {
            IOUtils.copy(input, output);
        }
        update(target, fieldValues);
    }

    public static void update(File audioFile, Map<FieldKey, String> fieldValues) throws Exception {
        AudioFile f = AudioFileIO.read(audioFile);
        Tag tag = f.getTag();

        for (FieldKey key : fieldValues.keySet()) {
            String value = fieldValues.get(key);
            tag.setField(key, value);
        }
        f.commit();
    }

    public static String regexTransform(String input, String inPattern, String outPattern) {
        Pattern pattern = Pattern.compile(inPattern);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            try {
                StringBuilder sb = new StringBuilder();

                String tempOutPattern = outPattern;
                int index = tempOutPattern.indexOf(PREFIX);

                while (index >= 0) {
                    sb.append(tempOutPattern.substring(0, index));
                    tempOutPattern = tempOutPattern.substring(index + 2);

                    int endIndex = tempOutPattern.indexOf(POSTFIX);
                    String groupName = tempOutPattern.substring(0, endIndex);

                    sb.append(matcher.group(groupName).trim());

                    tempOutPattern = tempOutPattern.substring(endIndex + 1);
                    index = tempOutPattern.indexOf(PREFIX);
                }

                sb.append(tempOutPattern);
                return sb.toString();
            } catch (Throwable t) {
                return outPattern;
            }
        }
        return input;
    }
}
