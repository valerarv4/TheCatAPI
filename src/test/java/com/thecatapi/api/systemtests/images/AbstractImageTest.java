package com.thecatapi.api.systemtests.images;

import com.thecatapi.api.core.thecatapi.controllers.ImageController;
import com.thecatapi.api.systemtests.AbstractTestRunner;

public abstract class AbstractImageTest extends AbstractTestRunner {

    protected final ImageController imageController = new ImageController();
}
