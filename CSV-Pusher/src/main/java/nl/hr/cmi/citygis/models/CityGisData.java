package nl.hr.cmi.citygis.models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;


public abstract class CityGisData implements iCityGisModel{

    transient GsonBuilder gsonBuilder = new GsonBuilder();
    transient  Gson gson;


    public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDateTime.toString());
        }
    }

    public String toJSON(){
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gson = gsonBuilder.create();
        return gson.toJson(this);
    }


}
