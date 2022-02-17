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

import folks.api_flight.dto.CrewDTO;
import folks.api_flight.dto.FligthDTO;
import folks.api_flight.dto.PassengerDTO;
import folks.api_flight.entities.Crew;
import folks.api_flight.entities.Fligth;
import folks.api_flight.entities.Passenger;
import folks.api_flight.enums.Category;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import folks.api_flight.repositories.CrewRepository;
import folks.api_flight.repositories.FligthRepository;
import folks.api_flight.repositories.PassengerRepository;

@Service
public class FligthService {
    private String textFligthNotFound = "Fligth not found";
    @Autowired
    private FligthRepository fligthRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CrewRepository crewRepository;

    public Page<FligthDTO> getFligths(PageRequest pageRequest, int availableSeats, int occupiedSeats, int crewNeeded,
            int crewClimbed, String startingPlace, String arrivalPlace, Category category) {
        try {
            Page<Fligth> list = fligthRepository.findAll(pageRequest, availableSeats, occupiedSeats,
                    crewNeeded, crewClimbed, startingPlace, arrivalPlace, category);
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

    public void deleteFligth(Long id) {
        try {
            fligthRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, textFligthNotFound);
        }
    }

    public FligthDTO updateFligth(Long id, @Valid FligthDTO dto) {
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
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, textFligthNotFound);
        }
    }

    public Page<PassengerDTO> getFligthPassengers(PageRequest pageRequest, long id, String passengerName,
            String passengerEmail, String passengerPhoneNumber, String passengerAddress,
            String passengerEmergencyPhoneNumber) {
        Optional<Fligth> op = fligthRepository.findById(id);

        Fligth fligth = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fligth not found"));

        Page<Passenger> passengers = passengerRepository.findAllByFligthId(pageRequest, fligth.getId(), passengerName,
                passengerEmail, passengerPhoneNumber, passengerAddress, passengerEmergencyPhoneNumber);

        return passengers.map(PassengerDTO::new);
    }

    public Page<CrewDTO> getFligthCrews(PageRequest pageRequest, long id, String crewName, String crewEmail,
            String crewPhoneNumber, String crewAddress, Double crewSalary, Level crewLevel,
            Office crewOffice) {
        Optional<Fligth> op = fligthRepository.findById(id);

        Fligth fligth = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fligth not found"));

        Page<Crew> crews = crewRepository.findAllByFligthId(pageRequest, fligth.getId(), crewName,
                crewEmail, crewPhoneNumber, crewAddress, crewSalary, crewLevel, crewOffice);

        return crews.map(CrewDTO::new);
    }

}