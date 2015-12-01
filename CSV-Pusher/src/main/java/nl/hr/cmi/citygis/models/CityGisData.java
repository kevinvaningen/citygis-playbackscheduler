package nl.hr.cmi.citygis.models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;


public abstract class CityGisData implements iCityGisModel{

    private transient GsonBuilder gsonBuilder = new GsonBuilder();
    private transient Gson gson;


    public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDateTime.toString());
        }
    }

    /***
     * Uses GSON to serialise the data object to JSON.
     *
     * @return a JSON version of the String.
     */
    public String toJSON(){
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gson = gsonBuilder.create();
        return gson.toJson(this);
    }


}
