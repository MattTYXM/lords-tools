package com.mbss.lordsmobile.tools;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.regex.Pattern;

public final class Constants {

    public static final String LANGUAGE = System.getProperty("user.language");

    public static final int GAME_ID = 1051029902;
    public static final String ROOT_URI = "https://lordsmobile.igg.com";
    public static final String GAME_ID_KEY = "game_id";
    public static final TypeReference<Map<String, String>> TO_STRING_STRING_MAP_TYPE_REF =
            new TypeReference<Map<String, String>>() {
            };
    public static final Pattern REDEMPTION_USER_INFO_CLIENT_RESPONSE_MESSAGE_PATTERN = Pattern.compile(
            "(Kingdom:[\\s\\S]#)(?<kingdom>[0-9]*)(<br>)(Might:[\\s\\S])(?<might>[0-9,]*)");

    private Constants() {

    }
}
