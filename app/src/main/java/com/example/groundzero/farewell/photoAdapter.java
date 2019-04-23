package com.example.groundzero.farewell;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.viewHolder> {
    Context mcontext;
    List<photo> mphotos;
    FirebaseStorage storage;
    public photoAdapter(Context context, List<photo> photos){
        mcontext = context;
        mphotos = photos;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        photo Currentphoto = mphotos.get(i);
        storage = FirebaseStorage.getInstance();
        StorageReference store = storage.getReference("photos/" + Currentphoto.getPath());
        GlideApp.with(mcontext)
                .load(store)
                .into(viewHolder.view);
    }

    @Override
    public int getItemCount() {
        return mphotos.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView view;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.photoss);
        }
    }

}
