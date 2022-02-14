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

import folks.api_flight.dto.PassengerDTO;
import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Passenger;
import folks.api_flight.repositories.PassengerRepository;
import java.util.Objects;

@Service
public class PassengerService {
    private String textPassengerNotFound = "Passenger not found";
    @Autowired
    private PassengerRepository passengerRepository;

    public Page<PassengerDTO> getPassengers(PageRequest pageRequest, String passengerName, String passengerEmail,
            String passengerPhoneNumber, String passengerAddress, String passengerEmergencyPhoneNumber) {
        try {
            Page<Passenger> list = passengerRepository.findAll(pageRequest, passengerName, passengerEmail,
                    passengerPhoneNumber, passengerAddress, passengerEmergencyPhoneNumber);
            return list.map(PassengerDTO::new);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger search gave error");
        }
    }

    public PassengerDTO postPassenger(@Valid PassengerDTO dto) {
        BaseUser user = passengerRepository.findByEmail(dto.getEmail());
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This e-mail already exists");
        }
        Passenger entity = new Passenger(dto);
        entity = passengerRepository.save(entity);
        return new PassengerDTO(entity);
    }

    public PassengerDTO getPassengerById(Long id) {
        Optional<Passenger> op = passengerRepository.findById(id);
        Passenger passenger = op
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, textPassengerNotFound));
        return new PassengerDTO(passenger);
    }

    public void deletePassenger(Long id){
        try{
            passengerRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, textPassengerNotFound);
        }
    }
    public PassengerDTO updatePassenger(Long id, @Valid PassengerDTO dto){
        try {
            Passenger entity = passengerRepository.getById(id);            
            BaseUser user = passengerRepository.findByEmail(dto.getEmail());
            if (user != null && !Objects.equals(user.getEmail(), entity.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This e-mail already exists");
            }

            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setEmail(dto.getEmail());
            entity.setName(dto.getName());
            entity.setAddress(dto.getAddress());
            entity.setEmergencyPhoneNumber(dto.getEmergencyPhoneNumber());
            entity = passengerRepository.save(entity);
            return new PassengerDTO(entity);
        }
        catch (EntityNotFoundException ex) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, textPassengerNotFound);
        }   
    }
}
