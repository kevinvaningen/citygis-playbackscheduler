package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.subjects.PublishSubject;

import java.time.LocalDateTime;
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

    FileMapping fileMapping;

    Publishable messageBroker;
    private final static Logger LOGGER = LoggerFactory.getLogger(PlaybackScheduler.class);

    public PlaybackScheduler(LocalDateTime fileStartTime, Publishable messageBroker, FileMapping fileMapping) {
        this.messageBroker = messageBroker;
        this.schedulerTime = LocalDateTime.now();
        this.fileStartTime = fileStartTime;

        this.fileMapping = fileMapping;

        LOGGER.debug(String.format("PlaybackScheduler instantiated with: filestartTime: %s, schedulerTime: %s", fileStartTime.toString(), schedulerTime.toString()));
    }

    protected void sendOrWait(CityGisData entry) {
        long waitTime = getWaitTimeForEntry(entry);
        LOGGER.debug("Waittime: "+waitTime);

        if ( waitTime > 0) {
            try {
                LOGGER.debug(String.format("Waiting for %d seconds", timeToNextMessage));
                Thread.sleep(Math.max(1, timeToNextMessage * 1000));
            }catch (InterruptedException ie){
                LOGGER.error(ie.getMessage());
            }
        }
        messageBroker.publish(fileMapping.name(), entry.toJSON());
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

        CityGisDataSubscriber cs = new CityGisDataSubscriber(messageBroker, fileMapping);
        PublishSubject<CityGisData> subject = PublishSubject.create();
        subject.subscribe(cs);
        data.forEach(cityGisData1 -> {
            long waitTime = getWaitTimeForEntry(cityGisData1);
                try {
                    LOGGER.debug("Waiting: " + waitTime);
                    Thread.sleep(Math.max(1, waitTime * 1000));
                } catch (InterruptedException ie) {
                    LOGGER.error(ie.getMessage());
                }
                subject.onNext(cityGisData1);
        });
    }
}
