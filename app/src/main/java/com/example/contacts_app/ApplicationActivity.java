package com.example.contacts_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ApplicationActivity extends AppCompatActivity {

    private ImageView image;
    private EditText etName;
    private EditText etPhone;
    private Button btnSave;

    static final int REQUEST_CODE_ADDING = 200;
    public static final String KEY_FOR_ADDING = "key for  adding a contact";

    Uri imageData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        init();
    }

    private void init() {
        image = findViewById(R.id.iconAdd);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.btn_save);

        image.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "choose an image"), REQUEST_CODE_ADDING);

        });

        btnSave.setOnClickListener(view -> {
            String titleName = etName.getText().toString();
            String titlePhone = etPhone.getText().toString();
            String image = imageData.toString();
            Intent intent = new Intent();
            TitleModel model = new TitleModel();
            model.setTitle(titleName);
            model.setNumber(titlePhone);
            model.setImageView(image);
            intent.setData(imageData);
            intent.putExtra(KEY_FOR_ADDING, model);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADDING && resultCode == RESULT_OK) {
            if (data != null) {
                imageData = data.getData();
                image.setImageURI(imageData);
                getContentResolver().takePersistableUriPermission(imageData, data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

        }
    }
}