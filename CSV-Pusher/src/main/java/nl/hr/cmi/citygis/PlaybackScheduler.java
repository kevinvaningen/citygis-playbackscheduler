package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.CityGisData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/***
 * Playback schedular is the core component that mimics the playback of data from the file using a scheduling algorithm.
 * Because files can get realy big, the input uses a Stream.
 */
public class PlaybackScheduler {
    private LocalDateTime schedulerTime;
    private LocalDateTime fileStartTime;

    private String messageQueuetopic;

    private Publishable messageBroker;
    private final static Logger LOGGER = LoggerFactory.getLogger(PlaybackScheduler.class);

    /***
     * Playback scheduler using RX Java implementation. Instantiated for each filetype.
     *
     * @param fileStartTime     this is used for playback purposes. The scheduler fast-forwards to the specified start time. This takes some seconds for >1Gb files.
     * @param messageBroker     a preconfigured message broker, ready to publish on.
     * @param messageQueuetopic provides a referencename of a queue or topic where observed messages can be send to.
     */
    public PlaybackScheduler(LocalDateTime fileStartTime, Publishable messageBroker, String messageQueuetopic) {
        this.messageBroker = messageBroker;
        this.schedulerTime = LocalDateTime.now();
        this.fileStartTime = fileStartTime;

        this.messageQueuetopic = messageQueuetopic;

        LOGGER.debug(String.format("PlaybackScheduler instantiated with: filestartTime: %s, schedulerTime: %s", fileStartTime.toString(), schedulerTime.toString()));
    }

    /***
     * Starts playback providing the proper instantiation of the configuration.
     * Playback uses the current time for playback purposes.
     * The weekday is used to select the proper time frame.
     * Playback mimics the data flow from the source file as it occurs now.
     *
     * @param cityGisDataFrame provide a Stream of occurrences.
     */
    public void startPlayback(Stream<CityGisData> cityGisDataFrame) {
        CityGisDataSubscriber cs = new CityGisDataSubscriber(messageBroker, messageQueuetopic);
        PublishSubject<CityGisData> subject = PublishSubject.create();
        subject.subscribe(cs);

        Observable.from(cityGisDataFrame::iterator)
                .skipWhile(cityGisData -> getWaitTimeForEntrySameDayandTime(cityGisData) >= 0)
                .forEach(cityGisData1 -> {
                    long waitTime = getWaitTimeForEntrySameDayandTime(cityGisData1);
                    if (waitTime >= 0) {
                        try {
                            LOGGER.debug(String.format("Waiting: %f minutes. entry time: %s", waitTime / 60.0, cityGisData1.getDateTime().toString()));
                            Thread.sleep(Math.max(1, waitTime * 1000));
                        } catch (InterruptedException ie) {
                            LOGGER.error(ie.getMessage());
                        }
                        subject.onNext(cityGisData1);
                    }
                });
    }

    //    /protected void sendOrWait(CityGisData entry) {
//        long waitTime = getWaitTimeForEntry(entry);
//        LOGGER.debug("Waittime: "+waitTime);
//
//        if ( waitTime > 0) {
//            try {
//                LOGGER.debug(String.format("Waiting for %d seconds", timeToNextMessage));
//                Thread.sleep(Math.max(1, timeToNextMessage * 1000));
//            }catch (InterruptedException ie){
//                LOGGER.error(ie.getMessage());
//            }
//        }
//        messageBroker.publish(messageQueuetopic, entry.toJSON());
//    }

    private long getWaitTimeForEntry(CityGisData entry) {
        LocalDateTime now = LocalDateTime.now();
        long realTimePast = schedulerTime.until(now, ChronoUnit.SECONDS);
        long fileTimePast = fileStartTime.until(entry.getDateTime(), ChronoUnit.SECONDS);
        return fileTimePast - realTimePast;
    }

    private long getWaitTimeForEntrySameDay(CityGisData entry) {
        if (entry.getDateTime().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())) {
            LocalTime now = LocalTime.now();
            LocalTime entryTime = entry.getDateTime().toLocalTime();

            return now.until(entryTime, ChronoUnit.SECONDS);
        } else {
            return -1L;
        }
    }

    private long getWaitTimeForEntrySameDayandTime(CityGisData entry) {
        boolean sameDay = entry.getDateTime().getDayOfWeek().equals(LocalDate.now().getDayOfWeek());

        if (sameDay) {
            LocalTime now = LocalTime.now();
            LocalTime entryTime = entry.getDateTime().toLocalTime();

            return now.until(entryTime, ChronoUnit.SECONDS);
        } else {
            return -1L;
        }
    }
}
