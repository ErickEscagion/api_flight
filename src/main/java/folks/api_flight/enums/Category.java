package folks.api_flight.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    COMMERCIAL("commercial"),
    PARTICULAR("particular");

    private final String description;
}
