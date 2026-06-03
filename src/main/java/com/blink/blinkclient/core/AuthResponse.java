package com.blink.blinkclient.core;

public record AuthResponse(
        String token,
        String id,
        String username,
        String email
) {
}
