package home.task.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationSearchResult extends LocationCommon {

    private Integer distance;

    @Builder
    public LocationSearchResult(String title, String locationType, String lattLong, int woeid, Integer distance) {
        super(title, locationType, lattLong, woeid);
        this.distance = distance;
    }
}
