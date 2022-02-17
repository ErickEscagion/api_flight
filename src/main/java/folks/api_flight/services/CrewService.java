package folks.api_flight.services;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import folks.api_flight.dto.CrewDTO;
import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Crew;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import folks.api_flight.repositories.CrewRepository;

@Service
public class CrewService {
    private String textCrewNotFound = "Crew not found";
    @Autowired
    private CrewRepository crewRepository;

    public Page<CrewDTO> getCrews(PageRequest pageRequest, String crewName, String crewEmail,
    String crewPhoneNumber, String crewAddress, Double crewSalary, Level crewLevel, Office crewOffice) {
        try {
            Page<Crew> list = crewRepository.findAll(pageRequest, crewName, crewEmail,
            crewPhoneNumber, crewAddress,crewSalary,crewLevel,crewOffice);
            return list.map(CrewDTO::new);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Crew search gave error");
        }
    }

    
    public CrewDTO postCrew(@Valid CrewDTO dto) {
        BaseUser user = crewRepository.findByEmail(dto.getEmail());
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This e-mail already exists");
        }
        Crew entity = new Crew(dto);
        entity = crewRepository.save(entity);
        return new CrewDTO(entity);
    }

    public CrewDTO getCrewById(Long id) {
        Optional<Crew> op = crewRepository.findById(id);
        Crew crew = op
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, textCrewNotFound));
        return new CrewDTO(crew);
    }

    public void deleteCrew(Long id){
        try{
            crewRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, textCrewNotFound);
        }
    }
    public CrewDTO updateCrew(Long id, @Valid CrewDTO dto){
        try {
            Crew entity = crewRepository.getById(id);            
            BaseUser user = crewRepository.findByEmail(dto.getEmail());
            if (user != null && !Objects.equals(user.getEmail(), entity.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This e-mail already exists");
            }

            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setEmail(dto.getEmail());
            entity.setName(dto.getName());
            entity.setAddress(dto.getAddress());
            entity.setLevel(dto.getLevel());
            entity.setOffice(dto.getOffice());
            entity.setSalary(dto.getSalary());
            entity = crewRepository.save(entity);
            return new CrewDTO(entity);
        }
        catch (EntityNotFoundException ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, textCrewNotFound);
        }   
    }
}
