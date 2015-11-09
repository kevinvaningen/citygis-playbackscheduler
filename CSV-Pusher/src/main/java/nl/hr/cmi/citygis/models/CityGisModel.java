package nl.hr.cmi.citygis.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public abstract class CityGisModel {
    private LocalDateTime dateTime;
    private Long unitId;
    transient Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public CityGisModel create(LocalDateTime dateTime, Long unitId) {
        this.dateTime = dateTime;
        this.unitId = unitId;
        return this;
    }

    public CityGisModel create(String dateTime, String unitId) {
        this.setDateTime(dateTime);
        this.setUnitId(unitId);
        return this;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.parse(dateTime, formatter);
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = Long.parseLong(unitId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("dateTime=").append(dateTime);
        sb.append(", unitId=").append(unitId);
        sb.append(' ');
        return sb.toString();
    }

    public String toJSON(){
        return gson.toJson(this);
    }
}
