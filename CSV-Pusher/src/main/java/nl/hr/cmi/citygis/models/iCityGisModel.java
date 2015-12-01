package nl.hr.cmi.citygis.models;

import java.time.LocalDateTime;
import java.util.List;

public interface iCityGisModel {
    CityGisData create(List<String> data);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime date);
}
