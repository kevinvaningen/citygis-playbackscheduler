package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Subscriber;

import java.time.LocalDateTime;

/**
 * Created by youritjang on 11-11-15.
 */
public class CityGisDataSubscriber<E extends CityGisData> extends Subscriber<CityGisData> {

    PlaybackScheduler mps;
    private final static Logger LOGGER = LoggerFactory.getLogger(CityGisDataSubscriber.class);

    CityGisDataSubscriber(LocalDateTime schedulerTime, Publishable messageBroker) {
        mps = new PlaybackScheduler(schedulerTime, messageBroker);
    }

    @Override
    public void onCompleted() {
        LOGGER.info("Finished!");
    }

    @Override
    public void onNext(CityGisData cgd) {
        LOGGER.debug("onNext: " + cgd.toString());
        mps.sendOrWait(cgd);
    }

    @Override
    public void onError(Throwable e) {
        LOGGER.error("Error in Data Subscriber: " + e);
    }
}
