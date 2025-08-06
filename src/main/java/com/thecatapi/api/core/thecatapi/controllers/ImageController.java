package com.thecatapi.api.core.thecatapi.controllers;

import com.thecatapi.api.core.restassuredclient.HttpResponseWrapper;
import com.thecatapi.api.core.thecatapi.clients.ImageClient;
import com.thecatapi.api.models.requests.ImageRequestDto;
import io.qameta.allure.Step;

public class ImageController {

    private final ImageClient imageClient;

    public ImageController() {
        this.imageClient = new ImageClient();
    }

    @Step("Send get uploaded images with x-api-key")
    public HttpResponseWrapper getUploadedImages(String xApiKey, int statusCode) {
        return sendGetUploadedImagesRequest(xApiKey)
                .expectStatusCode("Get uploaded images request returned inappropriate status code", statusCode);
    }

    @Step("Send get uploaded images without x-api-key")
    public HttpResponseWrapper getUploadedImages(int statusCode) {
        return sendGetUploadedImagesRequest(null)
                .expectStatusCode("Get uploaded images request without x-api-key returned inappropriate status code", statusCode);
    }

    @Step("Send upload new image with x-api-key and with multipart as: {imageRequestDto}")
    public HttpResponseWrapper uploadImage(String xApiKey, ImageRequestDto imageRequestDto, int statusCode) {
        return sendUploadImageRequest(xApiKey, imageRequestDto)
                .expectStatusCode("Upload image request returned inappropriate status code", statusCode);
    }

    @Step("Send upload new image without x-api-key and with multipart as: {imageRequestDto}")
    public HttpResponseWrapper uploadImage(ImageRequestDto imageRequestDto, int statusCode) {
        return sendUploadImageRequest(null, imageRequestDto)
                .expectStatusCode("Upload image request without x-api-key returned inappropriate status code", statusCode);
    }

    @Step("Send delete uploaded image with x-api-key with id: {id}")
    public HttpResponseWrapper deleteImage(String xApiKey, String id, int statusCode) {
        return sendDeleteUploadedImageByIdRequest(xApiKey, id)
                .expectStatusCode("Delete uploaded image request returned inappropriate status code", statusCode);
    }

    @Step("Send delete uploaded new image without x-api-key with id: {id}")
    public HttpResponseWrapper deleteImage(String id, int statusCode) {
        return sendDeleteUploadedImageByIdRequest(null, id)
                .expectStatusCode("Delete uploaded  image request without x-api-key returned inappropriate status code", statusCode);
    }

    private HttpResponseWrapper sendGetUploadedImagesRequest(String xApiKey) {
        return imageClient.sendGetUploadedImagesRequest(xApiKey);
    }

    private HttpResponseWrapper sendUploadImageRequest(String xApiKey, ImageRequestDto imageRequestDto) {
        return imageClient.sendUploadImageRequest(xApiKey,
                imageRequestDto.getImg(), imageRequestDto.getSubId(), imageRequestDto.getBreedIds());
    }


    private HttpResponseWrapper sendDeleteUploadedImageByIdRequest(String xApiKey, String id) {
        return imageClient.sendDeleteUploadedImageByIdRequest(xApiKey, id);
    }
}
