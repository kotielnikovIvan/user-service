package com.fitgoal.dao.mongo.converter;

import com.fitgoal.dao.domain.UserDto;
import org.bson.Document;

public class UserConverter {

    public static Document convertDtoEntityToDocument(UserDto userDto) {
        return new Document("email", userDto.getEmail())
                .append("password", userDto.getPassword())
                .append("link", userDto.getLink())
                .append("active", userDto.isActive());
    }

    public static UserDto convertDocumentToDtoEntity(Document doc) {
        return UserDto.builder()
                .id(doc.getObjectId("_id").toString())
                .email(doc.getString("email"))
                .password(doc.getString("password"))
                .link(doc.getString("link"))
                .active(doc.getBoolean("active"))
                .build();
    }
}
