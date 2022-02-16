package folks.api_flight.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Office {

    PILOT("Pilot"),
    FLIGHTATTENDANT("Flight attendant");

    private final String description;
}
