package nl.hr.cmi.citygis.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by youritjang on 09-11-15.
 */

public interface iCityGisModel {
    CityGisData create(List<String> data);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime date);

}
