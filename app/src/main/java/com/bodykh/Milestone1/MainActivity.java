package com.bodykh.Milestone1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView contactList;
    Button btnAdd;
    DbContact db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbContact(this);
        contactList = (ListView) findViewById(R.id.contactList);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);

            }
        });


        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selected_contact = (Contact) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, UpdateContact.class);

                intent.putExtra("id", selected_contact.getId());

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Contact> contacts = db.getAllContacts();

        ContactAdapter contactAdapter = new ContactAdapter(this, R.layout.item_contact, contacts);

        contactList.setAdapter(contactAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filters_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Contact> filterList = new ArrayList<>();
                ArrayList<Contact> contacts = db.getAllContacts();
                for (Contact temp : contacts) {
                    if (temp.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(temp);
                    } else {
                        long phone = temp.getPhone();
                        String phones = Long.toString(phone);
                        if (phones.toLowerCase().contains(newText.toLowerCase())) {
                            filterList.add(temp);
                        }
                    }
                }
                ContactAdapter contactAdapter = new ContactAdapter(getApplicationContext(), R.layout.item_contact, filterList);
                contactList.setAdapter(contactAdapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void sortFun(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sorting");
        String[] items = {"Turn Off Sorting", "Turn On Sorting From A to Z", "Turn On Sorting From Z to A"};
        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        unsortList();
                        dialog.dismiss();
                        break;
                    case 1:
                        sortListAtoZ();
                        dialog.dismiss();
                        break;
                    case 2:
                        sortListZtoA();
                        dialog.dismiss();
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void unsortList() {
        ArrayList<Contact> contacts = db.getAllContacts();
        ContactAdapter contactAdapter = new ContactAdapter(this, R.layout.item_contact, contacts);
        contactList.setAdapter(contactAdapter);
    }

    public void sortListAtoZ() {
        ArrayList<Contact> contacts = db.getAllContacts();
        Collections.sort(contacts);
        ContactAdapter contactAdapter = new ContactAdapter(this, R.layout.item_contact, contacts);
        contactList.setAdapter(contactAdapter);
    }

    public void sortListZtoA() {
        ArrayList<Contact> contacts = db.getAllContacts();
        Collections.sort(contacts);
        Collections.reverse(contacts);
        ContactAdapter contactAdapter = new ContactAdapter(this, R.layout.item_contact, contacts);
        contactList.setAdapter(contactAdapter);
    }

    public void infoFun(MenuItem item) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Team Members");
        alertDialog.setMessage("1- Abdulrahman Khaled Hassan\n" +
                "2- Mohamed Salah Mohamed\n" +
                "3- Karim Gharib Mohamed\n" +
                "4- Mahmoud Ashraf Mohamed\n" +
                "5- Ahmed Abbas Mohamed\n" +
                "6- Abdulrahman Abdulhalem Mohamed\n" +
                "7- Mo'men Ashraf Mohamed\n" +
                "8- Youssef Wael Elsayed");
        alertDialog.setPositiveButton("Ok", null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
