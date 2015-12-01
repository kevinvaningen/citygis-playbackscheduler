package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import nl.hr.cmi.citygis.models.FileMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Subscriber;

import java.time.LocalDateTime;

public class CityGisDataSubscriber<E extends CityGisData> extends Subscriber<CityGisData> {

    Publishable messageBroker;
    FileMapping fileMapping;

    private final static Logger LOGGER = LoggerFactory.getLogger(CityGisDataSubscriber.class);

    CityGisDataSubscriber(Publishable messageBroker, FileMapping fileMapping) {
        this.messageBroker = messageBroker;
        this.fileMapping = fileMapping;
    }

    @Override
    public void onCompleted() {
        LOGGER.info("Finished!");
    }

    @Override
    public void onNext(CityGisData cgd) {
        LOGGER.debug("onNext: topic:" + fileMapping.name() +"--" +cgd.toString());

        messageBroker.publish(fileMapping.name(), cgd.toJSON());
    }

    @Override
    public void onError(Throwable e) {
        LOGGER.error("Error in Data Subscriber: " + e);
    }
}
