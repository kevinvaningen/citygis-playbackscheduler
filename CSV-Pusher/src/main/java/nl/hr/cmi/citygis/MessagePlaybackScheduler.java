package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by cmi on 09-11-15.
 */
public class MessagePlaybackScheduler {
    LocalDateTime schedulerTime;

    long timeToNextMessage = -1;

    boolean playeable = true;


    public  MessagePlaybackScheduler(){
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Created scheduler using System time now:" + start.toString());
    }

    public  MessagePlaybackScheduler(LocalDateTime time){
        schedulerTime = time;
        System.out.println("Created scheduler using inputted time:" + schedulerTime.toString());
    }

    public void startPlayback(Stream<CityGisData> data, Publishable messageBroker) {
        this.playeable = true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime virtualStart = LocalDateTime.parse("2015-03-10 07:12:25", formatter);//data.findFirst().get().getDateTime();

        data.forEach(entry -> {
            boolean sent = false;
            while(!sent && playeable) {
                LocalDateTime now = LocalDateTime.now();
                long virtualTimeDifference = virtualStart.until(entry.getDateTime(), ChronoUnit.SECONDS);
                long realTimeDifference = schedulerTime.until(now, ChronoUnit.SECONDS);
                long timeDifference = virtualTimeDifference - realTimeDifference;

                timeToNextMessage = timeDifference;

                if (timeDifference <= 0) {
                    System.out.println(entry.toJSON());

                    messageBroker.publish("events",entry.toJSON());
                    sent = true;
                } else {
                    try {
                        Thread.sleep(timeToNextMessage * 1000);
                    }catch (InterruptedException ie){
                        System.err.println(ie);
                    }
                }
            }
        });
    }

    public void stopPlayback(){
        this.playeable = false;
    }


    public long getTimeToNextMessage() {
        return this.timeToNextMessage;
    }
}
