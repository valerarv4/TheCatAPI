package com.thecatapi.api.utils;

import io.qameta.allure.Allure;
import lombok.experimental.UtilityClass;

import static io.qameta.allure.Allure.description;

@UtilityClass
public class AllureUtils {

    public static void allureAttachTestDescription(String description) {
        description(description);
    }

    public static void allureAttachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }
}
