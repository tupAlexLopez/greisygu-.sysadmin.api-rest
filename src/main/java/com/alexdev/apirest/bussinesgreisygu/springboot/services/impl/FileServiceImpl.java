package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.services.FileService;
import com.alexdev.apirest.bussinesgreisygu.springboot.utils.PathUtil;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    private final PathUtil pathUtil;

    @Autowired
    public FileServiceImpl(PathUtil pathUtil) {
        this.pathUtil = pathUtil;
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        if( Files.exists( pathUtil.rootPath() ))
            return;

        Files.createDirectory( pathUtil.rootPath() );
    }

    @Override
    public Resource search(String filename) {
        if( filename.isEmpty() )
            throw new RuntimeException("Filename no debe estar vacio.");

        try {
            Path filePath = pathUtil.rootPath().resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable())
                throw new RuntimeException("No se puede cargar la imagen especificada: " + filePath);

            return resource;
        }catch (MalformedURLException mfe){
            throw new RuntimeException( "No se encontro el archivo especificado: "+ mfe.getMessage() );
        }
    }

    @Override
    public String update(String filename, MultipartFile newFile) {
        if( !search(filename).exists() )
            throw new RuntimeException("No se encontro archivo existente a modificar");

        delete( filename );
        return upload( newFile );
    }

    @Override
    public String upload(MultipartFile file) {
        if( file.isEmpty() || file.getOriginalFilename() == null )
            throw new RuntimeException("File is empty.");

        try {
            String filename = file.getOriginalFilename();
            Path destinationFile = pathUtil.rootPath().resolve(Paths.get( filename ))
                    .normalize().toAbsolutePath();

            try (InputStream stream = file.getInputStream()) {
                Files.copy(stream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return filename;
        }catch (IOException e){
            throw new RuntimeException("Ocurrio un error al guardar imagen: "+ e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        File file = pathUtil.rootPath().resolve( filename ).toFile();

        if (!file.exists() || !file.canRead())
            throw new RuntimeException( "No se pudo eliminar la imagen." );

        return file.delete();
    }
}
