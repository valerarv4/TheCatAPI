package com.thecatapi.api.utils;

import lombok.experimental.UtilityClass;

import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.description;

@UtilityClass
public class AllureUtils {

    public static void allureAttachTestDescription(String description) {
        description(description);
    }

    public static void allureAttachText(String name, String content) {
        addAttachment(name, "text/plain", content);
    }
}
