package com.fillikenesucn.petcare.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.models.Acontecimiento;
import com.fillikenesucn.petcare.models.Pet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Esta clase representa a un helper para la obtencion de información global para toda la aplicación
 * @author: Marcelo Lazo Chavez
 * @version: 2/04/2020
 */
public class DataHelper {
    public static final Random r = new Random();
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

    /**
     * Función que se encarga de verificar si una mascota es valida para registrarla
     * @param context contexto actual de la aplicación
     * @param pet objeto mascota con los datos a evaluar
     * @return retorna true si es una mascota valida, en caso contrario retorna false
     */
    public static boolean VerificarMascotaValida(Context context, Pet pet){
        int errorOcurred = 0;
        // NOMBRE, DIRECCION Y NACIMIENTO
        errorOcurred += VerificarString(context, pet.getName(), "Nombre Requerido");
        errorOcurred += VerificarString(context, pet.getAddress(), "Dirección Requerida");
        errorOcurred += VerificarString(context, pet.getBirthdate(), "Fecha de Nacimiento Requerida");
        // VERIFICAR EPC
        if( pet.getEPC().equals(GetDefaultTagRFID()) ){
            Toast.makeText(context,"Seleccione Tag Valido",Toast.LENGTH_SHORT).show();
            errorOcurred++;
        }
        // ESPECIE
        if(DataHelper.GetSpecies().get(0).equals(pet.getSpecies())){
            Toast.makeText(context,"Seleccione una Especie",Toast.LENGTH_SHORT).show();
            errorOcurred++;
        }
        return errorOcurred > 0 ? false : true;
    }

    /**
     * Función que verifica si un string esta vacio
     * @param context contexto actual de la aplicación
     * @param txtEvaluate string a evaluar
     * @param error string que se desplegará como información al usuarios
     * @return retorna 1 si esta vacio y 0 si no lo esta
     */
    public static int VerificarString(Context context, String txtEvaluate, String error){
        if(txtEvaluate.isEmpty()){
            Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * Función que se encarga de verificar si un acontecimiento es valido para registrarlo
     * @param context contexto actual de la aplicación
     * @param acontecimiento Objeto acontecimiento con los datos a validar
     * @param epc valor del epc asociado a la mascota del ascontecimiento
     * @return retorna true si el acontecimiento es valido, en caso contrario false
     */
    public static boolean VerificarAcontecimientoValido(Context context,Acontecimiento acontecimiento, String epc){
        int errorOcurred = 0;
        // TITULO Y FECHA
        errorOcurred += VerificarString(context, acontecimiento.getTitulo(), "Titulo Requerido");
        errorOcurred += VerificarString(context, acontecimiento.getTitulo(), "Fecha Requerida");
        if( epc.equals(GetDefaultTagRFID()) ){
            Toast.makeText(context,"Seleccione Tag Valido",Toast.LENGTH_SHORT).show();
            errorOcurred++;
        }
        return errorOcurred > 0 ? false : true;
    }



    /**
     * Función que retorna el string definido como el tag por defecto de la aplicación
     * @return retorna el string default
     */
    public static String GetDefaultTagRFID(){
        return "TAG: XXXX-XXXXXX-XXXXX";
    }

    /**
     * Método que se encargá de retornar un listado con los identificadores de las imagenes estaticas
     * de los perros
     * @return retorna un listado con identificadores numericos de imagenes
     */
    public static ArrayList<Integer> GetDogsImages(){
        // DOGS
        ArrayList<Integer> dogDrawList = new ArrayList<>();
        dogDrawList.add(R.drawable.perro1);
        dogDrawList.add(R.drawable.perro2);
        dogDrawList.add(R.drawable.perro3);
        dogDrawList.add(R.drawable.perro4);
        dogDrawList.add(R.drawable.perro5);
        dogDrawList.add(R.drawable.perro6);
        dogDrawList.add(R.drawable.perro7);
        dogDrawList.add(R.drawable.perro8);
        dogDrawList.add(R.drawable.perro9);
        return dogDrawList;
    }

    /**
     * Método que se encargá de retornar un listado con los identificadores de las imagenes estaticas
     * de los gatos
     * @return retorna un listado con identificadores numericos de imagenes
     */
    public static ArrayList<Integer> GetCatsImages(){
        // CATS
        ArrayList<Integer> catDrawList = new ArrayList<>();
        catDrawList.add(R.drawable.gato1);
        catDrawList.add(R.drawable.gato2);
        catDrawList.add(R.drawable.gato3);
        catDrawList.add(R.drawable.gato4);
        return catDrawList;
    }
}
