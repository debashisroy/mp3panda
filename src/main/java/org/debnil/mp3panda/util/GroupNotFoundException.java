package org.debnil.mp3panda.util;

/**
 * Created by debnil on 29-Aug-15.
 */
public class GroupNotFoundException extends Exception {

    private final String groupName;

    public GroupNotFoundException(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
