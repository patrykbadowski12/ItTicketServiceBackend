package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;

import java.util.*;


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

    @GetMapping("/userInfo")
    ResponseEntity<UserDbModel> userInfo(WebSession webSession){
        String byId = sessionStorage.findById(webSession.getId());
        UserDbModel user = userRepository.findByEmail(byId);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/login")
    ResponseEntity<String> login(WebSession session, @RequestBody LoginForm loginForm){
        UserDbModel user = userRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
        if(user!= null){
            session.start();
            sessionStorage.saveUser(session.getId(),loginForm.getEmail());

            return ResponseEntity.ok(user.role);
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/ticket/add")
    ResponseEntity<String> addTicket(WebSession session, @RequestBody TicketDbModel ticketDbModel) {
            ticketDbModel.setId(UUID.randomUUID().toString());
            ticketDbModel.setStatus("toDo");
            ticketDbModel.setDate(new Date());
            ticketDbModel.setDepartment(userRepository.findByEmail(sessionStorage.findById(session.getId())).getDepartment());
            ticketDbModel.setName(userRepository.findByEmail(sessionStorage.findById(session.getId())).getName());
            ticketDbModel.setLastname(userRepository.findByEmail(sessionStorage.findById(session.getId())).getLastname());
            ticketDbModel.setEmail(userRepository.findByEmail(sessionStorage.findById(session.getId())).getEmail());
            ticketRepository.save(ticketDbModel);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllTickets")
    List<TicketDbModel> getTickets(WebSession session){
        return ticketRepository.findByEmail(sessionStorage.findById(session.getId()));
    }

    @GetMapping("/createUser")
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
