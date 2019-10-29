package com.fitgoal.service.util;

public interface SimpleConverter<T, E> {

    E convertApiEntityToDtoEntity(T apiEntity);

    T convertDtoEntityToApiEntity(E dtoEntity);
}
