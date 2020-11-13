package com.example.contacts_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditContact extends AppCompatActivity {

    private ImageView editedImage;
    private EditText editedName;
    private EditText editedPhone;
    private Button saveEdited;

    static final int REQUEST_CODE_EDITING = 300;
    public static final String KEY_FOR_EDITING = "key for editing a contact";

    Uri imageData;
    int posFromMain;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        init();
    }

    private void init() {
        editedImage = findViewById(R.id.ic_edited);
        editedName = findViewById(R.id.et_editedName);
        editedPhone = findViewById(R.id.et_editedPhone);
        saveEdited = findViewById(R.id.btn_Edited);


        Intent dataFromMain = getIntent();
        position = dataFromMain.getIntExtra(MainActivity.KEY_FOR_EDITING, posFromMain);
        editedImage.setImageURI(MainActivity.list.get(position).imageURI);
        editedName.setText(MainActivity.list.get(position).title);
        editedPhone.setText(MainActivity.list.get(position).number);
        if (MainActivity.list.get(position).imageURI != null)
            imageData = MainActivity.list.get(position).imageURI;


        editedImage.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "choose an image"), REQUEST_CODE_EDITING);

        });

        saveEdited.setOnClickListener(view -> {
            String titleName = editedName.getText().toString();
            String titlePhone = editedPhone.getText().toString();
           // TitleModel model = new TitleModel();
            MainActivity.list.get(position).setTitle(titleName);
            MainActivity.list.get(position).setNumber(titlePhone);
            dataFromMain.setData(imageData);
            dataFromMain.putExtra(KEY_FOR_EDITING, MainActivity.list.get(position));
            setResult(RESULT_OK, new Intent());
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDITING && resultCode == RESULT_OK) {
            if (data != null) {
                imageData = data.getData();
                editedImage.setImageURI(imageData);
                MainActivity.list.get(position).setImageURI(imageData);
                getContentResolver().takePersistableUriPermission(imageData, data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

        }
    }
}