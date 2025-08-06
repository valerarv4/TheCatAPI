package com.thecatapi.api.systemtests.images.uploaded.delete;

import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.thecatapi.api.utils.RandomUtils.getRandomCatSubId;
import static com.thecatapi.api.utils.ResponseErrorMessages.INVALID_DATA;
import static com.thecatapi.api.utils.ResponseErrorMessages.NOT_FOUND;
import static io.qameta.allure.Allure.description;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@ImageEpic
public class DeleteUploadedImageNegativeTests extends AbstractImageTest {

    @ApiVersion(1)
    @Test(dataProvider = "deleteImageDataProvider")
    public void verifyDeleteUploadedImageNegativeTest(String description, String id,
                                                      int statusCode, String expectedResponseErrorMessage) {
        description(description);

        var actualResponseErrorMessage = imageController
                .deleteImage(xApiKey, id, statusCode)
                .getBodyAsString();

        assertThat(actualResponseErrorMessage)
                .as("Incorrect response error message while deleting image")
                .isEqualTo(expectedResponseErrorMessage);
    }

    @DataProvider(name = "deleteImageDataProvider", parallel = true)
    public Object[][] imageDataProvider() {
        return new Object[][]{
                //TODO need to refactor define not owned image id
                {
                        "Verify that user can not delete not owned image", "43j", SC_BAD_REQUEST, INVALID_DATA.getResponseErrorMessage()
                },
                {
                        "Verify that user can not delete not existed image", getRandomCatSubId(), SC_NOT_FOUND, NOT_FOUND.getResponseErrorMessage()
                }
        };
    }
}
