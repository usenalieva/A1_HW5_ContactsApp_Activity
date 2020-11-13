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


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
    public static final String KEY_FOR_EDITING = "key for editing a contact";
    static Uri imageData;

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

       adapter.setOnContactClickListener(pos1 -> {
           Intent intent = new Intent(MainActivity.this, EditContact.class);
           intent.putExtra(KEY_FOR_EDITING, pos1);
           startActivityForResult(intent, REQUEST_CODE_EDIT);

       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            //imageData = data.getData();
            //TitleModel titleModel = (TitleModel) data.getSerializableExtra(EditContact.KEY_FOR_EDITING);
            //titleModel.setImageURI(imageData);
            //adapter.editContact(titleModel,pos);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            TitleModel titleModel = (TitleModel) data.getSerializableExtra(ApplicationActivity.KEY_FOR_ADDING);
            titleModel.setImageURI(imageData);
            adapter.addApplication(titleModel);
        }
    }
}
