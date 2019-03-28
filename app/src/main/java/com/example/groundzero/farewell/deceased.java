package com.example.groundzero.farewell;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;
import static java.lang.System.currentTimeMillis;


public class deceased extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference store, storeget;
    ImageView view,view2;
    Uri uri;
    postI p;
    Button pic, save;
    EditText name,age,date,description,eulogy;
    String dpath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        p = getArguments().getParcelable("orbi");
        View rootView = inflater.inflate(R.layout.deceased, container, false);
        name = rootView.findViewById(R.id.eName);
        name.setText(p.getName());
        age = rootView.findViewById(R.id.eAge);
        age.setText(p.getAge());
        date = rootView.findViewById(R.id.eDateDeath);
        date.setText(p.getDob());
        description = rootView.findViewById(R.id.eDescription);
        description.setText(p.getDescription());
        eulogy = rootView.findViewById(R.id.eEulogy);
        eulogy.setText(p.getEulogy());
        pic = rootView.findViewById(R.id.decibutton);
        view = rootView.findViewById(R.id.decimage);
        view2 = rootView.findViewById(R.id.decimage2);
        save = rootView.findViewById(R.id.save);
        db =FirebaseFirestore.getInstance();
        storage= FirebaseStorage.getInstance();
        final ProgressBar progressBar;
        final ProgressBar progressBar1;
        Circle Circle;
        progressBar = rootView.findViewById(R.id.spin_kit2);
        progressBar1 = rootView.findViewById(R.id.spin_kit3);
        Circle = new Circle();
        progressBar.setIndeterminateDrawable(Circle);
        progressBar1.setIndeterminateDrawable(Circle);



            storeget = storage.getReference("deceased_pics/" + p.getPhoto());
            GlideApp.with(getActivity())
                    .load(storeget)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.noimage)
                    .into(view);


            store= storage.getReference("deceased_pics");
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar1.setVisibility(View.VISIBLE);
                save.setVisibility(View.INVISIBLE);
                if(uri != null){
                    dpath = String.valueOf(currentTimeMillis()) +"." + getFileExtension(uri);
                    final  StorageReference file = store.child(dpath);
                    file.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity(), "succesfully uploaded", Toast.LENGTH_SHORT).show();
                                    p.setPhoto(dpath);

                                    db.collection("obituaries")
                                            .document(p.getName())
                                            .set(p)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getActivity(), "User created succesfully", Toast.LENGTH_SHORT).show();
                                                    progressBar1.setVisibility(View.GONE);
                                                    save.setVisibility(View.VISIBLE);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(), "\"User created unsuccesfully\" + e", Toast.LENGTH_SHORT).show();
                                                    progressBar1.setVisibility(View.GONE);
                                                    save.setVisibility(View.VISIBLE);
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "unsucessfully uploaded", Toast.LENGTH_SHORT).show();
                                    progressBar1.setVisibility(View.GONE);
                                    save.setVisibility(View.VISIBLE);
                                }
                            });
                }else{
                    Toast.makeText(getActivity(), "No Photo Selected", Toast.LENGTH_SHORT).show();
                    progressBar1.setVisibility(View.GONE);
                    save.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK){
            uri = data.getData();
            view.setVisibility(View.INVISIBLE);
            view2.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


}
