package com.FujiIKEAtsusys.ItTicketService.DBModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
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
    String department;
    String password;

    public UserDbModel(String email, String department, String password) {
        this.email = email;
        this.department = department;
        this.password = password;
    }
}
