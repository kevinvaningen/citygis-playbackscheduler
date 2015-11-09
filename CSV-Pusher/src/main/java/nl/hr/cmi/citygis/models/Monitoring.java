package nl.hr.cmi.citygis.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by youritjang on 09-11-15.
 */
public class Monitoring extends CityGisData implements iCityGisModel{

    private String unitId;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String type;
    private double min;
    private double max;
    private double sum;

    public Monitoring create(List<String> data){
        return this.create(
                data.get(0),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                data.get(5),
                data.get(6)
        );
    }

    public Monitoring create(String unitId, String beginTime, String endTime, String type, String min, String max, String sum) {
        this.setUnitId(unitId);
        this.setBeginTime(beginTime);
        this.setEndTime(endTime);
        this.setType(type);
        this.setMin(min);
        this.setMax(max);
        this.setSum(sum);

        return this;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public LocalDateTime getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setBeginTime(String beginTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.beginTime = LocalDateTime.parse(beginTime, formatter);
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public LocalDateTime getDateTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.endTime = LocalDateTime.parse(endTime, formatter);

    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMin(String min) {
        this.min = Double.parseDouble(min);
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMax(String max) {
        this.max = Double.parseDouble(max);
    }

    public double getSum() {
        return this.sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setSum(String sum) {
        this.sum = Double.parseDouble(sum);
    }
}
