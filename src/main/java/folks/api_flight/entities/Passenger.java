package folks.api_flight.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.JoinColumn;

import folks.api_flight.dto.PassengerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PASSENGER")
@PrimaryKeyJoinColumn(name = "PASSENGER_ID")
public class Passenger extends BaseUser {

    @NotBlank(message = "The emergency phone number is mandatory!")
    private String emergencyPhoneNumber;

    @ManyToMany
    @JoinTable(
        name="TB_PASSENGER_FLIGTH",
        joinColumns =  @JoinColumn(name="PASSENGER_ID"),
        inverseJoinColumns = @JoinColumn(name="FLIGTH_ID")
    )
    private List<Fligth> fligths = new ArrayList<>();
    

    public Passenger(
            Long id,
            @NotBlank(message = "The user name is mandatory!") String name,
            @NotBlank(message = "Email is mandatory!") @Email String email,
            @NotBlank(message = "The phone number is mandatory!") String phoneNumber,
            @NotBlank(message = "The address is mandatory!") String address,
            @NotBlank(message = "The emergency phone number is mandatory!") String emergencyPhoneNumber) {
        super(id, name, email, phoneNumber, address);
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public Passenger(Passenger passenger) {
        this.emergencyPhoneNumber = passenger.getEmergencyPhoneNumber();
    }

    public Passenger(PassengerDTO dto) {
        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setEmail(dto.getEmail());
        this.setPhoneNumber(dto.getPhoneNumber());
        this.setAddress(dto.getAddress());
        this.emergencyPhoneNumber = dto.getEmergencyPhoneNumber();
    }
}
