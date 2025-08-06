package com.thecatapi.api.core.thecatapi.clients;

import com.thecatapi.api.core.restassuredclient.HttpResponseWrapper;
import io.restassured.config.EncoderConfig;
import lombok.NoArgsConstructor;

import java.io.File;

import static com.thecatapi.api.core.thecatapi.clients.ImageClient.ImageResource.*;
import static io.restassured.http.ContentType.TEXT;
import static lombok.AccessLevel.PRIVATE;

public class ImageClient extends AbstractClient {

    public HttpResponseWrapper sendGetUploadedImagesRequest(String xApiKey) {
        return getClient()
                .addXApiKey(xApiKey)
                .queryParam("limit", 100)
                .queryParam("page", 0)
                .queryParam("order", "ASC")
                .get(IMAGES_BASE_URL);
    }

    public HttpResponseWrapper sendUploadImageRequest(String xApiKey, File img, String subId, String breedIds) {
        var encoderConfig = new EncoderConfig().encodeContentTypeAs("multipart/form-data", TEXT);

        return getClient()
                .addEncoderConfig(encoderConfig)
                .addXApiKey(xApiKey)
                .multipartParam("file", img, "image/jpeg")
                .formParam("sub_id", subId)
                .formParam("breed_ids", breedIds)
                .post(IMAGE_UPLOAD_BASE_URL);
    }

    public HttpResponseWrapper sendDeleteUploadedImageByIdRequest(String xApiKey, String id) {
        return getClient()
                .addXApiKey(xApiKey)
                .pathParam("image_id", id)
                .delete(IMAGE_DELETE_BASE_URL);
    }

    @NoArgsConstructor(access = PRIVATE)
    static class ImageResource {
        static final String IMAGES_BASE_URL = "/images";
        static final String IMAGE_UPLOAD_BASE_URL = IMAGES_BASE_URL + "/upload";
        static final String IMAGE_DELETE_BASE_URL = IMAGES_BASE_URL + "/{image_id}";
    }
}
