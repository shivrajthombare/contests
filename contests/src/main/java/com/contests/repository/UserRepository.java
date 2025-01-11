package com.contests.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.contests.entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
