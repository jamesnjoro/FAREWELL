package com.example.groundzero.farewell;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;


public class deceased extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference store;
    ImageView view;
    Uri uri;
    postI p;
    Button pic, save;
    EditText name,age,date,description,eulogy;
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
        save = rootView.findViewById(R.id.save);
        db =FirebaseFirestore.getInstance();
        storage= FirebaseStorage.getInstance();
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

            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode==RESULT_OK){
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
