package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import rx.Observable;

import java.time.LocalDateTime;

/**
 * Created by youritjang on 11-11-15.
 */
public class ObservablePlaybackScheduler extends PlaybackScheduler{

    public ObservablePlaybackScheduler(LocalDateTime schedulerTime, Brokereable messageBroker) {
        super(schedulerTime, messageBroker);
    }

    public void startPlayback(Observable<CityGisData> data) {
        this.playeable = true; //TODO Is playable really needed?

        data.subscribe(new CityGisDataSubscriber<CityGisData>(schedulerTime, messageBroker));
//        data.forEach(entry -> {
//            while(playeable) {
//                sendOrWait(entry);
//            }
//        });
    }
}
