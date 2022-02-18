package folks.api_flight.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import folks.api_flight.dto.FligthDTO;
import folks.api_flight.enums.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "TB_FLIGTH")
public class Fligth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The starting place is mandatory!")
    private String startingPlace;

    @NotBlank(message = "The arrival place is mandatory!")
    private String arrivalPlace;

    @NotNull(message = "The available seats is mandatory!")
    private int availableSeats;

    @NotNull(message = "The occupied seats is mandatory!")
    private int occupiedSeats;

    @NotNull(message = "The crew needed is mandatory!")
    private int crewNeeded;

    @NotNull(message = "The crew climbed is mandatory!")
    private int crewClimbed;

    @NotBlank(message = "The category is mandatory!")
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(name = "TB_PASSENGER_FLIGTH", joinColumns = @JoinColumn(name = "FLIGTH_ID"), inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID"))
    private List<Passenger> passengers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "TB_CREW_FLIGTH", joinColumns = @JoinColumn(name = "FLIGTH_ID"), inverseJoinColumns = @JoinColumn(name = "CREW_ID"))
    private List<Crew> crews = new ArrayList<>();

    public Fligth(FligthDTO dto) {
        this.availableSeats = dto.getAvailableSeats();
        this.occupiedSeats = dto.getOccupiedSeats();
        this.crewClimbed = dto.getCrewClimbed();
        this.crewNeeded = dto.getCrewNeeded();
        this.startingPlace = dto.getStartingPlace();
        this.arrivalPlace = dto.getArrivalPlace();
        this.category = dto.getCategory();
    }

    public void addCrew(Crew crew) {
        this.crews.add(crew);
    }

    public void removeCrew(Crew crew) {
        this.crews.remove(crew);
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }
}
