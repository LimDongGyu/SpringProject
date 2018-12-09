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
        "mnrlsp_nm",
        "rdnmadr",
        "lnmadr",
        "latitude",
        "hardness",
        "appn_date",
        "day_use_cn",
        "qltwtr_inspct_date",
        "qltwtr_inspct_result_type",
        "impropt_iem",
        "phone_number",
        "institution_nm",
        "reference_date",
        "instt_code",
        "instt_nm"
})
public class Water {

    @JsonProperty("mnrlsp_nm")
    private String mnrlspNm;
    @JsonProperty("rdnmadr")
    private String rdnmadr;
    @JsonProperty("lnmadr")
    private String lnmadr;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("hardness")
    private String hardness;
    @JsonProperty("appn_date")
    private String appnDate;
    @JsonProperty("day_use_cn")
    private String dayUseCn;
    @JsonProperty("qltwtr_inspct_date")
    private String qltwtrInspctDate;
    @JsonProperty("qltwtr_inspct_result_type")
    private String qltwtrInspctResultType;
    @JsonProperty("impropt_iem")
    private String improptIem;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("institution_nm")
    private String institutionNm;
    @JsonProperty("reference_date")
    private String referenceDate;
    @JsonProperty("instt_code")
    private String insttCode;
    @JsonProperty("instt_nm")
    private String insttNm;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mnrlsp_nm")
    public String getMnrlspNm() {
        return mnrlspNm;
    }

    @JsonProperty("mnrlsp_nm")
    public void setMnrlspNm(String mnrlspNm) {
        this.mnrlspNm = mnrlspNm;
    }

    @JsonProperty("rdnmadr")
    public String getRdnmadr() {
        return rdnmadr;
    }

    @JsonProperty("rdnmadr")
    public void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    @JsonProperty("lnmadr")
    public String getLnmadr() {
        return lnmadr;
    }

    @JsonProperty("lnmadr")
    public void setLnmadr(String lnmadr) {
        this.lnmadr = lnmadr;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("hardness")
    public String getHardness() {
        return hardness;
    }

    @JsonProperty("hardness")
    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

    @JsonProperty("appn_date")
    public String getAppnDate() {
        return appnDate;
    }

    @JsonProperty("appn_date")
    public void setAppnDate(String appnDate) {
        this.appnDate = appnDate;
    }

    @JsonProperty("day_use_cn")
    public String getDayUseCn() {
        return dayUseCn;
    }

    @JsonProperty("day_use_cn")
    public void setDayUseCn(String dayUseCn) {
        this.dayUseCn = dayUseCn;
    }

    @JsonProperty("qltwtr_inspct_date")
    public String getQltwtrInspctDate() {
        return qltwtrInspctDate;
    }

    @JsonProperty("qltwtr_inspct_date")
    public void setQltwtrInspctDate(String qltwtrInspctDate) {
        this.qltwtrInspctDate = qltwtrInspctDate;
    }

    @JsonProperty("qltwtr_inspct_result_type")
    public String getQltwtrInspctResultType() {
        return qltwtrInspctResultType;
    }

    @JsonProperty("qltwtr_inspct_result_type")
    public void setQltwtrInspctResultType(String qltwtrInspctResultType) {
        this.qltwtrInspctResultType = qltwtrInspctResultType;
    }

    @JsonProperty("impropt_iem")
    public String getImproptIem() {
        return improptIem;
    }

    @JsonProperty("impropt_iem")
    public void setImproptIem(String improptIem) {
        this.improptIem = improptIem;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("institution_nm")
    public String getInstitutionNm() {
        return institutionNm;
    }

    @JsonProperty("institution_nm")
    public void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    @JsonProperty("reference_date")
    public String getReferenceDate() {
        return referenceDate;
    }

    @JsonProperty("reference_date")
    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    @JsonProperty("instt_code")
    public String getInsttCode() {
        return insttCode;
    }

    @JsonProperty("instt_code")
    public void setInsttCode(String insttCode) {
        this.insttCode = insttCode;
    }

    @JsonProperty("instt_nm")
    public String getInsttNm() {
        return insttNm;
    }

    @JsonProperty("instt_nm")
    public void setInsttNm(String insttNm) {
        this.insttNm = insttNm;
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
        return "Water{" +
                "mnrlspNm='" + mnrlspNm + '\'' +
                ", rdnmadr='" + rdnmadr + '\'' +
                ", lnmadr='" + lnmadr + '\'' +
                ", latitude='" + latitude + '\'' +
                ", hardness='" + hardness + '\'' +
                ", appnDate='" + appnDate + '\'' +
                ", dayUseCn='" + dayUseCn + '\'' +
                ", qltwtrInspctDate='" + qltwtrInspctDate + '\'' +
                ", qltwtrInspctResultType='" + qltwtrInspctResultType + '\'' +
                ", improptIem='" + improptIem + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", institutionNm='" + institutionNm + '\'' +
                ", referenceDate='" + referenceDate + '\'' +
                ", insttCode='" + insttCode + '\'' +
                ", insttNm='" + insttNm + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
