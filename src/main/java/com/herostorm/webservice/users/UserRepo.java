package com.herostorm.webservice.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepo extends MongoRepository<User, String> {

    @Query("{'userName':?0}")
    User getByUserName(String userName);
}
