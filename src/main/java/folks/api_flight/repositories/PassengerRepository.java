package folks.api_flight.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import folks.api_flight.entities.BaseUser;
import folks.api_flight.entities.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
        @Query("SELECT a FROM Passenger a " +
                        "WHERE" +
                        "(LOWER(a.name) LIKE LOWER(CONCAT('%', :passengerName,'%'))) AND " +
                        "(LOWER(a.email) LIKE LOWER(CONCAT('%', :passengerEmail,'%'))) AND " +
                        "(LOWER(a.address) LIKE LOWER(CONCAT('%', :passengerAddress,'%'))) AND " +
                        "(LOWER(a.emergencyPhoneNumber) LIKE LOWER(CONCAT('%', :passengerEmergencyPhoneNumber,'%'))) AND "
                        +
                        "(LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :passengerPhoneNumber,'%'))) ")
        public Page<Passenger> findAll(Pageable pageRequest, String passengerName, String passengerEmail,
                        String passengerPhoneNumber, String passengerAddress, String passengerEmergencyPhoneNumber);

        @Query("SELECT u FROM BaseUser u " +
                        "WHERE LOWER(u.email) = LOWER(:email)")
        public BaseUser findByEmail(String email);
}
