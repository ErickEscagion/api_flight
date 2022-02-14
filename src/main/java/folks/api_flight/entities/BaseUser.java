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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The user name is mandatory!")
    private String name;

    @NotBlank(message = "Email is mandatory!")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "The phone number is mandatory!")
    private String phoneNumber;

    @NotBlank(message = "The address is mandatory!")
    private String address;

    public BaseUser(String name, @Email String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
