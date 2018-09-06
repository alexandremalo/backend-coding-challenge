package coveo.backend.challenge.util;

import coveo.backend.challenge.model.CityInfo;

public class CityInfoBuilder {
    private long id;
    private String name;
    private String altName;
    private String stateOrProvince;
    private String country;
    private double longitude;
    private double latitude;
    private int population;

    public CityInfoBuilder id(long id){
        this.id = id;
        return this;
    }

    public CityInfoBuilder name(String name){
        this.name = name;
        return this;
    }

    public CityInfoBuilder altName(String altName){
        this.altName = altName;
        return this;
    }

    public CityInfoBuilder stateOrProvince(String stateOrProvince){
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public CityInfoBuilder country(String country){
        this.country = country;
        return this;
    }

    public CityInfoBuilder longitude(double longitude){
        this.longitude = longitude;
        return this;
    }

    public CityInfoBuilder latitude(double latitude){
        this.latitude = latitude;
        return this;
    }

    public CityInfoBuilder population(int population){
        this.population = population;
        return this;
    }

    public CityInfo createCityInfo(){
        return new CityInfo(id, name, altName, country, stateOrProvince, longitude, latitude, population);
    }
}
