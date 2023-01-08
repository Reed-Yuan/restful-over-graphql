package fun.reed.restfulovergraphql.model;

import java.util.List;

public class ContinentsDTO {
    private Continent[] continents;

    // constructor from list of continents
    public ContinentsDTO(List<Continent> continents) {
        this.continents = continents.toArray(new Continent[continents.size()]);
    }

    public Continent[] getContinents() {
        return continents;
    }

    public void setContinents(Continent[] continents) {
        this.continents = continents;
    }

    @Override
    public String toString() {
        return "ContinentsDTO{" +
                "continents=" + continents +
                '}';
    }
}
