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


public class storyAdapter extends FirestoreRecyclerAdapter<story, storyAdapter.MyAdapterHolder> {

    onclickListener listener;
    public storyAdapter(@NonNull FirestoreRecyclerOptions<story> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull story model) {
        holder.user.setText(model.getUserEmail());
        holder.story.setText(model.getStory());


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
