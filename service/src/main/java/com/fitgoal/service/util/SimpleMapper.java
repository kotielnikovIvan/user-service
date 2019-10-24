package com.fitgoal.service.util;

public interface SimpleMapper<T, E> {

    E convertApiEntityToDtoEntity(T apiEntity);

    T convertDtoEntityToApiEntity(E dtoEntity);
}
