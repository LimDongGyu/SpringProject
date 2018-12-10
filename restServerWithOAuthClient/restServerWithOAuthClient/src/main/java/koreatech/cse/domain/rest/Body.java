
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
    "items",
    "numOfRows",
    "pageNo",
    "totalCount"
})
public class Body {

    @JsonProperty("items")
    private Items items;
    @JsonProperty("numOfRows")
    private Long numOfRows;
    @JsonProperty("pageNo")
    private Long pageNo;
    @JsonProperty("totalCount")
    private Long totalCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("items")
    public Items getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items items) {
        this.items = items;
    }

    @JsonProperty("numOfRows")
    public Long getNumOfRows() {
        return numOfRows;
    }

    @JsonProperty("numOfRows")
    public void setNumOfRows(Long numOfRows) {
        this.numOfRows = numOfRows;
    }

    @JsonProperty("pageNo")
    public Long getPageNo() {
        return pageNo;
    }

    @JsonProperty("pageNo")
    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    @JsonProperty("totalCount")
    public Long getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
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
        return "Body{" +
                "items=" + items +
                ", numOfRows=" + numOfRows +
                ", pageNo=" + pageNo +
                ", totalCount=" + totalCount +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
