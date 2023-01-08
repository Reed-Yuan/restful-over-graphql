package fun.reed.restfulovergraphql.model;

import java.util.HashSet;

public class Continent {
    // Continent is also a DTO, so concrete class HashSet is used instead of interface Set, otherwise
    // Jackson will not be able to deserialize the JSON to Continent object.
    private HashSet<CountryCode> countries;
    private String name;
    private HashSet<CountryCode> otherCountries;

    public Continent() {
    }

    public Continent(HashSet<CountryCode> countries, String name, HashSet<CountryCode> otherCountries) {
        this.countries = countries;
        this.name = name;
        this.otherCountries = otherCountries;
    }

    public static Continent valueOf(String code) {
        Continent continent = new Continent();
        continent.setName(code);
        return continent;
    }

    public HashSet<CountryCode> getCountries() {
        return countries;
    }

    public void setCountries(HashSet<CountryCode> countries) {
        this.countries = countries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<CountryCode> getOtherCountries() {
        return otherCountries;
    }

    public void setOtherCountries(HashSet<CountryCode> otherCountries) {
        this.otherCountries = otherCountries;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "countries=" + countries +
                ", name='" + name + '\'' +
                ", otherCountries=" + otherCountries +
                '}';
    }

}
