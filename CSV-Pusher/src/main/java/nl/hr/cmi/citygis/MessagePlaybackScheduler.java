package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisModel;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public void startPlayback(LinkedHashMap<LocalDateTime, List<CityGisModel>> data,Brokereable messageBroker){
        this.playeable = true;


        LocalDateTime virtualStart = data.keySet().iterator().next();

        for (Map.Entry<LocalDateTime, List<CityGisModel>> entry : data.entrySet()) {
            boolean sent = false;
            while(!sent && playeable) {
                LocalDateTime now = LocalDateTime.now();
                long virtualTimeDifference = virtualStart.until(entry.getKey(), ChronoUnit.SECONDS);
                long realTimeDifference = schedulerTime.until(now, ChronoUnit.SECONDS);
                long timeDifference = virtualTimeDifference - realTimeDifference;

                timeToNextMessage = timeDifference;

                System.out.println(virtualTimeDifference + "-" + realTimeDifference +"="+timeDifference);

                if (timeDifference <= 0) {
                    for (CityGisModel cgm : entry.getValue()) {
                        System.out.println(cgm.toJSON());
                        messageBroker.publish("events",cgm.toJSON());
                    }
                    sent = true;
                } else {
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException ie){
                        System.err.println(ie);
                    }
                }
            }
        }
    }

    public void stopPlayback(){
        this.playeable = false;
    }


    public long getTimeToNextMessage() {
        return this.timeToNextMessage;
    }
}
