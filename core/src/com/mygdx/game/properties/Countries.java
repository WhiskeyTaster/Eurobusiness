package com.mygdx.game.properties;

public enum Countries {

    GREECE("Greece", 2),
    ITALY("Italy", 3),
    SPAIN("Spain", 3),
    ENGLAND("England", 3),
    BENELUX("Benelux", 3),
    SWEDEN("Sweden", 3),
    RFN("RFN", 3),
    AUSTRIA("Austria", 2);

    private final Integer cities;
    private final String country;

    private Countries(String country, Integer cities) {
        this.country = country;
        this.cities = cities;
    }

    public Integer getCities() {
        return cities;
    }

    public String getCountry() {
        return country;
    }

    public static Countries getCountry(String countryName) {
        for (Countries countries : values())
            if (countries.getCountry().equals(countryName))
                return countries;

        return null;
    }
}
