package nl.hr.cmi.citygis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CityGisModel {
    private LocalDateTime dateTime;
    private Long unitId;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData(List<String> data) {
        this.data = String.join(" ; ", data);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityGisModel{");
        sb.append("dateTime=").append(dateTime);
        sb.append(", unitId=").append(unitId);
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
