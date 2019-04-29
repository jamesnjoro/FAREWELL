package com.example.groundzero.farewell;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static java.lang.System.currentTimeMillis;


public class deceased extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference store, storeget;
    ImageView view,view2;
    Uri uri;
    postI p;
    Button pic, save, share;
    DocumentReference ref;
    FloatingActionButton memorialmake,delete;
    EditText name,age,date,description,eulogy;
    String dpath;
    FirebaseFirestore  dbs;
    FirebaseStorage storages;
    int REQUEST_WRITE_STORAGE = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        p = getArguments().getParcelable("orbi");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        View rootView = inflater.inflate(R.layout.deceased, container, false);
        name = rootView.findViewById(R.id.eName);
        name.setText(p.getName());
        share = rootView.findViewById(R.id.share);
        age = rootView.findViewById(R.id.eAge);
        age.setText(p.getAge());
        delete = rootView.findViewById(R.id.floating2);
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
        memorialmake = rootView.findViewById(R.id.memorialmake);
        if(p.getUser().contentEquals(user.getEmail()) ){
            delete.show();
            pic.setVisibility(View.VISIBLE);
            memorialmake.show();
            save.setVisibility(View.VISIBLE);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = Screenshot.takeScreenShotRootView(view);
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);


                boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if(hasPermission){

                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "obituaryPost.jpg");
                    try {
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    share.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:///sdcard/obituaryPost.jpg"));
                    startActivity(Intent.createChooser(share,"Share post"));

                }else{
                    // ask the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                    // You have to put nothing here (you can't write here since you don't
                    // have the permission yet and requestPermissions is called asynchronously)
                }








            }
        });

        db =FirebaseFirestore.getInstance();
        storage= FirebaseStorage.getInstance();
        ref = db.document("obituaries/"+p.getName());
        final ProgressBar progressBar;
        final ProgressBar progressBar1;
        Circle Circle;
        progressBar1 = rootView.findViewById(R.id.spin_kit3);
        Circle = new Circle();
        progressBar1.setIndeterminateDrawable(Circle);



            storeget = storage.getReference("deceased_pics/" + p.getPhoto());
            GlideApp.with(getActivity())
                    .load(storeget)
                    .placeholder(R.drawable.noimage)
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.delete();
                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);
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

        memorialmake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbs = FirebaseFirestore.getInstance();
                storages = FirebaseStorage.getInstance();
                Memorial me = new Memorial(p.getName(), p.getUser(), p.getDescription(), p.getEulogy(),p.getPhoto(),p.getDob(), p.getDod());
                dbs.collection("memorials")
                        .document(p.getName())
                        .set(me)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "memorial created", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "memorial creation Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // The result of the popup opened with the requestPermissions() method
        // is in that method, you need to check that your application comes here
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // write
            }
        }
    }




}
