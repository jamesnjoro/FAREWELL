package com.example.groundzero.farewell;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAdapter extends FirestoreRecyclerAdapter<postI, MyAdapter.MyAdapterHolder> {

    onclickListener listener;
    public MyAdapter(@NonNull FirestoreRecyclerOptions<postI> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull postI model) {
        holder.n.setText(model.getName());
        String text = "Age " +model.getAge()+"|" + model.getDod() + " - " + model.getDob();
        holder.db.setText(text);
        holder.dd.setText(model.getEulogy());
        if(model.getPhoto()!=null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference store = storage.getReference("deceased_pics/" + model.getPhoto());
            GlideApp.with(holder.view.getContext())
                    .load(store)
                    .into(holder.view);
        }


    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        TextView n,dd,db;
        ImageView view;
    public MyAdapterHolder(@NonNull View itemView) {
        super(itemView);
        n = (TextView)itemView.findViewById(R.id.textView15);
        dd= (TextView)itemView.findViewById(R.id.textView16);
        db= (TextView)itemView.findViewById(R.id.textView17);
        view = itemView.findViewById(R.id.pic);



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
