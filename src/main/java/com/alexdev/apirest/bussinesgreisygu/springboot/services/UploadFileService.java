package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface UploadFileService {
    /**
     *  Crea un directorio externo raiz para almacenar alli las imagenes que se guardaran del proyecto.
     * @throws IOException Si el PATH no es valido.
     */
    void init() throws IOException;

    /**
     *  Busca un archivo con el nombre especificado como parametro.
     * @param filename nombre de archivo
     * @return Un recurso.
     */
    Resource search(String filename);

    /**
     *  Registra el archivo en el directorio externo del proyecto.
     * @param file MultipartFile
     * @return nombre del archivo registrado en el directorio externo.
     */
    String store(MultipartFile file);

    /**
     * Elimina un archivo de imagen en el directorio externo.
     * @param filename nombre del archivo-imagen
     * @return true - si se borro correctamente. false - si no lo borro.
     */
    boolean delete(String filename);

    String update( String filename, MultipartFile newFile );
    // Elimina todas las imagenes de manera recursiva, incluyendo el PATH.
    void deleteAll();
}