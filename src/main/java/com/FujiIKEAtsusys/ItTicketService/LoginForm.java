package com.FujiIKEAtsusys.ItTicketService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    String email;
    String password;
    String department;
}
