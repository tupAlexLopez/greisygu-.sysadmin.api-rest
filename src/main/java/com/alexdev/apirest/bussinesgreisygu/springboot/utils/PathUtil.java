package com.alexdev.apirest.bussinesgreisygu.springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathUtil {
    @Value("${url.upload.windows}")
    private String urlWindows;

    @Value("${url.upload.linux}")
    private String urlLinux;

    public String rootPath(){
        return System.getProperty("os.name").equalsIgnoreCase("linux") ?
                urlLinux : urlWindows;
    }
}