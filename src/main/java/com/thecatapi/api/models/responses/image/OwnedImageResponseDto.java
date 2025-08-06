package com.thecatapi.api.models.responses.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OwnedImageResponseDto {

    private String id;
    private String url;
    private Integer width;
    private Integer height;
    @JsonProperty("sub_id")
    private String subId;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("original_filename")
    private String originalFileName;
    private List<BreedResponseDto> breeds;
    @JsonProperty("breed_ids")
    private String breedIds;
}
