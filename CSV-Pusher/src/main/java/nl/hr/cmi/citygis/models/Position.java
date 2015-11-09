package nl.hr.cmi.citygis.models;

import java.util.List;

/**
 * Created by cmi on 07-11-15.
 */
public class Position extends CityGisPoint implements iCityGisModel{

    private String rDx;
    private String rDy;
    private double speed;
    private double course;
    private int numSattellites;
    private int hdop;
    private String quality;


    public Position create(List<String> data){
        return create(
                data.get(0),
                data.get(1),
                data.get(2),
                data.get(3),
                data.get(4),
                data.get(5),
                data.get(6),
                data.get(7),
                data.get(8)
        );
    }

    public Position create(String dateTime, String unitId, String rDx, String rDy, String speed, String course, String numSattellites, String hdop, String quality) {
        super.create(dateTime, unitId);
        this.setrDx(rDx);
        this.setrDy(rDy);
        this.setSpeed(speed);
        this.setCourse(course);
        this.setNumSattellites(numSattellites);
        this.setHdop(hdop);
        this.setQuality(quality);
        return this;
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

    public void setSpeed(String speed) {
        this.speed = Double.parseDouble(speed);
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public void setCourse(String course) {
        this.course = Double.parseDouble(course);
    }

    public int getNumSattellites() {
        return numSattellites;
    }

    public void setNumSattellites(int numSattellites) {
        this.numSattellites = numSattellites;
    }

    public void setNumSattellites(String numSattellites) {
        this.numSattellites = Integer.parseInt(numSattellites);
    }

    public int getHdop() {
        return hdop;
    }

    public void setHdop(int hdop) {
        this.hdop = hdop;
    }

    public void setHdop(String hdop) {
        this.hdop = Integer.parseInt(hdop);
    }

    public String getQuality() {
        return this.quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Deprecated
    public enum GpsQuality{
        Gps("Gps"),
        dGps("dGps"),
        Fix3D("Fix3D"),
        Fix3DDGPS("Fix3D+DGPS"),
        DrOnly("DrOnly"),
        GpsAndDr("GpsAndDr"),
        GpsAndDrDGPS("GpsAndDr+DGPS"),
        Fix2D("Fix2D"),
        Fix2DDGPS("Fix2D+DGPS"),
        DrOnlyDGPS("DrOnly+DGPS"),
        Dr("Dr");

        private final String name;

        private GpsQuality(String s) {
            name = s;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Position{");
        sb.append("").append(super.toString());
        sb.append("rDx='").append(rDx).append('\'');
        sb.append(", rDy='").append(rDy).append('\'');
        sb.append(", speed=").append(speed);
        sb.append(", course=").append(course);
        sb.append(", numSattellites=").append(numSattellites);
        sb.append(", hdop=").append(hdop);
        sb.append(", quality='").append(quality).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
