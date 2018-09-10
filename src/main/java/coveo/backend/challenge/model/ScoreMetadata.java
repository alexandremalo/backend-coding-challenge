package coveo.backend.challenge.model;

public class ScoreMetadata {
    private int minPopulation;
    private int maxPopulation;
    private double minDistance;
    private double maxDistance;
    private double maxName;
    private double minName;

    public ScoreMetadata(int minPopulation, int maxPopulation, double minDistance, double maxDistance, double minName, double maxName) {
        this.minPopulation = minPopulation;
        this.maxPopulation = maxPopulation;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minName = minName;
        this.maxName = maxName;
    }

    public int getMinPopulation() {
        return minPopulation;
    }

    public void setMinPopulation(int minPopulation) {
        this.minPopulation = minPopulation;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public double getMaxName() {
        return maxName;
    }

    public void setMaxName(double maxName) {
        this.maxName = maxName;
    }

    public double getMinName() {
        return minName;
    }

    public void setMinName(double minName) {
        this.minName = minName;
    }
}
