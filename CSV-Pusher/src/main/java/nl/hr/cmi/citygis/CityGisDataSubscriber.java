package nl.hr.cmi.citygis;

import rx.Subscriber;
import nl.hr.cmi.citygis.models.CityGisData;
import java.time.LocalDateTime;

/**
 * Created by youritjang on 11-11-15.
 */
public class CityGisDataSubscriber<E extends CityGisData> extends Subscriber<CityGisData> {
    PlaybackScheduler mps;

    CityGisDataSubscriber(LocalDateTime schedulerTime, Publishable messageBroker){
        mps = new PlaybackScheduler(schedulerTime, messageBroker);
    }

    @Override
    public void onCompleted(){
        System.out.println("Finished!");
    }

    @Override
    public void onNext(CityGisData cgd){
         mps.sendOrWait(cgd);
    }

    @Override
    public void onError(Throwable e){
        System.err.println("Error: " + e);
    }
}
