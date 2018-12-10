
package koreatech.cse.domain.rest;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "baseDate",
    "baseTime",
    "category",
    "nx",
    "ny",
    "obsrValue"
})
public class Item {

    @JsonProperty("baseDate")
    private Long baseDate;
    @JsonProperty("baseTime")
    private String baseTime;
    @JsonProperty("category")
    private String category;
    @JsonProperty("nx")
    private Long nx;
    @JsonProperty("ny")
    private Long ny;
    @JsonProperty("obsrValue")
    private Double obsrValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("baseDate")
    public Long getBaseDate() {
        return baseDate;
    }

    @JsonProperty("baseDate")
    public void setBaseDate(Long baseDate) {
        this.baseDate = baseDate;
    }

    @JsonProperty("baseTime")
    public String getBaseTime() {
        return baseTime;
    }

    @JsonProperty("baseTime")
    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("nx")
    public Long getNx() {
        return nx;
    }

    @JsonProperty("nx")
    public void setNx(Long nx) {
        this.nx = nx;
    }

    @JsonProperty("ny")
    public Long getNy() {
        return ny;
    }

    @JsonProperty("ny")
    public void setNy(Long ny) {
        this.ny = ny;
    }

    @JsonProperty("obsrValue")
    public Double getObsrValue() {
        return obsrValue;
    }

    @JsonProperty("obsrValue")
    public void setObsrValue(Double obsrValue) {
        this.obsrValue = obsrValue;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Item{" +
                "baseDate=" + baseDate +
                ", baseTime='" + baseTime + '\'' +
                ", category='" + category + '\'' +
                ", nx=" + nx +
                ", ny=" + ny +
                ", obsrValue=" + obsrValue +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
