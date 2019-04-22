package com.example.groundzero.farewell;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class memAdapter extends FirestoreRecyclerAdapter<Memorial, memAdapter.MyAdapterHolder> {

    onclickListener listener;
    public memAdapter(@NonNull FirestoreRecyclerOptions<Memorial> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull Memorial model) {
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


    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memorial_list,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        TextView n,dd;
        ImageView view;
        public MyAdapterHolder(@NonNull View itemView) {
            super(itemView);
            n = itemView.findViewById(R.id.memName);
            dd= itemView.findViewById(R.id.memDate);
            view = itemView.findViewById(R.id.memPhoto);



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
