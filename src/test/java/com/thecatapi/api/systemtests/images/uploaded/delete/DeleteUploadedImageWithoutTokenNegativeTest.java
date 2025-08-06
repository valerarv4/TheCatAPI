package com.thecatapi.api.systemtests.images.uploaded.delete;

import com.thecatapi.api.systemtests.ITokenValidation;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@ImageEpic
public class DeleteUploadedImageWithoutTokenNegativeTest extends AbstractImageTest implements ITokenValidation {

    @ApiVersion(1)
    @Test(description = "Verify that user without token can not remove uploaded image")
    public void verifyDeleteUploadedImageWithoutTokenNegativeTest() {
        validateErrorMessage(() -> imageController.deleteImage(imagesConfig.getRandomImage().getId(), SC_UNAUTHORIZED));
    }
}
