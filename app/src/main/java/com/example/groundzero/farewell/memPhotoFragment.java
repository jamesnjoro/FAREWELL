package com.example.groundzero.farewell;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static java.lang.System.currentTimeMillis;

public class memPhotoFragment extends Fragment {
    private TextView click;
    Uri uri;
    Dialog dialog;
    ImageView view;
    Button save, cancel;
    photo photo;
    Memorial m;
    FirebaseAuth auth;
    FirebaseUser currentuser;
    String dpath,dat,desc;
    Date date = new Date();
    EditText description;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference store;
    DateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
    RecyclerView recyclerView;
    photoAdapter PhotoAdapter;
    Query phot;
    List<photo> mphotos = new ArrayList<>();
    CollectionReference collect;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m = getArguments().getParcelable("mem");
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentuser = auth.getCurrentUser();
        View rootView = inflater.inflate(R.layout.photo_layout, container, false);
        recyclerView = rootView.findViewById(R.id.photoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        collect = db.collection("photos");
        phot =collect.whereEqualTo("deceased",m.getName());

        phot.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot snap: task.getResult()){
                            photo p = snap.toObject(photo.class);
                            mphotos.add(p);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        Context con = getActivity();
        PhotoAdapter = new photoAdapter(con,mphotos);
        recyclerView.setAdapter(PhotoAdapter);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.photopopup);
        click = rootView.findViewById(R.id.tup);
        save = dialog.findViewById(R.id.photosave);
        cancel = dialog.findViewById(R.id.photocancel);
        view = dialog.findViewById(R.id.poppic);
        description = dialog.findViewById(R.id.photodescription);
        store= storage.getReference("photos");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri!=null){
                    dat = sdf.format(date);
                    desc = description.getText().toString();
                    dpath = String.valueOf(currentTimeMillis()) +"." + getFileExtension(uri);
                }
            photo = new photo(m.getName(),currentuser.getEmail(),dpath,dat,desc);
                db.collection("photos")
                        .document(dpath)
                        .set(photo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                final  StorageReference file = store.child(dpath);
                                file.putFile(uri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(getActivity(), "Photo uploaded", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Photo upload failed", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK){
            dialog.show();
            uri = data.getData();
            view.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

}
