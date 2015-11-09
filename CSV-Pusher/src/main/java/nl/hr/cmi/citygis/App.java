package nl.hr.cmi.citygis;

import nl.hr.cmi.citygis.models.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * CityGis CSV pusher
 *
 */
public class App 
{
    public static void main(String[] args) {
        App a = new App();
        LinkedHashMap<LocalDateTime, List<CityGisModel>> data = CsvConverter.getGroupedModelsFromFile("","Events.csv", () -> new Event());
        a.runMessages(data);
    }

    public void runMessages(LinkedHashMap<LocalDateTime, List<CityGisModel>> data){
//        BrokerConnector connection = new BrokerConnector();
//        connection.connect();

        LocalDateTime start = LocalDateTime.now();

        LocalDateTime virtualStart = data.keySet().iterator().next();

        for (Map.Entry<LocalDateTime, List<CityGisModel>> entry : data.entrySet()) {


            boolean sent = false;
            while(!sent) {
                LocalDateTime now = LocalDateTime.now();
                long virtualTimeDifference = virtualStart.until(entry.getKey(), ChronoUnit.SECONDS);
                long realTimeDifference = start.until(now, ChronoUnit.SECONDS);
                long timeDifference = virtualTimeDifference - realTimeDifference;

                System.out.println(virtualTimeDifference + "-" + realTimeDifference +"="+timeDifference);

                if (timeDifference <= 0) {
                    for (CityGisModel cgm : entry.getValue()) {
//                        connection.publishConnectAndTransfer(cgm.toString());
                        System.out.println(cgm.toJSON());
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
//        connection.disconnectFromBroker();
//        System.exit(0);
    }

    public void runMessages(String... messages){
        BrokerConnector connection = new BrokerConnector();
        connection.connect();
        for (int i = 0; i < messages.length; i++) {
            connection.publishConnectAndTransfer(messages[i]);
        }
        connection.disconnectFromBroker();
        System.exit(0);
    }
}
