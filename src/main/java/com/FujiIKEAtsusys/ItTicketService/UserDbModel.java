package com.FujiIKEAtsusys.ItTicketService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserDbModel {

    @Id
    String id;

    String email;
    String name;
    String lastname;
    String department;
    String password;
    String position;
    String role;

}
