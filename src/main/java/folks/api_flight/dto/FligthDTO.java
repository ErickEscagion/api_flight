package folks.api_flight.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import folks.api_flight.entities.Fligth;
import folks.api_flight.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FligthDTO implements Serializable {

    @Schema(description = "ID auto generate")
    private Long id;

    @Schema(example = "50", description = "Total available seats")
    @NotNull(message = "The available seats is mandatory!")
    private int availableSeats;

    @Schema(example = "1", description = "Total occupied seats")
    @NotNull(message = "The occupied seats is mandatory!")
    private int occupiedSeats;

    @Schema(example = "5", description = "Total crew needed")
    @NotNull(message = "The crew needed is mandatory!")
    private int crewNeeded;

    @Schema(example = "2", description = "Total crew climbed")
    @NotNull(message = "The crew climbed is mandatory!")
    private int crewClimbed;

    @Schema(example = "starting point", description = "Flight departure point")
    @NotBlank(message = "The starting place is mandatory!")
    private String startingPlace;

    @Schema(example = "arrival point", description = "Flight end point")
    @NotBlank(message = "The arrival place is mandatory!")
    private String arrivalPlace;

    @Schema(example = "COMMERCIAL", description = "Flight category")
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