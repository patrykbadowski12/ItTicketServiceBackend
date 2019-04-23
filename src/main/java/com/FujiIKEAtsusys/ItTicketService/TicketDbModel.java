package com.FujiIKEAtsusys.ItTicketService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TicketDbModel {

    @Id
    String id;

    String status;
    Date date;
    String name;
    String lastname;
    String email;
    String description;
    String department;
    String problemType;

}
