package com.blink.blinkclient.core;

public class SessionManager {

    private static String jwtToken = null;
    private static String currUsername = null;

    public static void startSession(String token, String username) {
        jwtToken = token;
        currUsername = username;
    }

    public static void clearSession() {
        jwtToken = null;
        currUsername = null;
    }

    public static String getToken() { return jwtToken; }
    public static String getCurrentUsername() { return currUsername; }
    public static boolean isAuthenticated() { return jwtToken != null; }

}
