package com.fillikenesucn.petcare.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Pet {
    private String name;
    private String sex;
    private String birthdate;
    private String address;
    private String allergies;
    private String species;
    private ArrayList<Acontecimiento> acontecimientos;
    private String EPC;
    private Boolean active;

    // Constructor del objecto Pet con nombre, sexo, fecha de nacimiento, alergias, especie y el Tag asignado
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

    // Escribimos la lista de acontecimientos en un String
    @NonNull
    @Override
    public String toString() {
        // Iniciamos un string vacío
        String listaAcontecimientos = "";
        for(int i = 0; i < this.acontecimientos.size(); i++){
            // Agregamos el acontecimiento con sus datos, esto por cada acontecimiento de la lista
            listaAcontecimientos += "Acontecimiento (" + (i+1) + ") { ";
            listaAcontecimientos += "Título: " + acontecimientos.get(i).getTitulo();
            listaAcontecimientos += ", Fecha: " + acontecimientos.get(i).getFecha();
            listaAcontecimientos += ", Descripción: " + acontecimientos.get(i).getDescripcion();
            listaAcontecimientos += " }\n";
        }
        // Devolvemos el string con la lista de acontecimientos
        return listaAcontecimientos;
    }

    // Obtenemos la lista de acontecimientos de la mascota
    public ArrayList<Acontecimiento> getEventList(){ return this.acontecimientos; }

    // Le damos una lista de acontecimientos a la mascota
    public void addEventList(ArrayList<Acontecimiento> acontecimientos){ this.acontecimientos = acontecimientos; }

    // Agregamos un acontecimiento nuevo a la lista
    public void addEvent(Acontecimiento acontecimiento){ this.acontecimientos.add(acontecimiento); }

    // Obtenemos un acontecimiento
    public Acontecimiento getEvent(Integer i){ return this.acontecimientos.get(i); }

    // Obtenemos el estado de la mascota ( activo/inactivo )
    public Boolean getActive() { return active; }

    // Cambiamos el valor de activo
    public void setActive(Boolean active) { this.active = active; }

    // Obtenemos el EPC del Tag
    public String getEPC() { return EPC; }

    // Cambiamos el EPC
    public void setEPC(String EPC) { this.EPC = EPC; }

    // Obtenemos la especie de la mascota
    public String getSpecies() {
        return species;
    }

    // Cambiamos la especie
    public void setSpecies(String species) {
        this.species = species;
    }

    // Obtenemos el nombre de la mascota
    public String getName() {
        return name;
    }

    // Cambiamos el nombre
    public void setName(String name) {
        this.name = name;
    }

    // Obtenemos el sexo de la mascota
    public String getSex() {
        return sex;
    }

    // Cambiamos el sexo
    public void setSex(String sex) {
        this.sex = sex;
    }

    // Obtenemos la fecha de nacimiento de la mascota
    public String getBirthdate() {
        return birthdate;
    }

    // Cambiamos la fecha de nacimiento
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    // Obtenemos la dirección donde vive
    public String getAddress() {
        return address;
    }

    // Cambiamos su domicilio
    public void setAddress(String address) { this.address = address; }

    // Obtenemos las alergias de la mascota
    public String getAllergies() {
        return allergies;
    }

    // Cambiar las alergias
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

}
