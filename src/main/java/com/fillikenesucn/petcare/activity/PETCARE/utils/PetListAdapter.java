package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;

import java.util.ArrayList;
import java.util.Random;

public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.PetHolder>{

    private ArrayList<Pet> mPetList = new ArrayList<>();
    private Context mContext;
    private ArrayList<Integer> dogDrawList = new ArrayList<>();
    private ArrayList<Integer> catDrawList = new ArrayList<>();

    public PetListAdapter(Context mContext, ArrayList<Pet> mPetList) {
        this.mPetList = mPetList;
        this.mContext = mContext;
        InitDrawImages();
    }

    private void InitDrawImages(){
        //dogs
        dogDrawList.add(R.drawable.perro1);
        dogDrawList.add(R.drawable.perro2);
        dogDrawList.add(R.drawable.perro3);
        dogDrawList.add(R.drawable.perro4);
        dogDrawList.add(R.drawable.perro5);
        dogDrawList.add(R.drawable.perro6);
        dogDrawList.add(R.drawable.perro7);
        dogDrawList.add(R.drawable.perro8);
        dogDrawList.add(R.drawable.perro9);
        //cats
        catDrawList.add(R.drawable.gato1);
        catDrawList.add(R.drawable.gato2);
        catDrawList.add(R.drawable.gato3);
        catDrawList.add(R.drawable.gato4);
    }

    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_petlistitem, viewGroup, false);
        PetHolder petHolder = new PetHolder(view);
        return petHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PetHolder petHolder, final int i) {
        final Pet petItem = mPetList.get(i);
        petHolder.petName.setText(petItem.getName());

        petHolder.image.setImageResource(GetDrawableImagePet(petItem.getSpecies()));
        petHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, petItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int GetDrawableImagePet(String specie){
        Random random = new Random();
        switch (specie.toUpperCase()){
            case "PERRO":
                return dogDrawList.get(random.nextInt(dogDrawList.size()));
            case "GATO":
                return catDrawList.get(random.nextInt(catDrawList.size()));
            default:
                return R.drawable.ic_launcher;
        }
    }

    @Override
    public int getItemCount() {
        return mPetList.size();
    }

    public class PetHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView petName;
        RelativeLayout parentLayout;

        public PetHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            petName = (TextView) itemView.findViewById(R.id.petname);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);

        }
    }
}
