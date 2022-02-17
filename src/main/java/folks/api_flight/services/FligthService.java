package folks.api_flight.services;

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

import folks.api_flight.dto.FligthDTO;
import folks.api_flight.entities.Fligth;
import folks.api_flight.enums.Category;
import folks.api_flight.repositories.FligthRepository;

@Service
public class FligthService {
    private String textFligthNotFound = "Fligth not found";
    @Autowired
    private FligthRepository fligthRepository;

    public Page<FligthDTO> getFligths(PageRequest pageRequest, int availableSeats, int occupiedSeats, int crewNeeded,
    int crewClimbed, String startingPlace, String arrivalPlace, Category category) {
        try {
            Page<Fligth> list = fligthRepository.findAll(pageRequest, availableSeats, occupiedSeats,
            crewNeeded, crewClimbed,startingPlace,arrivalPlace,category);
            return list.map(FligthDTO::new);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fligth search gave error");
        }
    }

    
    public FligthDTO postFligth(@Valid FligthDTO dto) {
        Fligth entity = new Fligth(dto);
        entity = fligthRepository.save(entity);
        return new FligthDTO(entity);
    }

    public FligthDTO getFligthById(Long id) {
        Optional<Fligth> op = fligthRepository.findById(id);
        Fligth fligth = op
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, textFligthNotFound));
        return new FligthDTO(fligth);
    }

    public void deleteFligth(Long id){
        try{
            fligthRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, textFligthNotFound);
        }
    }
    public FligthDTO updateFligth(Long id, @Valid FligthDTO dto){
        try {
            Fligth entity = fligthRepository.getById(id);  
            entity.setArrivalPlace(dto.getArrivalPlace());          
            entity.setAvailableSeats(dto.getAvailableSeats());
            entity.setCategory(dto.getCategory());
            entity.setCrewClimbed(dto.getCrewClimbed());
            entity.setCrewNeeded(dto.getCrewNeeded());
            entity.setOccupiedSeats(dto.getOccupiedSeats());
            entity.setStartingPlace(dto.getStartingPlace());
            entity = fligthRepository.save(entity);
            return new FligthDTO(entity);
        }
        catch (EntityNotFoundException ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, textFligthNotFound);
        }   
    }

}
