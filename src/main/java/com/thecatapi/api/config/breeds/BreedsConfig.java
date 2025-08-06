package com.thecatapi.api.config.breeds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thecatapi.api.exceptions.TAFRuntimeException;
import com.thecatapi.api.models.responses.image.BreedResponseDto;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.thecatapi.api.utils.StreamUtils.randomItem;
import static java.util.Arrays.asList;

@Data
public class BreedsConfig {

    private List<BreedResponseDto> breeds;

    public BreedsConfig() {
        try {
            var mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

            breeds = asList(mapper.readValue(
                    new File("src/main/resources/static_config/breeds.json"),
                    BreedResponseDto[].class
            ));
        } catch (IOException e) {
            throw new TAFRuntimeException("Can not read file with images", e);
        }
    }

    public BreedResponseDto getRandomBreed() {
        return breeds
                .stream()
                .collect(randomItem());
    }
}
