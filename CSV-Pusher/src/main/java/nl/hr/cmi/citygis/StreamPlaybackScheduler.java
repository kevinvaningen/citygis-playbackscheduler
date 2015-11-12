package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class StreamPlaybackScheduler extends PlaybackScheduler{

//    public StreamPlaybackScheduler(LocalDateTime time) {
//        super(time);
//    }

    public StreamPlaybackScheduler(LocalDateTime schedulerTime, Brokereable messageBroker) {
        super(schedulerTime, messageBroker);
    }

    public void startPlayback(Stream<CityGisData> data) {
        this.playeable = true; //TODO Is playable really needed?

        data.forEach(entry -> {
            while(playeable) {
                sendOrWait(entry);
            }
        });
    }
}
