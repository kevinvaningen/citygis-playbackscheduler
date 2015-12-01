package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Subscriber;

/***
 * Java RX subscriber for
 *
 * @param <E>
 */
public class CityGisDataSubscriber<E extends CityGisData> extends Subscriber<CityGisData> {

    private Publishable messageBroker;
    private String messageQueuetopic;

    private final static Logger LOGGER = LoggerFactory.getLogger(CityGisDataSubscriber.class);

    /***
     * Java RX Subscriber instantiation.
     *
     * @param messageBroker     receives an instance a type Publishable interface. The implementation should be aware of it's own ability to make a (preconfigured) connection and the connection state.
     * @param messageQueuetopic provides a referencename of a queue or topic where observed messages can be send to.
     */
    public CityGisDataSubscriber(Publishable messageBroker, String messageQueuetopic) {
        this.messageBroker = messageBroker;
        this.messageQueuetopic = messageQueuetopic;
    }

    /***
     * Will be called when the scheduler reaches the end of the input-file. So recordings are not repeated.
     */
    @Override
    public void onCompleted() {
        LOGGER.info("Finished reading the data-file!");
        //BUG
        //System.exit(0);
    }

    /***
     * onNext is called for each observed instance and is the main processing step. It publishes the data-frame.
     *
     * @param cityGisDataFrame accepts any CityGisData frame
     */
    @Override
    public void onNext(CityGisData cityGisDataFrame) {
        LOGGER.debug("onNextObservable: topic/queue is:" + messageQueuetopic + "--" + cityGisDataFrame.toString());
        messageBroker.publish(messageQueuetopic, cityGisDataFrame.toJSON());
    }

    /***
     * When an error occurs, the software the observing (iterating) is not stopped.
     * @param e the actual error that occured during watching. Difficult to specify, likely a publication error.
     */
    @Override
    public void onError(Throwable e) {
        LOGGER.error("Error publishing or iterating the data in " + CityGisDataSubscriber.class.getSimpleName() + ":" + e + " on messageQueuetopic" + messageQueuetopic);
    }
}
