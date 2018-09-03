package coveo.backend.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CitiesDataSource {
    @Value("${cities.file}")
    private String citiesFile;

    public String getCitiesFile() {
        return citiesFile;
    }

    public void setCitiesFile(String citiesFile) {
        this.citiesFile = citiesFile;
    }
}
