package com.alexdev.apirest.bussinesgreisygu.springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PathUtil {
    @Value("${url.upload.windows}")
    private String urlWindows;

    @Value("${url.upload.linux}")
    private String urlLinux;

    public Path rootPath(){
        String url =System.getProperty("os.name").equalsIgnoreCase("linux") ?
                urlLinux : urlWindows;

        return Paths.get( url ).toAbsolutePath();
    }
}