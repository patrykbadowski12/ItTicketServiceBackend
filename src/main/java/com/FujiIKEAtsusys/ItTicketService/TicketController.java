package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
            userDbModel.setRole("USER");
            userRepository.save(userDbModel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    ResponseEntity<String> login(WebSession session, @RequestBody LoginForm loginForm){
        if(userRepository.findByEmailAndPassword(loginForm.getEmail(),loginForm.getPassword())!= null){
            session.start();
            sessionStorage.saveUser(session.getId(),loginForm.getEmail());
            System.out.println("działa");
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/ticket/add")
    ResponseEntity<String> addTicket(WebSession session, @RequestBody TicketDbModel ticketDbModel) {
        if (userRepository.findByEmail(sessionStorage.findById(session.getId())).getRole().equals("USER")) {
            ticketDbModel.setId(UUID.randomUUID().toString());
            ticketDbModel.setStatus("toDo");
            ticketDbModel.setDate(new Date());
            ticketDbModel.setDepartment(userRepository.findByEmail(sessionStorage.findById(session.getId())).getDepartment());
            ticketDbModel.setName(userRepository.findByEmail(sessionStorage.findById(session.getId())).getName());
            ticketDbModel.setLastname(userRepository.findByEmail(sessionStorage.findById(session.getId())).getLastname());
            ticketRepository.save(ticketDbModel);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllTickets")
    ResponseEntity<List<TicketDbModel>> getTickets(WebSession session){
        return ResponseEntity.ok(ticketRepository.findByEmail(sessionStorage.findById(session.getId())));
    }

    @PostMapping("/createUser")
    ResponseEntity<Void> createUser(){
        UserDbModel user = new UserDbModel("1", "patryk_badowski@o2.pl", "Patryk", "Badowski", "R&D", "dupa123", "administrator", "ADMIN");
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getUsers")
    ResponseEntity<List<UserDbModel>> getAllUsers(){
        List<UserDbModel> all = userRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
