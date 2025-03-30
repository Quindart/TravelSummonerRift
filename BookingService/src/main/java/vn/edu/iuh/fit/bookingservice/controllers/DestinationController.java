package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.DestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.DestinationResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.DestinationService;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<DestinationResponse>>> getAllDestinations(){
        if(destinationService.getAllDestination().isEmpty()){
            return SuccessEntityResponse.CreateResponse("Không tìm thấy danh sách điểm đến",null);
        }
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy danh sách điểm đến",destinationService.getAllDestination());
    }

    @GetMapping("/{destinationId}")
    public ResponseEntity<MessageResponse<DestinationResponse>> getDestinationById(@PathVariable String destinationId){
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy điểm đến",destinationService.getDestinationById(destinationId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<DestinationResponse>> createDestination(@RequestBody DestinationRequest destinationRequest){
        return SuccessEntityResponse.CreateResponse("Tạo điểm đến thành công", destinationService.createDestination(destinationRequest));
    }

    @PutMapping("/{destinationId}")
    public ResponseEntity<MessageResponse<DestinationResponse>> updateDestination(@PathVariable String destinationId, @RequestBody DestinationRequest destinationRequest){
        return SuccessEntityResponse.OkResponse("Cập nhật điểm đến thành công", destinationService.updateDestination(destinationId, destinationRequest));
    }

    @DeleteMapping("/{destinationId}")
    public ResponseEntity<MessageResponse<Void>> deleteDestination(@PathVariable String destinationId){
        destinationService.deleteDestination(destinationId);
        return SuccessEntityResponse.OkResponse("Xóa điểm đến thành công", null);
    }
}
