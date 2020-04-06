package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fillikenesucn.petcare.activity.PETCARE.models.Acontecimiento;
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Esta clase representa a un helper para la obtencion de información de las mascotas y sus acontecimientos
 * @author: Rodrigo Dorat Merejo
 * @version: 04/04/2020
 */

public class IOHelper {

    /**
     * Función que se encarga de verificar si ya está creado el archivo de texto
     * @param context contexto asociado a la vista que llama a la función
     * @return Retorna un booleano que avisa si existe el archivo de texto
     */
    public static Boolean CheckFile(Context context){
        try {
            // Buscamos el txt de 'base de datos'
            FileInputStream fis = context.openFileInput("petcare.txt");
            // Si existe retorna verdadero para que no se cree de nuevo
            if (fis != null) { return true; }
            // Si no existe retorna falso para crear uno
            else { return false; }
        } catch (FileNotFoundException e){ return false; }
    }

    /**
     * Función que se encarga de generar un nuevo archivo de texto
     * @param context contexto asociado a la vista que llama a la función
     * @param objStr String que representa la información que habrá dentro del texto (en esta oportunidad es un array vacío
     */
    public static void WriteJson(Context context, String objStr){
        FileOutputStream outputStream;
        try {
            // Usado para transformar de JSON a String que simula un JSON
            Gson gson = new Gson();
            // Se genera el archivo de texto por primera vez
            outputStream = context.openFileOutput("petcare.txt", Context.MODE_PRIVATE);
            // Dentro del txt sólo hay un  array vacío
            outputStream.write(gson.toJson(objStr).getBytes());
            outputStream.close();
            Log.d("DORAT", "Archivo creado exitosamente!");
        } catch (Exception e) { Log.d("DORAT", "No se pudo crear el archivo..."); }
    }

