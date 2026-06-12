package com.blink.blinkclient.core;

public class SessionManager {

    private static String jwtToken = null;
    private static String userId = null;
    private static String currUsername = null;
    private static String currEmail;

    public static void startSession(
            String token,
            String id,
            String username,
            String email
    ) {
        jwtToken = token;
        userId = id;
        currUsername = username;
        currEmail = email;
    }

    public static void clearSession() {
        jwtToken = null;
        userId = null;
        currUsername = null;
        currEmail = null;
    }

    public static String getToken() { return jwtToken; }
    public static String getUserId() { return userId; }
    public static String getCurrUsername() { return currUsername; }
    public static String getCurrEmail() { return currEmail; }
    public static boolean isAuthenticated() { return jwtToken != null; }
}
