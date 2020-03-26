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
    private String EPC;
    private Boolean active;

    public Pet(String name, String sex, String birthdate, String address, String allergies, String species, String EPC) {
        this.name = name;
        this.sex = sex;
        this.birthdate = birthdate;
        this.address = address;
        this.allergies = allergies;
        this.species = species;
        this.EPC = EPC;
        this.active = true;
        this.acontecimientos = new ArrayList<>();
    }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public String getEPC() { return EPC; }

    public void setEPC(String EPC) { this.EPC = EPC; }

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
