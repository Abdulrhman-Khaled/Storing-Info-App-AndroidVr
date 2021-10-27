package com.bodykh.Milestone1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UpdateContact extends AppCompatActivity {

    DbContact db;

    EditText editName, editPhone;
    Button btnUpdate;
    ImageButton pickImag;

    byte[] image = null;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        id = getIntent().getIntExtra("id", 0);
        db = new DbContact(this);
        Contact contact = db.getContactById2(id);


        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        pickImag = (ImageButton) findViewById(R.id.pickImg);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        editName.setText(contact.getName());
        editPhone.setText(contact.getPhone() + "");

        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getImage(), 0, contact.getImage().length);
        pickImag.setImageBitmap(bitmap);
        image = getBytes(bitmap);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.getText().toString().trim().length() == 0) {
                    editName.setError("this field can't be empty");
                    return;
                } else if (editPhone.getText().toString().trim().length() != 14) {
                    editPhone.setError("this field can't be empty and must contain 14 number");
                    return;
                } else if (editName.getText().toString().trim().length() > 0 && editPhone.getText().toString().trim().length() > 0) {
                    String name = editName.getText().toString();
                    long phone = Long.parseLong(editPhone.getText().toString());
                    BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    image = getBytes(bitmap);
                    Contact newContact = new Contact(id, name, phone, image);
                    db.updateContact(newContact);
                    Toast.makeText(UpdateContact.this, "Person is updated !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delete_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_delet:
                showAlert();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showAlert() {

        AlertDialog.Builder alertBilder = new AlertDialog.Builder(this);
        alertBilder.setTitle("Confirmation")
                .setMessage("Are you sure that you want to delete this person?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletContact(id);
                        Toast.makeText(UpdateContact.this, "Person is deleted !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = alertBilder.create();
        dialog.show();
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
