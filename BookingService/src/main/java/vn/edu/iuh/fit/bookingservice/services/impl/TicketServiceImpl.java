//package vn.edu.iuh.fit.bookingservice.services.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Service;
//import vn.edu.iuh.fit.bookingservice.dtos.requests.TicketRequest;
//import vn.edu.iuh.fit.bookingservice.dtos.responses.TicketResponse;
//import vn.edu.iuh.fit.bookingservice.entities.Ticket;
//import vn.edu.iuh.fit.bookingservice.enums.TicketType;
//import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
//import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
//import vn.edu.iuh.fit.bookingservice.mapper.TicketMapper;
//import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
//import vn.edu.iuh.fit.bookingservice.services.TicketService;
//import java.util.List;
//
//@Service
//public class TicketServiceImpl implements TicketService {
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Autowired
//    private TicketMapper ticketMapper;
//
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @Override
//    public List<TicketResponse> getTickets() {
//        return ticketRepository.findAll().stream().map(ticketMapper::toDTO).toList();
//    }
//
//    @Override
//    public  TicketResponse getTicketById(String ticketId){
//       Ticket current_ticket = checkedTicketExists(ticketId);
//       return ticketMapper.toDTO(current_ticket);
//    }
//
//    @Override
//    public TicketResponse createTicket(TicketRequest ticketRequest) {
//        try {
//            Ticket ticket_saved =  ticketRepository.save(ticketMapper.toEntity(ticketRequest));
////            System.out.println(ticket_saved);
//
//            return ticketMapper.toDTO(ticket_saved);
//        }
//        catch (Exception e) {
//            throw new InternalServerErrorException("Tạo vé thất bại");
//        }
//    }
//
//    @Override
//    public TicketResponse updateTicket(String ticketId, TicketRequest ticketRequest) {
//        try{
//            //TODO: Check exist ticket
//            Ticket old_ticket = checkedTicketExists(ticketId);
//
//            old_ticket.setTicketType(TicketType.valueOf(ticketRequest.getTicketType()));
//            old_ticket.setPrice(ticketRequest.getPrice());
//            old_ticket.setNote(ticketRequest.getNote());
//            old_ticket.setStatus(ticketRequest.getStatus());
//
//            ticketRepository.save(old_ticket);
//            return ticketMapper.toDTO(old_ticket);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new InternalServerErrorException("Cập nhật vé thất bại");
//        }
//    }
//
//    @Override
//    public void deleteTicket(String ticketId) {
//        try{
//            Ticket old_ticket = checkedTicketExists(ticketId);
//            ticketRepository.deleteById(old_ticket.getTicketId());
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new InternalServerErrorException("Xóa vé thất bại");
//        }
//    }
//
//    //TODO: Helper
//    private Ticket checkedTicketExists(String ticketId) {
//        if(ticketRepository.existsById(ticketId)) {
//            return ticketRepository.findById(ticketId).orElse(null);
//        }
//        throw new NotFoundException("Vé không tồn tại");
//    }
//}
