package com.example.groundzero.farewell;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class photo2Adapter extends FirestoreRecyclerAdapter<photo, photo2Adapter.MyAdapterHolder> {

    onclickListener listener;
    public photo2Adapter(@NonNull FirestoreRecyclerOptions<photo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull photo model) {
        if(model.getPath()!=null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference store = storage.getReference("photos/" + model.getPath());
            GlideApp.with(holder.view.getContext())
                    .load(store)
                    .placeholder(R.drawable.noimage)
                    .into(holder.view);
        }


    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        ImageView view;
        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.photoss);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                }
            });
        }

    }

    public interface onclickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);


    }
    public void setOnclickListener(onclickListener listener){
        this.listener = listener;
    }
}
