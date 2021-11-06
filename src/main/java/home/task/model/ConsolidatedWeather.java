package home.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsolidatedWeather {

    private long id;
    @JsonProperty("weather_state_name")
    private String weatherStateName;
    @JsonProperty("weather_state_abbr")
    private String weatherStateAbbr;
    @JsonProperty("wind_direction_compass")
    private String windDirectionCompass;
    private String created;
    @JsonProperty("applicable_date")
    private String applicableDate;
    @JsonProperty("min_temp")
    private double minTemp;
    @JsonProperty("max_temp")
    private double maxTemp;
    @JsonProperty("the_temp")
    private double theTemp;
    @JsonProperty("wind_speed")
    private double windSpeed;
    @JsonProperty("wind_direction")
    private double windDirection;
    @JsonProperty("air_pressure")
    private double airPressure;
    private double humidity;
    private double visibility;
    private int predictability;

}
