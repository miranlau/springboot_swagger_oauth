package com.tieto.springbootdemo.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import org.springframework.http.MediaType;

@ApiModel(description = "Response wrapper model")
public class Response {
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mediaType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object entity;

    private Response(){}

    public static Response newInstance() {
        return new Response();
    }

    public Response status(int status) {
        setStatus(status);
        return this;
    }

    public Response type(String mediaType) {
        setMediaType(mediaType);
        return this;
    }

    public Response entity(Object entity) {
        setEntity(entity);
        return this;
    }

    public Response build() {
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
