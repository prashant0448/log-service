package com.prashant.logservice.model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prashant.logservice.model.Event;
import com.prashant.logservice.model.EventType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "c_alerts")
public class Alert {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("type")
    private EventType type;

    @JsonProperty("host")
    private String host;

    @JsonProperty("alert")
    private Boolean alert;

    public Alert() {
    }

    public Alert(Event event, int duration) {
        this.id = event.getId();
        this.type = event.getType();
        this.host = event.getHost();
        this.duration = duration;
        this.alert = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", type=" + type +
                ", host='" + host + '\'' +
                ", alert=" + alert +
                '}';
    }
}