package com.fillikenesucn.petcare.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activities.PetListFragmentActivity;
import com.fillikenesucn.petcare.models.Pet;
import com.fillikenesucn.petcare.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Esta clase define objetos adapters que seran utilizados para los recyclerview del llistado de las mascotas registradas
 * Permitiendo gestionar un adapter para el controlar las funcionalidades del los items pertenecientes al listado
 * @author: Marcelo Lazo Chavez
 * @version: 28/03/2020
 */
public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.PetHolder>{

    //VARIABLES
    private Context mContext;
    private List<Pet> mPetList = new ArrayList<>();
    // LISTADO DE LAS IMAGENES ESTATICAS QUE SE DESPLEGARÁN EN EL LISTADO
    private ArrayList<Integer> dogDrawList = new ArrayList<>();
    private ArrayList<Integer> catDrawList = new ArrayList<>();
    private Random random = new Random();
    /**
     * Constructor para el adaptador del listado de mascotas asociadas al recyclerview
     * Además de inicializar las imagenes estaticas de perros y gatos que se desplagarán en el listado
     * @param mContext contexto asociado a la vista que llama al constructor
     * @param mPetList listado de mascotas (tipo objeto PET)
     */
    public PetListAdapter(Context mContext, List<Pet> mPetList) {
        this.mPetList = mPetList;
        this.mContext = mContext;
        InitDrawImages();
    }

    /**
     * Método que se encarga de poblar el listado de imagenes asociadas a las mascotas
     * asociando su identificador numerico registrado en la aplicación
     */
    private void InitDrawImages(){
        dogDrawList = DataHelper.GetDogsImages();
        catDrawList = DataHelper.GetCatsImages();
    }

    /**
     * Método que se ejecuta para crear los holders del adaptador
     * Creará las instancias del layout asociadas a los items xml que tendra los componentes de las mascotas
     * @param viewGroup Es el componente al cual se le asociara los items del adapter
     * @param i Indice asociado a la posición de la mascota en el listado
     * @return Retorna un objeto PetHolder que tiene asociado los ocmponentes del item de la lista de mascota
     */
    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_petlistitem, viewGroup, false);
        PetHolder petHolder = new PetHolder(view);
        return petHolder;
    }

    /**
     * Método que setea a un item del listado sus valores correspondientes/asociados a una mascota que se encuentra
     * en el listado de mascotas
     * Además de integrarla las funcionalidades onCLICK para su funcionamiento dentro de la aplicación
     * @param petHolder Objeto que tiene asociado los componentes del item del listado del adapter
     * @param i indice asociado a la mascota del listado de mascota
     */
    @Override
    public void onBindViewHolder(@NonNull final PetHolder petHolder, final int i) {
        final Pet petItem = mPetList.get(i);
        petHolder.petName.setText(petItem.getName());
        // SETEA UNA IMAGEN AL ITEM DEL ADAPTER
        petHolder.image.setImageResource(GetDrawableImagePet(petItem.getSpecies()));
        // SE LE AÑADE LA FUNCIONALIDAD DE QUE CUANDO SE CLICKE EL ITEM, SE DIRIGA A LA VISTA PETINFO
        petHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContext instanceof PetListFragmentActivity){
                    ((PetListFragmentActivity)mContext).OpenInfoPet(petItem.getEPC());
                }else{
                    Toast.makeText(mContext, "Ha ocurrido un error con esta mascota!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Método que se encarga de retornar un identificador numerico asociada a la imagen/drawable al tipo de especie
     * a la cual esta asociada una mascota, escogiendo una de forma aletoria de un pool de imagenes para cada especie
     * @param specie Valor string que tiene el nombre de la especie de la mascota (gato/perro)
     * @return retorna el identificador de la imagen que se desea asociar
     */
    private int GetDrawableImagePet(String specie){

        switch (specie.toUpperCase()){
            case "PERRO":
                return dogDrawList.get(random.nextInt(dogDrawList.size()));
            case "GATO":
                return catDrawList.get(random.nextInt(catDrawList.size()));
            default:
                return R.drawable.ic_launcher;
        }
    }

    /**
     * Método que retona el tamaño de mascotas asociadas al listado del adapter
     * @return retorna la cantidad de mascotas
     */
    @Override
    public int getItemCount() {
        return mPetList.size();
    }

    /**
     * Clase que retornar un objeto que tendra asociado los elementos pertenecientes a los item del listado
     * del layout al cual estará asociado el adapter del recyclerview
     */
    public class PetHolder extends RecyclerView.ViewHolder{

        // VARIABLES
        ImageView image;
        TextView petName;
        RelativeLayout parentLayout;

        /**
         * Constructor para el holder del item del listado de mascotas del layout
         * @param itemView Es el layout perteneciente al item del listado de mascotas, tendra los componentes que se
         *                 desean asociar a la vista del listado de mascotas
         */
        public PetHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            petName = (TextView) itemView.findViewById(R.id.petname);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);

        }
    }
}
