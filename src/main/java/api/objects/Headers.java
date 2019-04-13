package api.objects;

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
        "Accept",
        "Accept-Encoding",
        "Cache-Control",
        "Host",
        "Postman-Token",
        "User-Agent"
})
public class Headers {

    @JsonProperty("Accept")
    private String accept;
    @JsonProperty("Accept-Encoding")
    private String acceptEncoding;
    @JsonProperty("Cache-Control")
    private String cacheControl;
    @JsonProperty("Host")
    private String host;
    @JsonProperty("Postman-Token")
    private String postmanToken;
    @JsonProperty("User-Agent")
    private String userAgent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Accept")
    public String getAccept() {
        return accept;
    }

    @JsonProperty("Accept")
    public void setAccept(String accept) {
        this.accept = accept;
    }

    @JsonProperty("Accept-Encoding")
    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    @JsonProperty("Accept-Encoding")
    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    @JsonProperty("Cache-Control")
    public String getCacheControl() {
        return cacheControl;
    }

    @JsonProperty("Cache-Control")
    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    @JsonProperty("Host")
    public String getHost() {
        return host;
    }

    @JsonProperty("Host")
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty("Postman-Token")
    public String getPostmanToken() {
        return postmanToken;
    }

    @JsonProperty("Postman-Token")
    public void setPostmanToken(String postmanToken) {
        this.postmanToken = postmanToken;
    }

    @JsonProperty("User-Agent")
    public String getUserAgent() {
        return userAgent;
    }

    @JsonProperty("User-Agent")
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
