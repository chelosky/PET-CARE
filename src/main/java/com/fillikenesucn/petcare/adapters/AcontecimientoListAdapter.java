package com.fillikenesucn.petcare.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activities.EventListFragmentActivity;
import com.fillikenesucn.petcare.models.Acontecimiento;
import com.fillikenesucn.petcare.utils.DataHelper;

import java.util.ArrayList;

/**
 * Esta clase define objetos adapters que seran utilizados para los recyclerview del llistado de los acontecimientos de una mascota
 * Permitiendo gestionar un adapter para el controlar las funcionalidades del los items pertenecientes al listado
 * Tales como su eliminación del listado de mascotas
 * @author: Marcelo Lazo Chavez
 * @version: 03/04/2020
 */
public class AcontecimientoListAdapter extends  RecyclerView.Adapter<AcontecimientoListAdapter.AcontecimientoHolder>{

    //VARIABLES
    private Context mContext;
    private ArrayList<Acontecimiento> mAcontecimientoList = new ArrayList<>();

    /**
     * Constructor para el adaptador del listado de acontecimientos de una mascota asociadas al recyclerview
     * @param mContext contexto asociado a la vista que llama al constructor
     * @param mAcontecimientoList listado de acontecimientos de una mascota (tipo objeto ACONTECIMIENTO)
     */
    public AcontecimientoListAdapter(Context mContext, ArrayList<Acontecimiento> mAcontecimientoList) {
        this.mContext = mContext;
        this.mAcontecimientoList = mAcontecimientoList;
    }

    /**
     * Método que se ejecuta para crear los holders del adaptador
     * Creará las instancias del layout asociadas a los items xml que tendra los componentes de los eventos de una mascota
     * @param viewGroup Es el componente al cual se le asociara los items del adapter
     * @param i Indice asociado a la posición del acontecimiento en el listado
     * @return Retorna un objeto AcontecimientoHolder que tiene asociado los ocmponentes del item de la lista de acontecimientos
     */
    @NonNull
    @Override
    public AcontecimientoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_eventlistitem, viewGroup, false);
        AcontecimientoHolder acontecimientoHolder = new AcontecimientoHolder(view);
        return acontecimientoHolder;
    }

    /**
     * Método que setea a un item del listado sus valores correspondientes/asociados a un acontecimiento que se encuentra
     * en el listado de acontecimientos. Además se vincula con el dialog que pedira confirmación para eliminar un acontecimiento
     * @param acontecimientoHolder Objeto que tiene asociado los componentes del item del listado del adapter
     * @param i indice asociado al acontecimiento del listado de acontecimiento
     */
    @Override
    public void onBindViewHolder(@NonNull AcontecimientoHolder acontecimientoHolder, final int i) {
        final Acontecimiento acontecimiento = mAcontecimientoList.get(i);
        acontecimientoHolder.txtTitulo.setText(acontecimiento.getTitulo());
        acontecimientoHolder.txtDescripcion.setText(acontecimiento.getDescripcion());
        acontecimientoHolder.txtFecha.setText(acontecimiento.getFecha());
        acontecimientoHolder.imgColor.setBackground(DataHelper.GetRandomColor());
        acontecimientoHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog(i);
            }
        });
    }

    /**
     * Método que se encarga de desplegar un dialog de confirmación para la eliminación de un acontecimiento especifico
     * @param i indice asociado al acontecimiento que se desea eliminar
     */
    private void ConfirmDialog(final int i){
        AlertDialog.Builder builder = DataHelper.CreateAlertDialog(mContext,"Confirmación","¿Está seguro que desea eliminar este acontecimiento?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ((EventListFragmentActivity)mContext).DeleteAcontecimiento(i);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Método que retona el tamaño de asconteciminetos asociados al listado del adapter
     * @return retorna la cantidad de acontecimientos
     */
    @Override
    public int getItemCount() {
        return mAcontecimientoList.size();
    }

    /**
     * Clase que retornar un objeto que tendra asociado los elementos pertenecientes a los item del listado de acontecimientos
     * del layout al cual estará asociado el adapter del recyclerview
     */
    public class AcontecimientoHolder extends RecyclerView.ViewHolder{

        // VARIABLES
        TextView txtTitulo;
        TextView txtFecha;
        TextView txtDescripcion;
        ImageView imgColor;
        Button btnDelete;

        /**
         * Constructor para el holder del item del listado de acontecimientos del layout
         * @param itemView Es el layout perteneciente al item del listado de Acontecimientos, tendra los componentes que se
         *                 desean asociar a la vista del listado de acontecimientos de una mascota
         */
        public AcontecimientoHolder(@NonNull View itemView) {
            super(itemView);
            imgColor = (ImageView) itemView.findViewById(R.id.imgCOLOR);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtDescripcion = (TextView) itemView.findViewById(R.id.txtDescripcion);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}
