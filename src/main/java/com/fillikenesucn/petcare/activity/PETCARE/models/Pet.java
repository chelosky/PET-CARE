package com.fillikenesucn.petcare.activity.PETCARE.models;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String name;
    private String sex;
    private String birthdate;
    private String address;
    private String allergies;
    private String species;
    private List<Acontecimiento> acontecimientos;
    //private String EPC;

    public Pet(String name, String sex, String birthdate, String address, String allergies, String species) {
        this.name = name;
        this.sex = sex;
        this.birthdate = birthdate;
        this.address = address;
        this.allergies = allergies;
        this.species = species;
        this.acontecimientos = new ArrayList<>();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
