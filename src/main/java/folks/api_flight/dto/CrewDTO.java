package folks.api_flight.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Crew;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CrewDTO extends BaseUser {
    @NotBlank(message = "The salary is mandatory!")
    @Column(nullable = false)
    private Double salary;

    @NotBlank(message = "The level is mandatory!")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @NotBlank(message = "The office is mandatory!")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Office office;

    public CrewDTO(
            Long id,
            @NotBlank(message = "The user name is mandatory!") String name,
            @NotBlank(message = "Email is mandatory!") @Email String email,
            @NotBlank(message = "The phone number is mandatory!") String phoneNumber,
            @NotBlank(message = "The address is mandatory!") String address,
            @NotBlank(message = "The salary is mandatory!") Double salary,
            @NotBlank(message = "The level is mandatory!") Level level,
            @NotBlank(message = "The office is mandatory!") Office office) {
        super(id, name, email, phoneNumber, address);
        this.salary = salary;
        this.level = level;
        this.office = office;
    }

    public CrewDTO(Crew crew) {
        this.setId(crew.getId());
        this.setName(crew.getName());
        this.setEmail(crew.getEmail());
        this.setPhoneNumber(crew.getPhoneNumber());
        this.setAddress(crew.getAddress());
        this.salary = crew.getSalary();
        this.level = crew.getLevel();
        this.office = crew.getOffice();
    }
}
