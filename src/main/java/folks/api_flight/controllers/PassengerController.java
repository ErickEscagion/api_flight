package folks.api_flight.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import folks.api_flight.dto.PassengerDTO;
import folks.api_flight.services.PassengerService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Operation(summary = "POST passenger", description = "Route to post passenger", tags = "Passenger")
    @PostMapping
    public ResponseEntity<PassengerDTO> postPassenger(@Valid @RequestBody PassengerDTO insertDto) {
        PassengerDTO dto = passengerService.postPassenger(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(summary = "GET passengers", description = "Route to get all passengers", tags = "Passenger")
    @GetMapping
    public ResponseEntity<Page<PassengerDTO>> getPassengers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "name", defaultValue = "") String passengerName,
            @RequestParam(value = "email", defaultValue = "") String passengerEmail,
            @RequestParam(value = "phoneNumber", defaultValue = "") String passengerPhoneNumber,
            @RequestParam(value = "address", defaultValue = "") String passengerAddress,
            @RequestParam(value = "emergencyPhoneNumber", defaultValue = "") String passengerEmergencyPhoneNumber) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<PassengerDTO> list = passengerService.getPassengers(pageRequest, passengerName, passengerEmail,
                passengerPhoneNumber, passengerAddress, passengerEmergencyPhoneNumber);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "GET passenger per ID", description = "Route to get passenger per ID", tags = "Passenger")
    @GetMapping("{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        PassengerDTO dto = passengerService.getPassengerById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "DELETE passenger per ID", description = "Route to delete passenger per ID", tags = "Passenger")
    @DeleteMapping("{id}")
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id){
		passengerService.deletePassenger(id); 
		return ResponseEntity.noContent().build();
	}

    @Operation(summary = "PUT passenger per ID", description = "Route to put passenger per ID", tags = "Passenger")
    @PutMapping("{id}")
	public ResponseEntity<PassengerDTO> updatePassenger(@Valid @PathVariable Long id, @Valid @RequestBody PassengerDTO updateDto){
		PassengerDTO dto = passengerService.updatePassenger(id, updateDto); 
		return ResponseEntity.ok().body(dto);
	}
}
