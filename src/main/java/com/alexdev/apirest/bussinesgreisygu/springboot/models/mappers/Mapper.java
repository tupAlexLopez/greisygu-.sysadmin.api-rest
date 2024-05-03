package com.alexdev.apirest.bussinesgreisygu.springboot.models.mappers;

public interface Mapper <Entity, Dto>{
    Dto entityToDto(Entity entity);
    Entity dtoToEntity(Dto dto);
}
