package com.thecatapi.api.models.responses.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadedImageResponseDto {

    private String id;
    private String url;
    @JsonProperty("sub_id")
    private String subId;
    private Integer width;
    private Integer height;
    @JsonProperty("original_filename")
    private String originalFileName;
    @JsonProperty("breed_ids")
    private String breedIds;
    private Integer pending;
    private Integer approved;
}
