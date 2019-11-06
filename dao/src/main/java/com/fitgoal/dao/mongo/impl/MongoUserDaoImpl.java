package com.fitgoal.dao.mongo.impl;

import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.mongo.MongoUserDao;
import com.fitgoal.dao.mongo.converter.UserConverter;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoUserDaoImpl implements MongoUserDao {

    private static final String DB_NAME = "fit_goal_db";
    private static final String USERS_COLLECTION = "users";

    private MongoCollection<Document> usersCollection;

    @Inject
    public MongoUserDaoImpl(MongoClient client) {
        usersCollection = client
                .getDatabase(DB_NAME)
                .getCollection(USERS_COLLECTION);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return Optional.ofNullable(usersCollection
                .find(eq("email", email))
                .first())
                .map(UserConverter::convertDocumentToDtoEntity);
    }

    @Override
    public Optional<UserDto> findByLink(String link) {
        return Optional.empty();
    }

    @Override
    public UserDto save(UserDto userDto) {
        usersCollection.insertOne(UserConverter.convertDtoEntityToDocument(userDto));
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }
}
