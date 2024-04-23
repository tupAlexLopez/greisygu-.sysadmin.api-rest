package com.alexdev.apirest.bussinesgreisygu.springboot.models.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class MediaResponse {
    private String filename;
    private String url;
}
