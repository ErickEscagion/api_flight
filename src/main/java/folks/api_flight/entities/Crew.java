package folks.api_flight.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import folks.api_flight.dto.CrewDTO;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CREW")
@PrimaryKeyJoinColumn(name = "CREW_ID")
public class Crew extends BaseUser {

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

    public Crew(
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

    public Crew(Crew crew) {
        this.salary = crew.getSalary();
        this.level = crew.getLevel();
        this.office = crew.getOffice();
    }

    public Crew(CrewDTO dto) {
        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setEmail(dto.getEmail());
        this.setPhoneNumber(dto.getPhoneNumber());
        this.setAddress(dto.getAddress());
        this.salary = dto.getSalary();
        this.level = dto.getLevel();
        this.office = dto.getOffice();
    }
}
