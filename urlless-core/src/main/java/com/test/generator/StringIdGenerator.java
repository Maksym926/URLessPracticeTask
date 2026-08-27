package com.test.generator;

import com.test.exceptions.FailedToCreateCollectionException;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;

public class StringIdGenerator implements IdGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 5;
    private static final int MAX_ATTEMPTS = 10;
    private final Random random = new SecureRandom();

    @Override
    public String generate(String url, Set<String> collisions) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String candidate = randomCode();
            if (!collisions.contains(candidate)) {
                return candidate;
            }
        }
        throw new FailedToCreateCollectionException();
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
