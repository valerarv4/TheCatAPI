package com.thecatapi.api.models.responses.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {

    private List<CategoryResponseDto> categories;
    private String id;
    private String url;
    private Integer width;
    private Integer height;
    private BreedResponseDto breed;
}
