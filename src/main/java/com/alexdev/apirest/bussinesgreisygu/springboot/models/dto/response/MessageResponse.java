package com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Schema(name = "Error")
public class MessageResponse {
    @Schema(description = "message", example = "Mensaje de exito / error servidor")
    private String message;
}
