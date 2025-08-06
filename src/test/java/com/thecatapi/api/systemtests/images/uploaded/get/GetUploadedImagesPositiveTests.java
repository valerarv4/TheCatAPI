package com.thecatapi.api.systemtests.images.uploaded.get;

import com.thecatapi.api.models.responses.image.OwnedImageResponseDto;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@ImageEpic
public class GetUploadedImagesPositiveTests extends AbstractImageTest {

    @ApiVersion(1)
    @Test(description = "Verify that user able receive uploaded image")
    public void verifyGetUploadedImagesPositiveTest() {
        var ownedImageResponseDtos = imageController
                .getUploadedImages(xApiKey, SC_OK)
                .getBodyAsList(OwnedImageResponseDto.class);

        assertThat(ownedImageResponseDtos)
                .as("Expected uploaded images to match the configured images. The response body did not contain all the expected images.")
                .containsAll(imagesConfig.getImages());
    }
}
