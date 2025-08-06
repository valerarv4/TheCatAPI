package com.thecatapi.api.utils.annotations.epics;

import io.qameta.allure.Epic;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Epic("Image")
@Retention(RUNTIME)
@Target(TYPE)
public @interface ImageEpic {
}
