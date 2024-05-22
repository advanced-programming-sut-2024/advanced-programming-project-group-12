package com.mygdx.game.view.screen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidInputs {
    ;
    final String regex;
    ValidInputs(String regex) {
        this.regex = regex;
    }
    private Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return matcher;
    }
    public boolean isMatch(String input) {
        return getMatcher(input).matches();
    }
    public boolean isFind(String input) {
        return getMatcher(input).find();
    }
    public String getGroup(String input, String groupName) {
        return getMatcher(input).group(groupName);
    }

}
