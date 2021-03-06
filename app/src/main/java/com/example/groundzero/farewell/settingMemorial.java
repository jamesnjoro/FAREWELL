package com.example.groundzero.farewell;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class settingMemorial extends FirestoreRecyclerAdapter<Memorial, settingMemorial.MyAdapterHolder> {

    onclickListener listener;

    public settingMemorial(@NonNull FirestoreRecyclerOptions<Memorial> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull final Memorial model) {
        holder.n.setText(model.getName());
        String text =model.getBirth();
        holder.dd.setText(text);
        if(model.getPhoto()!=null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference store = storage.getReference("deceased_pics/" + model.getPhoto());
            GlideApp.with(holder.view.getContext())
                    .load(store)
                    .placeholder(R.drawable.noimage)
                    .into(holder.view);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("memorials").document(model.getName());
                ref.delete();
            }
        });


    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setting_memorial,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        TextView n,dd;
        ImageView view;
        Button delete;
        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            n = itemView.findViewById(R.id.memName1);
            dd= itemView.findViewById(R.id.memDate1);
            view = itemView.findViewById(R.id.memPhoto1);
            delete = itemView.findViewById(R.id.delete);




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
        void onItemClick(DocumentSnapshot documentSnapshot, int position);


    }
    public void setOnclickListener(onclickListener listener){
        this.listener = listener;
    }
}
