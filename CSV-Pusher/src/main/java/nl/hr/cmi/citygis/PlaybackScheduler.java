package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 * Created by youritjang on 11-11-15.
 */
public abstract class PlaybackScheduler {
    LocalDateTime schedulerTime;
    LocalDateTime fileStartTime;

    long timeToNextMessage = -1;
    boolean playeable = true;

    Publishable messageBroker;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     *  @deprecated set the messagebroker
     */
    @Deprecated
    public  PlaybackScheduler(){
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Created scheduler using System time now:" + start.toString());
    }

    /**
     *  @deprecated set the messagebroker
     */
    @Deprecated
    public  PlaybackScheduler(LocalDateTime time){
        schedulerTime = time;
        fileStartTime = LocalDateTime.parse("2015-03-10 07:12:25", formatter);
        System.out.println("Created scheduler using inputted time:" + schedulerTime.toString());
    }

    public PlaybackScheduler(LocalDateTime schedulerTime, Publishable messageBroker) {
        this.schedulerTime = schedulerTime;
        this.messageBroker = messageBroker;
        fileStartTime = LocalDateTime.parse("2015-03-10 07:12:25", formatter); //// TODO: 12-11-15 up up and away
    }

    public void setMessageBroker(Publishable messageBroker) {
        this.messageBroker = messageBroker;
    }



    protected void sendOrWait(CityGisData entry) {
        long waitTime = getWaitTimeForEntry(entry);

        if ( waitTime > 0) {
            try {
                System.out.println(String.format("Waiting for %d seconds", timeToNextMessage));
                Thread.sleep(Math.max(1, timeToNextMessage * 1000));
            }catch (InterruptedException ie){
                System.err.println(ie);
            }
        }
        System.out.println(entry.toJSON());
        messageBroker.publish("events",entry.toJSON());
    }

    private long getWaitTimeForEntry(CityGisData entry) {
        LocalDateTime now = LocalDateTime.now();
        long fileTimePast = fileStartTime.until(entry.getDateTime(), ChronoUnit.SECONDS);
        long realTimePast = schedulerTime.until(now, ChronoUnit.SECONDS);
        long timeDifference = fileTimePast - realTimePast;

        return timeDifference;
    }

    @Deprecated
    private boolean sendOrWait(Publishable messageBroker, CityGisData entry, boolean sent, long timeDifference) {
        if (timeDifference <= 0) {
            System.out.println(entry.toJSON());

            messageBroker.publish("events",entry.toJSON());
            sent = true;
        } else {
            try {
                System.out.println(String.format("Waiting for %d seconds", timeToNextMessage));
                Thread.sleep(timeToNextMessage * 1000);
            }catch (InterruptedException ie){
                System.err.println(ie);
            }
        }
        return sent;
    }

    public void stopPlayback(){
        this.playeable = false;
    }


    public long getTimeToNextMessage() {
        return this.timeToNextMessage;
    }

}
