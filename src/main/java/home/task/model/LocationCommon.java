package home.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationCommon {

    private String title;
    @JsonProperty("location_type")
    private String locationType;
    @JsonProperty("latt_long")
    private String lattLong;
    private int woeid;

}
