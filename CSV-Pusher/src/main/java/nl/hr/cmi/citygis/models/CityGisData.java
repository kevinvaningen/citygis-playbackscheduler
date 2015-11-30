package nl.hr.cmi.citygis.models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by youritjang on 09-11-15.
 */
public abstract class CityGisData implements iCityGisModel{
//    transient Gson gson = new GsonBuilder().create();

    transient GsonBuilder gsonBuilder = new GsonBuilder();
    transient  Gson gson = gsonBuilder.create();


    public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            return new JsonPrimitive(date.getTime());
        }
    }

    public String toJSON(){
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gson.toJson(this);
    }


}
