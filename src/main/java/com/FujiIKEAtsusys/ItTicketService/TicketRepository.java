package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<TicketDbModel, String> {

    List<TicketDbModel> findByEmail(String email);
    List<TicketDbModel> findByStatus(String status);
}
