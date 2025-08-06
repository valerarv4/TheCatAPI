package com.thecatapi.api.systemtests.images.uploaded.get;

import com.thecatapi.api.systemtests.ITokenValidation;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@ImageEpic
public class GetUploadedImageWithoutTokenNegativeTest extends AbstractImageTest implements ITokenValidation {

    @ApiVersion(1)
    @Test(description = "Verify that user without token can not receive uploaded image")
    public void verifyGetUploadedImageWithoutTokenNegativeTest() {
        validateErrorMessage(() -> imageController.getUploadedImages(SC_UNAUTHORIZED));
    }
}
