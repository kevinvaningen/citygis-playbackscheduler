package nl.hr.cmi.citygis.models;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by youritjang on 09-11-15.
 */
public abstract class CityGisData implements iCityGisModel{
    transient Gson gson = new GsonBuilder().create();
    public String toJSON(){
        return gson.toJson(this);
    }
}
