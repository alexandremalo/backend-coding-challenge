package coveo.backend.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeightingConfig {
    @Value("${weight.percentage.name:1}")
    private int name;
    @Value("${weight.percentage.population:1}")
    private int population;
    @Value("${weight.percentage.distance:1}")
    private int distance;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
