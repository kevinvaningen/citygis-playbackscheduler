package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 * Created by youritjang on 11-11-15.
 */
public class PlaybackScheduler {
    LocalDateTime schedulerTime;
    LocalDateTime fileStartTime;

    long timeToNextMessage = -1;
    boolean playeable = true;

    Publishable messageBroker;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static Logger LOGGER = LoggerFactory.getLogger(PlaybackScheduler.class);


    public PlaybackScheduler(LocalDateTime fileStartTime, Publishable messageBroker) {
        this.messageBroker = messageBroker;
        this.schedulerTime = LocalDateTime.now();
        this.fileStartTime = fileStartTime;
    }


    protected void sendOrWait(CityGisData entry) {
        long waitTime = getWaitTimeForEntry(entry);

        if ( waitTime > 0) {
            try {
                LOGGER.debug(String.format("Waiting for %d seconds", timeToNextMessage));
                Thread.sleep(Math.max(1, timeToNextMessage * 1000));
            }catch (InterruptedException ie){
                System.err.println(ie);
            }
        }
        LOGGER.debug(entry.toJSON());
        messageBroker.publish("events",entry.toJSON());
    }

    private long getWaitTimeForEntry(CityGisData entry) {
        LocalDateTime now   = LocalDateTime.now();
        long realTimePast   = schedulerTime.until(now, ChronoUnit.SECONDS);

        long fileTimePast   = fileStartTime.until(entry.getDateTime(), ChronoUnit.SECONDS);
        long timeDifference = fileTimePast - realTimePast;

        return timeDifference;
    }


    public void stopPlayback(){
        this.playeable = false;
    }


    public long getTimeToNextMessage() {
        return this.timeToNextMessage;
    }


    public void startPlayback(Stream<CityGisData> data) {
        this.playeable = true; //TODO Is playable really needed?

        data.forEach(entry -> {
            while(playeable) {
                sendOrWait(entry);
            }
        });
    }


    public void startPlayback(Observable<CityGisData> data) {
        this.playeable = true; //TODO Is playable really needed?
        data.subscribe(new CityGisDataSubscriber<>(schedulerTime, messageBroker));
    }

}
