package folks.api_flight.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import folks.api_flight.entities.Fligth;
import folks.api_flight.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FligthDTO implements Serializable {

    private Long id;

    @NotNull(message = "The available seats is mandatory!")
    private int availableSeats;

    @NotNull(message = "The occupied seats is mandatory!")
    private int occupiedSeats;

    @NotNull(message = "The crew needed is mandatory!")
    private int crewNeeded;

    @NotNull(message = "The crew climbed is mandatory!")
    private int crewClimbed;

    @NotBlank(message = "The starting place is mandatory!")
    private String startingPlace;

    @NotBlank(message = "The arrival place is mandatory!")
    private String arrivalPlace;

    @NotBlank(message = "The category is mandatory!")
    @Enumerated(EnumType.STRING)
    private Category category;

    public FligthDTO(Fligth fligth) {
        this.id = fligth.getId();
        this.availableSeats = fligth.getAvailableSeats();
        this.occupiedSeats = fligth.getOccupiedSeats();
        this.crewClimbed = fligth.getCrewClimbed();
        this.crewNeeded = fligth.getCrewNeeded();
        this.startingPlace = fligth.getStartingPlace();
        this.arrivalPlace = fligth.getArrivalPlace();
        this.category = fligth.getCategory();
    }
}