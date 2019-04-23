package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;

import java.util.Date;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController()
public class TicketController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionStorage sessionStorage;


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
    ResponseEntity<String> login(WebSession session, @RequestBody Map<String,String> loginForm){
        if(userRepository.findByEmailAndPassword(loginForm.get("email"),loginForm.get("password"))!= null){
            session.start();
            sessionStorage.saveUser(session.getId(),loginForm.get("email"));
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/ticket/add")
    ResponseEntity<String> addTicket(WebSession session, @RequestBody TicketDbModel ticketDbModel){
        ticketDbModel.setStatus("toDo");
        ticketDbModel.setDate(new Date());
        ticketDbModel.setDepartment(userRepository.findByEmail(sessionStorage.findById(session.getId())).getDepartment());
        ticketDbModel.setName(userRepository.findByEmail(sessionStorage.findById(session.getId())).getName());
        ticketDbModel.setLastname(userRepository.findByEmail(sessionStorage.findById(session.getId())).getLastname());
        ticketRepository.save(ticketDbModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    ResponseEntity<List<UserDbModel>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/getAllTickets")
    ResponseEntity<List<TicketDbModel>> getTickets(WebSession session){
        return ResponseEntity.ok(ticketRepository.findByEmail(sessionStorage.findById(session.getId())));
    }


}
