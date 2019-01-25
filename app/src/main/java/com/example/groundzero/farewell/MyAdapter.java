package com.example.groundzero.farewell;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MyAdapter extends FirestoreRecyclerAdapter<postI, MyAdapter.MyAdapterHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirestoreRecyclerOptions<postI> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapterHolder holder, int position, @NonNull postI model) {
        holder.n.setText(model.getName());
        holder.db.setText(model.getDob());
        holder.dd.setText(model.getDod());
    }

    @NonNull
    @Override
    public MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item,viewGroup,false);
        return new MyAdapterHolder(v);
    }

    public class MyAdapterHolder extends RecyclerView.ViewHolder{
        TextView n,dd,db;
    public MyAdapterHolder(@NonNull View itemView) {
        super(itemView);
        n = (TextView)itemView.findViewById(R.id.textView15);
        dd= (TextView)itemView.findViewById(R.id.textView16);
        db= (TextView)itemView.findViewById(R.id.textView17);
    }
    }
}
