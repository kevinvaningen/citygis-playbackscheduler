package nl.hr.cmi.citygis;

/**
 * CityGis CSV pusher
 *
 */
public class App 
{
    public static void main(String[] args) {
        App a = new App();
        a.runMessages("hoi");
    }

    public void runMessages(String... messages){
        BrokerConnector connection = new BrokerConnector();
        connection.connect();;
        for (int i = 0; i < messages.length; i++) {
            connection.publishConnectAndTransfer(messages[i]);
        }
        connection.disconnectFromBroker();
        System.exit(0);
    }



    @Deprecated
    private String createExampleMessage() {
        JsonPosition jsp = new JsonPosition();
        return jsp.toJSON();
    }
}
