package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Esta clase representa a un helper para la obtencion de información global para toda la aplicación
 * @author: Marcelo Lazo Chavez
 * @version: 2/04/2020
 */
public class DataHelper {
    /**
     * Función que se encarga de retornar todas las especies del sistema
     * @return un listado con todas las especies del sistema (gatos y perros)
     */
    public static ArrayList<String> GetSpecies(){
        ArrayList<String> pets = new ArrayList<>();
        pets.add(0, "Seleccione Especie");
        pets.add("Perro");
        pets.add("Gato");
        return pets;
    }

    /**
     * Función que retorna un drawable con colores aleatorios
     * @return retorna un drawable de tipo rectangle con colores aleatorios
     */
    public static GradientDrawable GetRandomColor(){
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red,green,blue));
        return draw;
    }

    /**
     * Función que se encarga de generar un dialog de alerta para la aplicación
     * @param context contexto asociado a la vista que llama a la función
     * @param tittle String que representa al titulo del dialog
     * @param description String que representa a la descripcion del dialog
     * @return Retorna un constructor de un dialog de alerta con los datos entregados como parámetros
     */
    public static AlertDialog.Builder CreateAlertDialog(Context context, String tittle, String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(description);
        return builder;
    }
}
