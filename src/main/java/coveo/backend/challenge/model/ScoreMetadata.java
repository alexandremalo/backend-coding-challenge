package coveo.backend.challenge.model;

public class ScoreMetadata {
    private int minPopulation;
    private int maxPopulation;
    private double minDistance;
    private double maxDistance;

    public ScoreMetadata(int minPopulation, int maxPopulation, double minDistance, double maxDistance) {
        this.minPopulation = minPopulation;
        this.maxPopulation = maxPopulation;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
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
}
