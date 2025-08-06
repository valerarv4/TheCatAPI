package com.thecatapi.api.systemtests.images.uploaded.upload;

import com.thecatapi.api.models.requests.ImageRequestDto;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.thecatapi.api.utils.ImageUtils.getLargeImage;
import static com.thecatapi.api.utils.RandomUtils.getRandomCatSubId;
import static com.thecatapi.api.utils.ResponseErrorMessages.INVALID_DATA;
import static io.qameta.allure.Allure.description;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@ImageEpic
public class UploadNewImageNegativeTests extends AbstractImageTest {

    @ApiVersion(1)
    @Test(dataProvider = "negativeUploadImageDataProvider")
    public void verifyUploadNewImageNegativeTest(String description, ImageRequestDto imageRequestDto,
                                                 int statusCode, String expectedResponseErrorMessage) {
        description(description);

        var actualResponseErrorMessage = imageController
                .uploadImage(xApiKey, imageRequestDto, statusCode)
                .getBodyAsString();

        assertThat(actualResponseErrorMessage)
                .as("Incorrect response error message while deleting image")
                .isEqualTo(expectedResponseErrorMessage);
    }

    @DataProvider(name = "negativeUploadImageDataProvider", parallel = true)
    public Object[][] negativeUploadImageDataProvider() {
        return new Object[][]{
                {
                        "Verify that user can not upload image without required fields",
                        new ImageRequestDto() {{
                            setSubId(getRandomCatSubId());
                            setBreedIds(breedsConfig.getRandomBreed().getId());
                        }},
                        SC_BAD_REQUEST,
                        "No 'file' sent. Check you're sending the image as the 'file' parameter. of the FormData object."
                },
                {
                        "Verify that user can not upload image with incorrect breedIds",
                        new ImageRequestDto() {{
                            setImg(getLargeImage());
                            setBreedIds(getRandomCatSubId());
                        }},
                        SC_BAD_REQUEST,
                        INVALID_DATA.getResponseErrorMessage()
                }
        };
    }
}
