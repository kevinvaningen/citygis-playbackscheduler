package nl.hr.cmi.citygis;

import rx.Subscriber;
import nl.hr.cmi.citygis.models.CityGisData;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Created by youritjang on 11-11-15.
 */
public class CityGisDataSubscriber<E extends CityGisData> extends Subscriber<CityGisData> {
    PlaybackScheduler mps;
    private final static Logger LOGGER = Logger.getLogger(CityGisDataSubscriber.class.getName());

    CityGisDataSubscriber(LocalDateTime schedulerTime, Publishable messageBroker){
        mps = new PlaybackScheduler(schedulerTime, messageBroker);
    }

    @Override
    public void onCompleted(){
        System.out.println("Finished!");
    }

    @Override
    public void onNext(CityGisData cgd){
        LOGGER.finer(cgd.toString());
        mps.sendOrWait(cgd);
    }

    @Override
    public void onError(Throwable e){
        LOGGER.severe("Error: " + e);
    }
}
