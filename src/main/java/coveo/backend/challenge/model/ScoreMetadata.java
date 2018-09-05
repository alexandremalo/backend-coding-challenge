package coveo.backend.challenge.model;

public class ScoreMetadata {
    private long minPopulation;
    private long maxPopulation;
    private double minDistance;
    private double maxDistance;

    public ScoreMetadata(long minPopulation, long maxPopulation, double minDistance, double maxDistance) {
        this.minPopulation = minPopulation;
        this.maxPopulation = maxPopulation;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public long getMinPopulation() {
        return minPopulation;
    }

    public void setMinPopulation(long minPopulation) {
        this.minPopulation = minPopulation;
    }

    public long getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(long maxPopulation) {
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
