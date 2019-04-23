package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDbModel, String> {

    UserDbModel findByEmail(String email);

    UserDbModel findByEmailAndPassword(String email, String password);
}
