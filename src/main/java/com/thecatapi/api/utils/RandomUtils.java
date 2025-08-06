package com.thecatapi.api.utils;

import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


@UtilityClass
public class RandomUtils {

    public static final Faker FAKER = new Faker();
    public final static Random RANDOM;

    static {
        try {
            RANDOM = new SecureRandom().getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRandomCatSubId() {
        return FAKER.cat().breed();
    }
}
