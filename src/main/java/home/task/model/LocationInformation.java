package home.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationInformation extends LocationCommon {

    @JsonProperty("consolidated_weather")
    private List<ConsolidatedWeather> consolidatedWeather;
    private JsonNode parent;
    private List<JsonNode> sources;
    private String time;
    @JsonProperty("sun_rise")
    private String sunRise;
    @JsonProperty("sun_set")
    private String sunSet;
    @JsonProperty("timezone_name")
    private String timezoneName;
    private String timezone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LocationInformation that = (LocationInformation) o;
        return Objects.equals(parent, that.parent) &&
                Objects.equals(sources, that.sources) &&
                Objects.equals(timezoneName, that.timezoneName) &&
                Objects.equals(timezone, that.timezone);
    }
}
