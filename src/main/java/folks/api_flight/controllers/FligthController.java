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

import folks.api_flight.dto.CrewDTO;
import folks.api_flight.dto.FligthDTO;
import folks.api_flight.dto.PassengerDTO;
import folks.api_flight.enums.Category;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import folks.api_flight.services.FligthService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/fligth")
public class FligthController {

    @Autowired
    private FligthService fligthService;

    @Operation(summary = "GET fligths", description = "Route to get all fligths", tags = "Fligth")
    @GetMapping
    public ResponseEntity<Page<FligthDTO>> getFligths(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "availableSeats", defaultValue = "0") int availableSeats,
            @RequestParam(value = "occupiedSeats", defaultValue = "0") int occupiedSeats,
            @RequestParam(value = "crewNeeded", defaultValue = "0") int crewNeeded,
            @RequestParam(value = "crewClimbed", defaultValue = "0") int crewClimbed,
            @RequestParam(value = "startingPlace", defaultValue = "") String startingPlace,
            @RequestParam(value = "arrivalPlace", defaultValue = "") String arrivalPlace,
            @RequestParam(value = "category", defaultValue = "") Category category) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<FligthDTO> list = fligthService.getFligths(pageRequest, availableSeats, occupiedSeats,
                crewNeeded, crewClimbed, startingPlace, arrivalPlace, category);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "POST fligth", description = "Route to post fligth", tags = "Fligth")
    @PostMapping
    public ResponseEntity<FligthDTO> postFligth(@Valid @RequestBody FligthDTO insertDto) {
        FligthDTO dto = fligthService.postFligth(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(summary = "GET fligth per ID", description = "Route to get fligth per ID", tags = "Fligth")
    @GetMapping("{id}")
    public ResponseEntity<FligthDTO> getFligthById(@PathVariable Long id) {
        FligthDTO dto = fligthService.getFligthById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "DELETE fligth per ID", description = "Route to fligth passenger per ID", tags = "Fligth")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFligth(@PathVariable Long id) {
        fligthService.deleteFligth(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "PUT fligth per ID", description = "Route to put fligth per ID", tags = "Fligth")
    @PutMapping("{id}")
    public ResponseEntity<FligthDTO> updateFligth(@Valid @PathVariable Long id,
            @Valid @RequestBody FligthDTO updateDto) {
        FligthDTO dto = fligthService.updateFligth(id, updateDto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "GET Fligth/Passenger", description = "Route to see all passengers on the flight", tags = "Fligt <-> Passenger")
    @GetMapping("/{id}/passengers")
    public ResponseEntity<Page<PassengerDTO>> getFligthPassengers(
            @PathVariable Long id,
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

        Page<PassengerDTO> places = fligthService.getFligthPassengers(pageRequest, id, passengerName, passengerEmail,
                passengerPhoneNumber, passengerAddress, passengerEmergencyPhoneNumber);

        return ResponseEntity.ok(places);
    }

    @Operation(summary = "POST Fligth/Passenger", description = "Route to connect the passenger to the flight", tags = "Fligt <-> Passenger")
    @PostMapping("/{id}/passengers/{passengerId}")
    public ResponseEntity<PassengerDTO> insertPassenger(@PathVariable Long id, @PathVariable Long passengerId) {
        PassengerDTO dto = fligthService.insertPassenger(id, passengerId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "DELETE Fligth/Passenger", description = "Route to remove a passenger from a flight", tags = "Fligt <-> Passenger")
    @DeleteMapping("{id}/passengers/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id, @PathVariable Long passengerId) {
        fligthService.deletePassenger(id, passengerId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "GET Fligth/Crew", description = "Route to see all crews on the flight(filter to enum)", tags = "Fligt <-> Crew")
    @GetMapping("/{id}/crews")
    public ResponseEntity<Page<CrewDTO>> getFligthCrews(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "name", defaultValue = "") String crewName,
            @RequestParam(value = "email", defaultValue = "") String crewEmail,
            @RequestParam(value = "phoneNumber", defaultValue = "") String crewPhoneNumber,
            @RequestParam(value = "address", defaultValue = "") String crewAddress,
            @RequestParam(value = "salary", defaultValue = "0") Double crewSalary,
            @RequestParam(value = "level", defaultValue = "") Level crewLevel,
            @RequestParam(value = "office", defaultValue = "") Office crewOffice) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<CrewDTO> places = fligthService.getFligthCrews(pageRequest, id, crewName,
                crewEmail, crewPhoneNumber, crewAddress, crewSalary, crewLevel, crewOffice);

        return ResponseEntity.ok(places);
    }

    @Operation(summary = "GET Fligth/Crew", description = "Route to see all crews on the flight", tags = "Fligt <-> Crew")
    @GetMapping("/{id}/crewsAll")
    public ResponseEntity<Page<CrewDTO>> getFligthCrewsAll(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "name", defaultValue = "") String crewName,
            @RequestParam(value = "email", defaultValue = "") String crewEmail,
            @RequestParam(value = "phoneNumber", defaultValue = "") String crewPhoneNumber,
            @RequestParam(value = "address", defaultValue = "") String crewAddress,
            @RequestParam(value = "salary", defaultValue = "0") Double crewSalary) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<CrewDTO> places = fligthService.getFligthCrewsAll(pageRequest, id, crewName,
                crewEmail, crewPhoneNumber, crewAddress, crewSalary);

        return ResponseEntity.ok(places);
    }

    @Operation(summary = "POST Fligth/Crew", description = "Route to connect the crew to the flight", tags = "Fligt <-> Crew")
    @PostMapping("/{id}/crews/{crewId}")
    public ResponseEntity<CrewDTO> insertCrew(@PathVariable Long id, @PathVariable Long crewId) {
        CrewDTO dto = fligthService.insertCrew(id, crewId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "DELETE Fligth/Crew", description = "Route to remove a crew from a flight", tags = "Fligt <-> Crew")
    @DeleteMapping("{id}/crews/{crewId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id, @PathVariable Long crewId) {
        fligthService.deleteCrew(id, crewId);
        return ResponseEntity.noContent().build();
    }

}