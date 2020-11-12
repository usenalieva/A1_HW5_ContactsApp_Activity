package com.example.contacts_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    static List<TitleModel> list;
    MainAdapter adapter;
    private Button btnAdd;
    int pos;

    static final int REQUEST_CODE_EDIT = 12;
    static final int REQUEST_CODE_ADD = 13;
    static Uri imageData;

    Dialog dialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MainAdapter(list, this);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ApplicationActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);

        });

        adapter.setListener(new OnItemMenuClick() {
            @Override
            public void save(int pos) {
                Toast.makeText(MainActivity.this, pos + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void delete(int pos) {
                list.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        });

        adapter.setOnContactClickListener(pos -> {

            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.edit_contact_window);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            // closing the dialog window
            TextView btnClose = dialog.findViewById(R.id.btn_close);
            btnClose.setOnClickListener(v -> {
                dialog.dismiss();
            });

            // getting the saved data
            imageView = dialog.findViewById(R.id.ic_profilePic);
            EditText nameEdit = dialog.findViewById(R.id.et_nameEdited);
            EditText phoneEdit = dialog.findViewById(R.id.et_phoneEdited);
            nameEdit.setText(list.get(pos).title);
            phoneEdit.setText(list.get(pos).number);
            imageView.setImageURI(list.get(pos).imageURI);

            //reset the image
            imageView.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "choose an image"), REQUEST_CODE_EDIT);
            });

            // saving the edited data
            Button btnSave = dialog.findViewById(R.id.btn_saveEdited);
            btnSave.setOnClickListener(v -> {
                String titleName = nameEdit.getText().toString();
                String titlePhone = phoneEdit.getText().toString();
                String image = imageData.toString();
                TitleModel modelEdited = new TitleModel();
                modelEdited.setTitle(titleName);
                modelEdited.setNumber(titlePhone);
                modelEdited.setImageView(image);
                modelEdited.setImageURI(imageData);
                list.set(pos, modelEdited);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            imageView.setImageURI(imageData);
        }
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            TitleModel titleModel = (TitleModel) data.getSerializableExtra(ApplicationActivity.KEY_FOR_ADDING);
            titleModel.setImageURI(imageData);
            adapter.addApplication(titleModel);
        }
    }
}