    /**
     * Función que se encarga de leer el archivo de texto de la aplicación
     * @param context contexto asociado a la vista que llama a la función
     * @return Retorna un string con la información que existe dentro del archivo
     */
    public static String ReadFileString(Context context){
        try {
            // Buscamos y leemos el archivo de texto que funciona como 'base de datos'
            FileInputStream fis = context.openFileInput("petcare.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            // Pasamos el texto con saltos de línea a una sola línea de texto
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            // Limpiamos la línea de texto de caracteres innecesarios que se agregan con GSON
            String json = sb.toString();
            json = json.replace("\\", "");
            json = json.substring(1);
            json = json.substring(0, json.length() - 1);
            return json;
        }catch (FileNotFoundException e){
            return "FileNotFoundException";
        }catch (IOException e){
            return "IOException";
        }
    }

    /**
     * Función que se encarga de revisar si el EPC que queremos asignar a una mascota ya está en uso
     * @param json información que hay dentro del archivo de texto
     * @param object el nuevo objeto mascota que se va a ingresar con el EPC
     * @return Retorna un Integer que corresponde a la posición en la lista si encontró el EPC utilizado, en su defecto -1
     */
    public static Integer CheckActiveEPC(JSONArray json, JSONObject object){
        try {
            // Buscamos en la JSONArray
            for (int i = 0; i < json.length(); i++) {
                // Guardamos el JSONObject visitado
                JSONObject curr = json.getJSONObject(i);
                // Revisamos si coincide con la EPC y si está activo
                if (curr.getString("EPC").equals(object.getString("EPC")) && curr.getBoolean("active")) return i;
            }
            return -1;
        } catch (Exception e) {
            Log.d("DORAT", e.toString());
            return -1;
        }
    }

    /**
     * Función complementaria que se encarga de revisar si el EPC que queremos asignar a una mascota ya está en uso
     * @param context contexto asociado a la vista que llama a la función
     * @param epc el tag escaneado para buscar a la mascota
     * @return Retorna un Integer que corresponde a la posición en la lista si encontró el EPC utilizado, en su defecto -1
     */
    public static Integer CheckActiveEPC(Context context, String epc){
        try{
            // Se lee el archivo
            String jsonTxt = IOHelper.ReadFileString(context);
            // Lo transformamos a una lista
            JSONArray jsonArray = new JSONArray(jsonTxt);
            // Inicializamos un objeto que tendrá el EPC
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("EPC",epc);
            // Usamos la función original para ver si existe
            return CheckActiveEPC(jsonArray, jsonObject);
        }catch (Exception e){
            Log.d("DORAT", e.toString());
            return -1;
        }
    }

    /**
     * Función que se encarga de generar un nuevo archivo de texto con la información ACTUALIZADA
     * @param context contexto asociado a la vista que llama a la función
     * @param pet objeto Pet que se añadirá a la lista
     */
    public static void AddPet(Context context, Pet pet){
        try {
            // Obtenemos el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformamos el String a JSONArray
            JSONArray json = new JSONArray(sxml);
            // Transformamos el Pet a String
            Gson gson = new Gson();
            String objStr = gson.toJson(pet);
            // Transformamos el String a JSONObject
            JSONObject object = new JSONObject(objStr);
            // Check if the EPC is used by an active pet
            if (CheckActiveEPC(json, object) == -1){
                // Agregamos el JSONObject al JSONArray
                json.put(object);
                // Escribimos el nuevo txt
                WriteJson(context,json.toString());
                Toast.makeText(context, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "TAG OCUPADO", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("DORAT", e.toString());
            Toast.makeText(context, "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Función que se encarga de agregar un nuevo evento a la mascota
     * @param context contexto asociado a la vista que llama a la función
     * @param acontecimiento información del nuevo evento que se va a agregar
     * @param epc el EPC que corresponde al de la mascota que le pertenece el evento
     */
    public static void AddEvent(Context context, Acontecimiento acontecimiento, String epc){
        try {
            // Obtenemos el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformamos el String a JSONArray
            JSONArray json = new JSONArray(sxml);
            // Transformamos el acontecimiento a String
            Gson gson = new Gson();
            String objStr = gson.toJson(acontecimiento);
            // Transformamos el String a JSONObject
            JSONObject event = new JSONObject(objStr);
            // Transformamos el epc a formato JSONObject
            JSONObject tag = new JSONObject();
            tag.put("EPC", epc);
            // Verificamos si el EPC esta usado por alguna mascota activa
            Integer indexPet = CheckActiveEPC(json, tag);
            if (indexPet != -1){
                // Obtenemos la mascota
                JSONObject object = json.getJSONObject(indexPet);
                // Obtener los acontecimientos de la mascota
                JSONArray acontecimientos = object.getJSONArray("acontecimientos");
                // Agregar un nuevo Acontecimeinto
                acontecimientos.put(event);
                // Escribir el nuevo txt
                WriteJson(context,json.toString());
                Toast.makeText(context, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "NO HAY REGISTRO PARA ESTE TAG", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("DORAT", e.toString());
            Toast.makeText(context, "ERROR AL AGREGAR EVENTO", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Función que se encarga de obtener la información de la mascota por su EPC
     * @param context contexto asociado a la vista que llama a la función
     * @param epc el tag escaneado para buscar a la mascota
     * @return Retorna un objeto mascota con toda su información
     */
    public static Pet GetPet(Context context, String epc){
        try {
            // Obtener el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformar el String a formato JSONArray
            JSONArray json = new JSONArray(sxml);
            // Verificar el EPC
            int indexPet = CheckActiveEPC(context,epc);
            if(indexPet != -1){
                JSONObject jsonObject = json.getJSONObject(indexPet);
                // Transformar el JSONObject a un objeto Pet
                Pet pet = new Pet(jsonObject.getString("name"),jsonObject.getString("sex"),jsonObject.getString("birthdate"),jsonObject.getString("address"),
                        jsonObject.getString("allergies"),jsonObject.getString("species"),jsonObject.getString("EPC"));
                // Obtenemos la lista de acontecimientos
                pet = GetEventsPerPet(pet,jsonObject);
                // Return the Pet Object with the Event List
                return pet;
            }
            // Retornamos vacío si no encontramos el EPC o si la mascota estaba inactiva
            return null;
        } catch (Exception e) {
            Log.d("DORAT", e.toString());
            Toast.makeText(context, "NO SE PUDO OBTENER LA MASCOTA", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * Función que se encarga de obtener la información de todas las mascotas
     * @param context contexto asociado a la vista que llama a la función
     * @return Retorna una lista de objetos de mascotas con toda su información
     */
    public static ArrayList<Pet> PetList(Context context){
        try {
            // Obtener el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformar el String a formato JSONArray
            JSONArray json = new JSONArray(sxml);
            // Variable de una lista de objetos Pet
            ArrayList<Pet> petList = new ArrayList<Pet>();
            for (int i = 0; i < json.length(); i++) {
                // Guardamos el JSONObject de la mascota en una variable
                JSONObject curr = json.getJSONObject(i);
                if (curr.getBoolean("active")) {
                    // Transformamos de JSONObject a un objecto Pet
                    Pet pet = new Pet(curr.getString("name"), curr.getString("sex"), curr.getString("birthdate"), curr.getString("address"), curr.getString("allergies"), curr.getString("species"), curr.getString("EPC"));
                    // Obtenemos la lista de acontecimientos
                    pet = GetEventsPerPet(pet, curr);
                    // Se añade a la lista
                    petList.add(pet);
                }
            }
            // Retorna una lista de objeto Pet
            return petList;
        } catch (Exception e) {
            Toast.makeText(context, "NO SE PUDO OBTENER EL LISTADO DE MASCOTAS", Toast.LENGTH_SHORT).show();
            Log.d("DORAT", e.toString());
            return null;
        }
    }

    /**
     * Función que se encarga de obtener la información de todas las actividades por la mascota
     * @param pet objeto mascota a la que se le asignan los eventos
     * @param curr es el objeto JSON que se obtuvo al leer el archivo de texto
     * @return Retorna la mascota con su lista de eventos
     */
    public static Pet GetEventsPerPet(Pet pet, JSONObject curr){
        try {
            // Obtenemos la lista de acontecimientos
            JSONArray list = curr.getJSONArray("acontecimientos");
            for (int j = 0; j < list.length(); j++) {
                // Guardamos el acontecimiento de la posición j en una variable
                JSONObject event = list.getJSONObject(j);
                // Transformamos el JSONObject al objeto Acontecimiento
                Acontecimiento acontecimiento = new Acontecimiento(event.getString("titulo"), event.getString("fecha"), event.getString("descripcion"));
                // Guardamos los acontecimientos de la mascota en una lista de objectos Acontecimiento
                pet.addEvent(acontecimiento);
            }
            return pet;
        } catch (JSONException e) {
            Log.d("DORAT", e.toString());
            return null;
        }
    }

    /**
     * Función que se encarga de actualizar la información de una mascota
     * @param context contexto asociado a la vista que llama a la función
     * @param newPet objeto mascota que se va a actualizar
     * @param oldEPC el el EPC que tenía originalmente la mascota (para encontrarlo en la base de datos)
     * @return Retorna la mascota con su lista de eventos
     */
    public static void UpdatePetInfo(Context context, Pet newPet, String oldEPC){
        try {
            // Obtener el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformar el String a formato JSONArray
            JSONArray json = new JSONArray(sxml);
            //
            JSONObject epc = new JSONObject();
            epc.put("EPC",oldEPC);
            Integer indexPet = CheckActiveEPC(json,epc);
            // Guardamos el JSONObject de la mascota en una variable
            JSONObject curr = json.getJSONObject(indexPet);
            // Transformamos de un objeto Pet a un JSONObject
            curr.put("name", newPet.getName());
            curr.put("sex", newPet.getSex());
            curr.put("birthdate", newPet.getBirthdate());
            curr.put("address", newPet.getAddress());
            curr.put("allergies", newPet.getAllergies());
            curr.put("species", newPet.getSpecies());
            curr.put("EPC", newPet.getEPC());
            // Escribimos el nuevo txt
            WriteJson(context,json.toString());
            Toast.makeText(context, "MASCOTA ACTUALIZADA", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "HA OCURRIDO UN ERROR", Toast.LENGTH_SHORT).show();
            Log.d("DORAT", e.toString());
        }
    }

    /**
     * Función que se encarga de obtener la información de todas las actividades por la mascota
     * @param context contexto asociado a la vista que llama a la función
     * @param epc el el EPC que tiene asignada la mascota
     * @return Retorna la mascota con su lista de eventos
     */
    public static boolean UnlinkPet(Context context, String epc){
        try{
            // Leemos el archivo
            String txtJson = ReadFileString(context);
            // Transformamos a una lista
            JSONArray jsonArray = new JSONArray(txtJson);
            // Obtenemos la posición en la lista
            int indexPet = CheckActiveEPC(context,epc);
            if (indexPet != -1){
                // Obtenemos la mascota
                JSONObject jsonObject = jsonArray.getJSONObject(indexPet);
                // Lo desvinculamos
                jsonObject.put("active", false);
                // Se escribe el nuevo archivo
                WriteJson(context, jsonArray.toString());
                return true;
            }
            return false;
        }catch (Exception e){
            Log.d("DORAT", e.toString());
            return false;
        }
    }

    /**
     * Función que se encarga de obtener la información de todas las actividades por la mascota
     * @param context contexto asociado a la vista que llama a la función
     * @param epc tag para buscar la mascota correspondiente
     * @param indexAcontecimiento la posición en la lista de acontecimientos
     * @return Retorna la mascota con su lista de eventos
     */
    public static void UpdateEventList(Context context, String epc, int indexAcontecimiento){
        try{
            // Leemos el archivo
            String txtJson = ReadFileString(context);
            // Transformamos a una lista
            JSONArray jsonArray = new JSONArray(txtJson);
            // Obtenemos la posición en la lista
            int indexPet = CheckActiveEPC(context,epc);
            if (indexPet != -1){
                Pet pet = GetPet(context, epc);
                // Obtenemos la lista de acontecimientos
                ArrayList<Acontecimiento> acontecimientos = pet.getEventList();
                // Removemos el que no necesitamos
                acontecimientos.remove(indexAcontecimiento);
                // Eliminamos la mascota
                jsonArray.remove(indexPet);
                // actualiza el archivo
                WriteJson(context, jsonArray.toString());
                // La añadimos con las acutalizaciones
                AddPet(context,pet);
                Toast.makeText(context, "LISTA DE EVENTOS ACTUALIZADA", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "NO SE ENCONTRÓ LA MASCOTA", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.d("DORAT", e.toString());
            Toast.makeText(context, "ERROR AL BORRAR ACONTECIMIENTO", Toast.LENGTH_SHORT).show();
        }
    }

}
