package folks.api_flight.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {

    INTERN("Intern"),
    JUNIOR("Junior"),
    PLENO("Pleno"),
    SENIOR("Senior");

    private final String description;
}
