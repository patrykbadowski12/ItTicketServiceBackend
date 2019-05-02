package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController()
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SessionStorage sessionStorage;

    @GetMapping("/admin/getAllTickets")
    ResponseEntity<List<TicketDbModel>> getAllTickets(){
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @PutMapping("/admin/updateStatusTicket")
    ResponseEntity<Void> changeStatusTicket(@RequestBody TicketDbModel ticketDbModel){
        ticketDbModel.setStatus("Done");
        ticketRepository.save(ticketDbModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/getAllToDo")
    ResponseEntity<List<TicketDbModel>> getAllToDoTickets(){
        return ResponseEntity.ok(ticketRepository.findByStatus("toDo"));
    }

    @GetMapping("/admin/getAllDone")
    ResponseEntity<List<TicketDbModel>> getAllDoneTickets(){
        return ResponseEntity.ok(ticketRepository.findByStatus("Done"));
    }

}
