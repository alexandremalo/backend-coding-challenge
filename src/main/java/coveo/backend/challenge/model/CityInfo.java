package coveo.backend.challenge.model;

import java.math.BigDecimal;

public class CityInfo {
    private long id;
    private String name;
    private String country;
    private String stateOrProvince;
    private double longitude;
    private double latitude;
    private long population;
    private double score;

    public CityInfo(long id, String name, String country, String stateOrProvince, double longitude, double latitude, long population) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.stateOrProvince = stateOrProvince;
        this.longitude = longitude;
        this.latitude = latitude;
        this.population = population;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
