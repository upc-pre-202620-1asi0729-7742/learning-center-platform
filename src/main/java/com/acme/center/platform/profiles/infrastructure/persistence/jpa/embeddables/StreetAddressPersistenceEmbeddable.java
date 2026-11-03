package com.acme.center.platform.profiles.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for a street address.
 */
@Embeddable
public class StreetAddressPersistenceEmbeddable {

    @Column(name = "street_address_street")
    private String street;

    @Column(name = "street_address_number")
    private String number;

    @Column(name = "street_address_city")
    private String city;

    @Column(name = "street_address_postal_code")
    private String postalCode;

    @Column(name = "street_address_country")
    private String country;

    public StreetAddressPersistenceEmbeddable() {
    }

    public StreetAddressPersistenceEmbeddable(String street, String number, String city, String postalCode, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

