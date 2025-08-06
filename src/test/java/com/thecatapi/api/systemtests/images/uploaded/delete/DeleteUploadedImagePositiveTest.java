package com.thecatapi.api.systemtests.images.uploaded.delete;

import com.thecatapi.api.models.requests.ImageRequestDto;
import com.thecatapi.api.models.responses.image.OwnedImageResponseDto;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.thecatapi.api.utils.ImageUtils.getSmallImage;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@ImageEpic
public class DeleteUploadedImagePositiveTest extends AbstractImageTest {

    private String uploadedImageId;

    @BeforeMethod
    public void initImageRequestDto() {
        var imageRequestDto = new ImageRequestDto() {{
            setImg(getSmallImage());
        }};

        uploadedImageId = imageController
                .uploadImage(xApiKey, imageRequestDto, SC_CREATED)
                .getBodyAsString("id");
    }

    @ApiVersion(1)
    @Test(description = "Verify that user able to remove uploaded image")
    public void verifyDeleteUploadedImagePositiveTest() {
        imageController.deleteImage(xApiKey, uploadedImageId, SC_NO_CONTENT);

        var ownedImageResponseDto = imageController
                .getUploadedImages(xApiKey, SC_OK)
                .getBodyAsList(OwnedImageResponseDto.class)
                .stream()
                .filter(uploadImage -> uploadImage.getId().equals(uploadedImageId))
                .findFirst()
                .orElse(null);

        assertThat(ownedImageResponseDto)
                .as("Uploaded image should be deleted")
                .isNull();
    }
}
