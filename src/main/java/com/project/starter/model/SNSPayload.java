/**
 * This is a lab for Unison Team
 * for new developers.
 * Author: Diego Gutierrez
 * 2021
 **/

package com.project.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SNSPayload {
    @JsonProperty("Type")
    private String type;

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("Message")
    private String message;
}
