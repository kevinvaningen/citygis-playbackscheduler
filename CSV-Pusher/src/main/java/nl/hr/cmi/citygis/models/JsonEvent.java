package nl.hr.cmi.citygis.models;

import com.google.gson.Gson;

/**
 * Created by cmi on 09-11-15.
 */
public class JsonEvent extends Event {
    Gson jsonBuilder = new Gson();

    public String toJSON(){
            return jsonBuilder.toJson(this);
    }
}
