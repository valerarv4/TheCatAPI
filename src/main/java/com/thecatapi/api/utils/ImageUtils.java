package com.thecatapi.api.utils;

import com.thecatapi.api.exceptions.ImageProcessingException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

@UtilityClass
public class ImageUtils {

    public static File getSmallImage() {
        return new File("src/main/resources/img/small.jpeg");
    }

    public static File getMediumImage() {
        return new File("src/main/resources/img/medium.jpeg");
    }

    public static File getLargeImage() {
        return new File("src/main/resources/img/large.jpeg");
    }

    public static int getImageWidth(File imageFile) {
        try {
            return read(imageFile).getWidth();
        } catch (IOException e) {
            throw new ImageProcessingException("Error while reading the image: " + e);
        }
    }

    public static int getImageHeight(File imageFile) {
        try {
            return read(imageFile).getHeight();
        } catch (IOException e) {
            throw new ImageProcessingException("Error while reading the image: " + e);
        }
    }

    public static String getImageName(File imageFile) {
        return imageFile.getName();
    }
}
