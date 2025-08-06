package com.thecatapi.api.systemtests;

import com.thecatapi.api.config.EnvironmentConfig;
import com.thecatapi.api.config.breeds.BreedsConfig;
import com.thecatapi.api.config.breeds.BreedsPool;
import com.thecatapi.api.config.images.ImagesConfig;
import com.thecatapi.api.config.images.ImagesPool;
import com.thecatapi.api.utils.listeners.ApiVersionFilterListener;
import com.thecatapi.api.utils.listeners.ThreadCountListener;
import org.modelmapper.ModelMapper;
import org.testng.annotations.Listeners;

@Listeners({ApiVersionFilterListener.class, ThreadCountListener.class})
public abstract class AbstractTestRunner {

    protected final static String xApiKey;
    protected final static ImagesConfig imagesConfig;
    protected final static ImagesPool imagesPool;
    protected final static BreedsConfig breedsConfig;
    protected final static BreedsPool breedsPool;
    protected final static ModelMapper modelMapper;

    static {
        xApiKey = EnvironmentConfig.getInstance().getXApiKey();
        imagesConfig = new ImagesConfig();
        imagesPool = new ImagesPool(imagesConfig);
        breedsConfig = new BreedsConfig();
        breedsPool = new BreedsPool(breedsConfig);
        modelMapper = new ModelMapper();
    }
}
