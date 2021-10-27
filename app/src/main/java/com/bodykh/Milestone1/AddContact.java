package com.bodykh.Milestone1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddContact extends AppCompatActivity {

    EditText editName, editPhone;
    Button btnConfirm;
    ImageButton pickImag;
    byte[] image = null;
    DbContact db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = new DbContact(this);


        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        pickImag = (ImageButton) findViewById(R.id.pickImg);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.getText().toString().trim().length() == 0) {
                    editName.setError("this field can't be empty");
                    return;
                } else if (editPhone.getText().toString().trim().length() != 14) {
                    editPhone.setError("this field can't be empty and must contain 14 number");
                    return;
                } else if (editName.getText().toString().trim().length() > 0 && editPhone.getText().toString().trim().length() > 0) {
                    BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    image = getBytes(bitmap);
                    String name = editName.getText().toString();
                    long id = Long.parseLong(editPhone.getText().toString());
                    Contact contact = new Contact(name, id, image);
                    db.addContact(contact);
                    Toast.makeText(AddContact.this, "Person is added !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }


    public void openGalleries(View view) {

        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                pickImag.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);

            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }

        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


}
