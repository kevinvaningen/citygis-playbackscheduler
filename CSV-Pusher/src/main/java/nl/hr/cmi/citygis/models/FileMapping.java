package nl.hr.cmi.citygis.models;

import java.util.function.Supplier;


public enum FileMapping {
    EVENTS("Events.csv", () -> new Event()),
    MONITORING("Monitoring.csv",() -> new Monitoring()),
    POSITIONS("Positions.csv", () -> new Position()),
    CONNECTIONS("Connections.csv", () -> new Connection());

    private final String name;
    private final Supplier<iCityGisModel> supplier;

    private FileMapping(String name, Supplier<iCityGisModel> supplier){
        this.name = name;
        this.supplier = supplier;
    }
    public String getFileName(){
        return name;
    }

    public Supplier<iCityGisModel> getSupplier(){
        return supplier;
    }
}


