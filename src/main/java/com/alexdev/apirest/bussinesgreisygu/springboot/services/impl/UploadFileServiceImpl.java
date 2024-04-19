package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.services.UploadFileService;
import com.alexdev.apirest.bussinesgreisygu.springboot.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired PathUtil pathUtil;


    @Override
    public Resource search(String filename) throws MalformedURLException {
        Path imgPath = Paths.get( pathUtil.rootPath() ).resolve( filename );
        Resource resource =new UrlResource( imgPath.toUri() );

        if(!resource.exists() || !resource.isReadable())
            throw new RuntimeException("No se puede cargar la imagen especificada: "+ imgPath);

        return resource;
    }

    @Override
    public String insert(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID() + "." + file.getOriginalFilename();
        Path imgPath =Paths.get( pathUtil.rootPath() ).resolve(uuid);
        Files.copy( file.getInputStream(), imgPath );

        return uuid;
    }

    @Override
    public boolean delete(String filename) {
        File file =Paths.get( pathUtil.rootPath() ).resolve( filename ).toFile();

        if (!file.exists() || !file.canRead())
            return false;

        return file.delete();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively( Paths.get( pathUtil.rootPath() ).toFile() );
        try {
            Files.deleteIfExists( Paths.get( pathUtil.rootPath() ));
        }catch (IOException ignored){ };
    }

    @Override
    public void init() throws IOException {
        if( Files.exists( Paths.get( pathUtil.rootPath() ) ) )
            return;

        Files.createDirectory( Paths.get( pathUtil.rootPath() ));
    }
}
