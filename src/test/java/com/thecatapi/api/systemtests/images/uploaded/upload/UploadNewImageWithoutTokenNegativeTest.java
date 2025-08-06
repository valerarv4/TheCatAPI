package com.thecatapi.api.systemtests.images.uploaded.upload;

import com.thecatapi.api.models.requests.ImageRequestDto;
import com.thecatapi.api.systemtests.ITokenValidation;
import com.thecatapi.api.systemtests.images.AbstractImageTest;
import com.thecatapi.api.utils.annotations.ApiVersion;
import com.thecatapi.api.utils.annotations.epics.ImageEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.thecatapi.api.utils.ImageUtils.getSmallImage;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@ImageEpic
public class UploadNewImageWithoutTokenNegativeTest extends AbstractImageTest implements ITokenValidation {

    private ImageRequestDto imageRequestDto;

    @BeforeMethod
    public void initImageRequestDto() {
        imageRequestDto = new ImageRequestDto() {{
            setImg(getSmallImage());
        }};
    }

    @ApiVersion(1)
    @Test(description = "Verify that user without token can not upload new image")
    public void verifyUploadNewImageWithoutTokenNegativeTest() {
        validateErrorMessage(() -> imageController.uploadImage(imageRequestDto, SC_UNAUTHORIZED));
    }
}
