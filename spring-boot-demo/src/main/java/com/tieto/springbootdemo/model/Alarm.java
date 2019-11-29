package com.tieto.springbootdemo.model;

import com.tieto.springbootdemo.entity.enums.AlarmStatus;
import com.tieto.springbootdemo.entity.enums.Severity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Alarm entity")
public class Alarm {
    private Long id;
    private String ts;
    private String name;
    private String module;
    private Integer instanceId;
    private String auid;
    private String json;
    @ApiModelProperty
    private AlarmStatus status;
    @ApiModelProperty
    private Severity severity = Severity.INFO;

    public Alarm(Long id, String ts, String name, String module, Integer instanceId, String auid,
                 String json, AlarmStatus status, Severity severity) {
        this.id = id;
        this.ts = ts;
        this.name = name;
        this.module = module;
        this.instanceId = instanceId;
        this.auid = auid;
        this.json = json;
        this.status = status;
        this.severity = severity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmStatus status) {
        status = status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", ts='" + ts + '\'' +
                ", name='" + name + '\'' +
                ", module='" + module + '\'' +
                ", instanceId=" + instanceId +
                ", auid='" + auid + '\'' +
                ", json='" + json + '\'' +
                ", status=" + status +
                ", severity=" + severity +
                '}';
    }
}
