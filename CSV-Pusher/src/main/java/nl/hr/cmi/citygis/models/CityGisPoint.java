package nl.hr.cmi.citygis.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class CityGisPoint extends CityGisData {
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private LocalDateTime dateTime;
    private Long unitId;


    public CityGisPoint create(LocalDateTime dateTime, Long unitId) {
        this.dateTime = dateTime;
        this.unitId = unitId;
        return this;
    }

    public CityGisPoint create(String dateTime, String unitId) {
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
        if(dateTime == null || dateTime.trim().equals("")){
            throw new IllegalArgumentException("The argument :" + dateTime +" does not fit format:"+dateTimeFormat);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
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
}
