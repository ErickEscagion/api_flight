package folks.api_flight.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.InheritanceType;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_BASEUSER")
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUser implements Serializable {

    
    @Schema(description = "ID auto generate")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Full name", description = "Full name")
    @NotBlank(message = "The user name is mandatory!")
    private String name;

    @Schema(example = "example@dominio", description = "Email")
    @NotBlank(message = "Email is mandatory!")
    @Email
    @Column(unique = true)
    private String email;

    @Schema(example = "(xx)xxxxx-xxxx", description = "Phone number with mask")
    @NotBlank(message = "The phone number is mandatory!")
    private String phoneNumber;

    @Schema(example = "Full address", description = "Full address")
    @NotBlank(message = "The address is mandatory!")
    private String address;

    public BaseUser(String name, @Email String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
