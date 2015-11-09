package nl.hr.cmi.citygis;

import java.util.Date;

/**
 * Created by cmi on 07-11-15.
 */
public class Position {

    private Date dateTime;
    private String unitId;
    private String rDx;
    private String rDy;
    private double speed;
    private double course;
    private int numSattellites;
    private int hdop;
    private GpsQuality quality;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getrDx() {
        return rDx;
    }

    public void setrDx(String rDx) {
        this.rDx = rDx;
    }

    public String getrDy() {
        return rDy;
    }

    public void setrDy(String rDy) {
        this.rDy = rDy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public int getNumSattellites() {
        return numSattellites;
    }

    public void setNumSattellites(int numSattellites) {
        this.numSattellites = numSattellites;
    }

    public int getHdop() {
        return hdop;
    }

    public void setHdop(int hdop) {
        this.hdop = hdop;
    }

    public GpsQuality getQuality() {
        return quality;
    }

    public void setQuality(GpsQuality quality) {
        this.quality = quality;
    }

    public enum GpsQuality{
        GPS,DGPS
    }

}
