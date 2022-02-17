package folks.api_flight.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import folks.api_flight.entities.Fligth;
import folks.api_flight.enums.Category;

@Repository
public interface FligthRepository extends JpaRepository<Fligth, Long> {
    @Query("SELECT a FROM Fligth a " +
            "WHERE" +
            "(LOWER(a.startingPlace) LIKE LOWER(CONCAT('%', :startingPlace,'%'))) AND " +
            "a.availableSeats >= :availableSeats AND " +
            "a.occupiedSeats >= :occupiedSeats AND " +
            "a.crewNeeded >= :crewNeeded AND " +
            "a.crewClimbed >= :crewClimbed AND " +
            "a.category = :category AND " +
            "(LOWER(a.arrivalPlace) LIKE LOWER(CONCAT('%', :arrivalPlace,'%'))) ")
    public Page<Fligth> findAll(Pageable pageRequest, int availableSeats, int occupiedSeats, int crewNeeded,
            int crewClimbed, String startingPlace, String arrivalPlace, Category category);
}