package nl.hr.cmi.citygis;

import com.google.gson.Gson;

/**
 * Created by cmi on 09-11-15.
 */
public class JsonPosition extends Position{
    Gson jsonBuilder = new Gson();

    public String toJSON(){
            return jsonBuilder.toJson(this);
    }
}
