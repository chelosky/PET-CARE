package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fillikenesucn.petcare.activity.PETCARE.models.Acontecimiento;
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IOHelper {

    public static Boolean CheckFile(Context context){
        try {
            // Buscamos el txt de 'base de datos'
            FileInputStream fis = context.openFileInput("petcare.txt");
            if (fis != null) {
                // Si existe retorna verdadero para que no se cree de nuevo
                return true;
            } else {
                // Si no existe retorna falso para crear uno
                return false;
            }
        } catch (FileNotFoundException e){
            return false;
        }
    }

    public static Boolean InitJson(Context context, String objStr){
        FileOutputStream outputStream;
        try {
            // Se genera el archivo de texto por primera vez
            outputStream = context.openFileOutput("petcare.txt", Context.MODE_PRIVATE);
            // Dentro del txt sólo hay un  array vacío
            outputStream.write(objStr.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
            //
            return "FileNotFoundException";
        }catch (IOException e){
            //
            return "IOException";
        }
    }

    public static Boolean WriteJson(Context context, String objStr){
        FileOutputStream outputStream;
        try {
            // Obtenemos el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformamos el String a JSONArray
            JSONArray json = new JSONArray(sxml);
            // Transformamos el String a JSONObject
            JSONObject object = new JSONObject(objStr);
            // Check if the EPC is used by an active pet
            if (CheckActiveEPC(json, object) == -1){
                // Agregamos el JSONObject al JSONArray
                json.put(object);
                // Usado para transformar de JSON a String que simula un JSON
                Gson gson = new Gson();

                // Escribimos el nuevo txt
                outputStream = context.openFileOutput("petcare.txt", Context.MODE_PRIVATE);
                outputStream.write(gson.toJson(json.toString()).getBytes());
                outputStream.close();

                return true;
            } else {
                Toast.makeText(context, "TAG OCUPADO", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

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
            e.printStackTrace();
            Log.d("DORAT", e.toString());
            return -1;
        }
    }

    public static Boolean AddEvent(Context context, String objStr, JSONObject tag){
        FileOutputStream outputStream;
        try {
            // Obtenemos el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformamos el String a JSONArray
            JSONArray json = new JSONArray(sxml);
            // Transformamos el String a JSONObject
            JSONObject event = new JSONObject(objStr);
            // Verificamos si el EPC esta usado por alguna mascota activa
            Integer pos = CheckActiveEPC(json, tag);
            if (pos != -1){
                // Obtenemos la mascota
                JSONObject object = json.getJSONObject(pos);
                // Obtener los acontecimientos de la mascota
                JSONArray acontecimientos = object.getJSONArray("acontecimientos");
                // Agregar un nuevo Acontecimeinto
                acontecimientos.put(event);

                // Usado para transformar de JSON a String que simula un JSON
                Gson gson = new Gson();

                // Escribir el nuevo txt
                outputStream = context.openFileOutput("petcare.txt", Context.MODE_PRIVATE);
                outputStream.write(gson.toJson(json.toString()).getBytes());
                outputStream.close();

                return true;
            } else {
                Toast.makeText(context, "NO HAY REGISTRO PARA ESTE TAG", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ERROR AL AGREGAR", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static Pet GetPet(Context context, String epc){
        try {
            // Obtener el JSON desde el contexto de la APP
            String sxml = ReadFileString(context);
            // Transformar el String a formato JSONArray
            JSONArray json = new JSONArray(sxml);
            // Verificar cada elemento de la lista
            for (int i = 0; i < json.length(); i++) {
                JSONObject curr = json.getJSONObject(i);
                // Verificamos si el objeto que se esta visitando posee el EPC que buscamos y si esta activa
                if (curr.getString("EPC").equals(epc) && curr.getBoolean("active")) {
                    // Transformar el JSONObject a un objeto Pet
                    Pet pet = new Pet(curr.getString("name"),curr.getString("sex"),curr.getString("birthdate"),curr.getString("address"),curr.getString("allergies"),curr.getString("species"),curr.getString("EPC"));
                    // Obtenemos la lista de acontecimientos
                    JSONArray list = curr.getJSONArray("acontecimientos");
                    for(int j = 0; j < list.length(); j++){
                        // Guardamos el acontecimiento de la posición j en una variable
                        JSONObject event = list.getJSONObject(j);
                        // Transformamos el JSONObject al objeto Acontecimiento
                        Acontecimiento acontecimiento = new Acontecimiento(event.getString("titulo"),event.getString("fecha"),event.getString("descripcion"));
                        // Guardamos los acontecimientos de la mascota en una lista de objectos Acontecimiento
                        pet.addEvent(acontecimiento);
                    }
                    // Return the Pet Object with the Event List
                    return pet;
                }
            }
            // Retornamos vacío si no encontramos el EPC o si la mascota estaba inactiva
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DORAT", e.toString());
            return null;
        }
    }

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
                // Transformamos de JSONObject a un objecto Pet
                Pet pet = new Pet(curr.getString("name"),curr.getString("sex"),curr.getString("birthdate"),curr.getString("address"),curr.getString("allergies"),curr.getString("species"),curr.getString("EPC"));
                // Se añade a la lista
                petList.add(pet);
            }
            // Retorna una lista de objeto Pet
            return petList;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DORAT", e.toString());
            return null;
        }
    }

}
