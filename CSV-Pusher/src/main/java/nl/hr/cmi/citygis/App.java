package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.FileMapping;

import java.time.LocalDateTime;

/**
 * CityGis CSV pusher
 *
 */
public class App {
    BrokereableConnector connection;
    MessagePlaybackScheduler scheduler;
    MessageFileRetriever mr;

    public App(){
        System.out.println("Started" + App.class.getSimpleName()+ " started. ");
        connection = new BrokereableConnector();
        scheduler = new MessagePlaybackScheduler(LocalDateTime.now());
        mr = new MessageFileRetriever();

        System.out.println("Starting message schedular.");

        scheduler.startPlayback(mr.getDataFromCSV(FileMapping.CONNECTIONS),connection);
    }

    public static void main(String[] args){
        App a = new App();
    }

}