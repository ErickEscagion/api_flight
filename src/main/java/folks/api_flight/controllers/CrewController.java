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
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import folks.api_flight.services.CrewService;

@RestController
@RequestMapping("/api/v1/crew")
public class CrewController {

    @Autowired
    private CrewService crewService;

    @GetMapping
    public ResponseEntity<Page<CrewDTO>> getCrews(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "name", defaultValue = "") String crewName,
            @RequestParam(value = "email", defaultValue = "") String crewEmail,
            @RequestParam(value = "phoneNumber", defaultValue = "") String crewPhoneNumber,
            @RequestParam(value = "salary", defaultValue = "0") Double crewSalary,
            @RequestParam(value = "level", defaultValue = "") Level crewLevel,
            @RequestParam(value = "office", defaultValue = "") Office crewOffice,
            @RequestParam(value = "address", defaultValue = "") String crewAddress) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Page<CrewDTO> list = crewService.getCrews(pageRequest, crewName, crewEmail,
                crewPhoneNumber, crewAddress, crewSalary, crewLevel, crewOffice);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CrewDTO> postCrew(@Valid @RequestBody CrewDTO insertDto) {
        CrewDTO dto = crewService.postCrew(insertDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("{id}")
    public ResponseEntity<CrewDTO> getCrewById(@PathVariable Long id) {
        CrewDTO dto = crewService.getCrewById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCrew(@PathVariable Long id) {
        crewService.deleteCrew(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CrewDTO> updateCrew(@Valid @PathVariable Long id, @Valid @RequestBody CrewDTO updateDto) {
        CrewDTO dto = crewService.updateCrew(id, updateDto);
        return ResponseEntity.ok().body(dto);
    }
}
