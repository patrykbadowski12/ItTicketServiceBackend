package com.FujiIKEAtsusys.ItTicketService.Controller;

import com.FujiIKEAtsusys.ItTicketService.DBModels.UserDbModel;
import com.FujiIKEAtsusys.ItTicketService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
public class TicketController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody UserDbModel userDbModel){
        if(userRepository.findByEmail(userDbModel.getEmail()) == null){
            userRepository.save(userDbModel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody Map<String,String> loginForm){
        if(userRepository.findByEmailAndPassword(loginForm.get("email"),loginForm.get("password"))!= null){
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    ResponseEntity<List<UserDbModel>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

}
