package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TicketRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.CategoryTourResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TicketResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tickets")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    @GetMapping
    public ResponseEntity<MessageResponse<List<TicketResponse>>> getAllTickets() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", ticketService.getTickets());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<MessageResponse<TicketResponse>> getTicketById(@PathVariable String ticketId){

        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", ticketService.getTicketById(ticketId));
    }

    @PostMapping
    public  ResponseEntity<MessageResponse<TicketResponse>> createTicket(@RequestBody TicketRequest ticketRequest){
        return SuccessEntityResponse.CreateResponse("Tạo vé thành công", ticketService.createTicket(ticketRequest));
    }
}
