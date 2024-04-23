package com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Schema(name = "Media")
public class MediaResponse {
    @Schema(example = "ravioles.png")
    private String filename;

    @Schema(example = "http://www.api-domain.com/media/ravioles.png")
    private String url;
}
