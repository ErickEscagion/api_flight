package folks.api_flight.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Crew;
import folks.api_flight.enums.Level;
import folks.api_flight.enums.Office;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
        @Query("SELECT a FROM Crew a " +
                        "WHERE" +
                        "(LOWER(a.name) LIKE LOWER(CONCAT('%', :crewName,'%'))) AND " +
                        "(LOWER(a.email) LIKE LOWER(CONCAT('%', :crewEmail,'%'))) AND " +
                        "(LOWER(a.address) LIKE LOWER(CONCAT('%', :crewAddress,'%'))) AND " +
                        "a.salary >= :crewSalary AND " +
                        "a.level = :crewLevel AND " +
                        "a.office = :crewOffice AND " +
                        "(LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :crewPhoneNumber,'%'))) ")
        public Page<Crew> findAll(Pageable pageRequest, String crewName, String crewEmail,
                        String crewPhoneNumber, String crewAddress, Double crewSalary, Level crewLevel,
                        Office crewOffice);

        @Query("SELECT u FROM BaseUser u " +
                        "WHERE LOWER(u.email) = LOWER(:email)")
        public BaseUser findByEmail(String email);

        @Query("SELECT a FROM Crew a " +
                        "LEFT JOIN a.fligths e " +
                        "WHERE " +
                        "(e.id = :fligthId) AND " +
                        "(LOWER(a.name) LIKE LOWER(CONCAT('%', :crewName,'%'))) AND " +
                        "(LOWER(a.email) LIKE LOWER(CONCAT('%', :crewEmail,'%'))) AND " +
                        "(LOWER(a.address) LIKE LOWER(CONCAT('%', :crewAddress,'%'))) AND " +
                        "a.salary >= :crewSalary AND " +
                        "a.level = :crewLevel AND " +
                        "a.office = :crewOffice AND " +
                        "(LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :crewPhoneNumber,'%'))) ")
        public Page<Crew> findAllByFligthId(Pageable pageRequest, Long fligthId, String crewName, String crewEmail,
                        String crewPhoneNumber, String crewAddress, Double crewSalary, Level crewLevel,
                        Office crewOffice);
}
