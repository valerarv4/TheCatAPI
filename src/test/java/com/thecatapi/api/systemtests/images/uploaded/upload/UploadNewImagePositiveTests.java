package com.thecatapi.api.systemtests.images.uploaded.upload;

import com.thecatapi.api.models.requests.ImageRequestDto;
import com.thecatapi.api.models.responses.image.OwnedImageResponseDto;
import com.thecatapi.api.models.responses.image.UploadedImageResponseDto;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import io.qameta.allure.Allure;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.format.DateTimeFormatter;

import static com.thecatapi.api.utils.DateTimeUtils.stringToLocalDateTime;
import static com.thecatapi.api.utils.ImageUtils.*;
import static com.thecatapi.api.utils.RandomUtils.getRandomCatSubId;
import static com.thecatapi.api.utils.StringUtils.URL_REGEX;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.isNull;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.within;

@ImageEpic
public class UploadNewImagePositiveTests extends AbstractImageTest {

    private final ThreadLocal<String> uploadedImageIdThreadLocal = new ThreadLocal<>();

    @ApiVersion(1)
    @Test(dataProvider = "imageDataProvider")
    public void verifyUploadNewImageTest(String description, ImageRequestDto imageRequestDto) {
        Allure.description(description);

        var uploadedImageResponseDto = imageController
                .uploadImage(xApiKey, imageRequestDto, SC_CREATED)
                .getBodyAs(UploadedImageResponseDto.class);
        uploadedImageIdThreadLocal.set(uploadedImageResponseDto.getId());

        var soft = new SoftAssertions();
        soft.assertThat(uploadedImageResponseDto.getId())
                .as("Created image id should not be empty")
                .isNotNull()
                .isNotEmpty();
        soft.assertThat(uploadedImageResponseDto.getUrl())
                .as("Created image id should not be empty")
                .matches(URL_REGEX);
        soft.assertThat(uploadedImageResponseDto.getSubId())
                .as("Incorrect created image subId")
                .isEqualTo(imageRequestDto.getSubId());

        var uploadedImageWidth = getImageWidth(imageRequestDto.getImg());
        soft.assertThat(uploadedImageResponseDto.getWidth())
                .as("Incorrect created image width")
                .isEqualTo(uploadedImageWidth);

        var uploadedImageHeight = getImageHeight(imageRequestDto.getImg());
        soft.assertThat(uploadedImageResponseDto.getHeight())
                .as("Incorrect created image height")
                .isEqualTo(uploadedImageHeight);

        var imageName = getImageName(imageRequestDto.getImg());
        soft.assertThat(uploadedImageResponseDto.getOriginalFileName())
                .as("Incorrect created image breedIds")
                .isEqualTo(imageName);
        soft.assertThat(uploadedImageResponseDto.getBreedIds())
                .as("Incorrect created image name")
                .isEqualTo(imageRequestDto.getBreedIds());
        soft.assertThat(uploadedImageResponseDto.getPending())
                .as("Incorrect created image pending value")
                .isEqualTo(0);
        soft.assertThat(uploadedImageResponseDto.getApproved())
                .as("Incorrect created image approved value")
                .isEqualTo(1);
        soft.assertAll();

        var ownedImageResponseDto = imageController
                .getUploadedImages(xApiKey, SC_OK)
                .getBodyAsList(OwnedImageResponseDto.class)
                .stream()
                .filter(uploadImage -> uploadImage.getId().equals(uploadedImageResponseDto.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Uploaded image should be present"));

        soft.assertThat(ownedImageResponseDto.getId())
                .as("Expected owned image ID (%s) to match uploaded image ID (%s)",
                        ownedImageResponseDto.getId(), uploadedImageResponseDto.getId())
                .isEqualTo(uploadedImageResponseDto.getId());
        soft.assertThat(ownedImageResponseDto.getUrl())
                .as("Expected owned image URL (%s) to match uploaded image URL (%s)",
                        ownedImageResponseDto.getUrl(), uploadedImageResponseDto.getUrl())
                .matches(uploadedImageResponseDto.getUrl());
        soft.assertThat(ownedImageResponseDto.getWidth())
                .as("Incorrect owned image width")
                .isEqualTo(uploadedImageWidth);
        soft.assertThat(ownedImageResponseDto.getHeight())
                .as("Incorrect owned image height")
                .isEqualTo(uploadedImageHeight);
        soft.assertThat(ownedImageResponseDto.getSubId())
                .as("Incorrect owned image subId")
                .isEqualTo(imageRequestDto.getSubId());
        soft.assertThat(stringToLocalDateTime(ownedImageResponseDto.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME))
                .as("Incorrect owned image created at")
                .isCloseToUtcNow(within(1, MINUTES));
        soft.assertThat(ownedImageResponseDto.getOriginalFileName())
                .as("Incorrect owned image name")
                .isEqualTo(imageName);

        var breadsCount = isNull(imageRequestDto.getBreedIds()) ? 0 : 1;
        soft.assertThat(ownedImageResponseDto.getBreeds())
                .as("Incorrect owned image breeds")
                .hasSize(breadsCount);
        soft.assertThat(ownedImageResponseDto.getBreedIds())
                .as("Incorrect owned image breed ids")
                .isEqualTo(imageRequestDto.getBreedIds());
        soft.assertAll();
    }

    @DataProvider(name = "imageDataProvider", parallel = true)
    public Object[][] imageDataProvider() {
        return new Object[][]{
                {
                        "Verify that user able to upload image with only required fields",
                        new ImageRequestDto() {{
                            setImg(getMediumImage());
                        }}
                },
                {
                        "Verify that user able to upload image with image and subId",
                        new ImageRequestDto() {{
                            setImg(getLargeImage());
                            setSubId(getRandomCatSubId());
                        }}
                },
                {
                        "Verify that user able to upload image with image and breedId",
                        new ImageRequestDto() {{
                            setImg(getSmallImage());
                            setBreedIds(breedsConfig.getRandomBreed().getId());
                        }}
                },
                {
                        "Verify that user able to upload image with all fields",
                        new ImageRequestDto(getSmallImage(), getRandomCatSubId(), breedsConfig.getRandomBreed().getId())
                }
        };
    }

    @AfterMethod
    public void cleanUp() {
        var uploadedImageId = uploadedImageIdThreadLocal.get();

        if (!isNull(uploadedImageId)) {
            imageController.deleteImage(xApiKey, uploadedImageId, SC_NO_CONTENT);
        }

        uploadedImageIdThreadLocal.remove();
    }
}
