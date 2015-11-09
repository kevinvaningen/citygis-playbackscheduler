package nl.hr.cmi.citygis.models;

import java.util.List;

/**
 * Created by youritjang on 09-11-15.
 */
public class Event extends CityGisModel implements iCityGisModel{
    public Port port;
    byte value;

    public Event create(List<String> data){
        return create(data.get(0), data.get(1), data.get(2), data.get(3));
    }

    public Event create(String dateTime, String unitId, String port, String value) {
        super.create(dateTime, unitId);
        this.setPort(port);
        this.setValue(value);

        return this;
    }

    public Port getPort() {
        return this.port;
    }

    public void setPort(Event.Port port) {
        this.port = port;
    }

    public void setPort(String port) {
        this.port = Port.valueOf(port);
    }

    public byte getValue() {
        return this.value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = Byte.parseByte(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event{");
        sb.append(super.toString());
        sb.append(", port=").append(port);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    public enum Port{
        Ignition, PowerStatus
    }
}
