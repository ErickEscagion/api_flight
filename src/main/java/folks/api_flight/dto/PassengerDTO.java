package folks.api_flight.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Passenger;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PassengerDTO extends BaseUser {

    @Schema(example = "(xx)xxxxx-xxxx", description = "Passenger emergency phone")
    @NotBlank(message = "The emergency phone number is mandatory!")
    private String emergencyPhoneNumber;

    public PassengerDTO(
            Long id,
            @NotBlank(message = "The user name is mandatory!") String name,
            @NotBlank(message = "Email is mandatory!") @Email String email,
            @NotBlank(message = "The phone number is mandatory!") String phoneNumber,
            @NotBlank(message = "The address is mandatory!") String address,
            @NotBlank(message = "The emergency phone number is mandatory!") String emergencyPhoneNumber) {
        super(id, name, email, phoneNumber, address);
        this.emergencyPhoneNumber = emergencyPhoneNumber;
    }

    public PassengerDTO(Passenger passenger) {
        this.setId(passenger.getId());
        this.setName(passenger.getName());
        this.setEmail(passenger.getEmail());
        this.setPhoneNumber(passenger.getPhoneNumber());
        this.setAddress(passenger.getAddress());
        this.emergencyPhoneNumber = passenger.getEmergencyPhoneNumber();
    }

}
