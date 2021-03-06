package com.example.groundzero.farewell;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class storyAdapter extends FirestoreRecyclerAdapter<story, storyAdapter.MyAdapterHolder> {

    FirebaseFirestore db;
    DocumentReference ref;
    StorageReference store;
    FirebaseStorage storage;
    onclickListener listener;
    public storyAdapter(@NonNull FirestoreRecyclerOptions<story> options) {
        super(options);
    }
    String user, path;
    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull story model) {

        holder.story.setText(model.getStory());
        db = FirebaseFirestore.getInstance();
        storage =FirebaseStorage.getInstance();
        ref = db.collection("users").document(model.getUserEmail());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              user = documentSnapshot.getString("name");
              path = documentSnapshot.getString("path");
            }
        });

        holder.user.setText(user);
        store = storage.getReference("users_dp/" + path);
        GlideApp.with(holder.view.getContext())
                .load(store)
                .into(holder.view);


    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.story_item,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        TextView user,story;
        ImageView view;
        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            user = (TextView)itemView.findViewById(R.id.storyuser);
            story= (TextView)itemView.findViewById(R.id.story);
            view = itemView.findViewById(R.id.storypic);




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
