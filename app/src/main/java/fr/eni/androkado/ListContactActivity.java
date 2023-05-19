package fr.eni.androkado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eni.androkado.adapters.ContactAdapter;
import fr.eni.androkado.bo.Friend;

public class ListContactActivity extends AppCompatActivity {

    private static final String TAG = "ENI";
    //Objet représentant le recyclerView
    private RecyclerView mRecyclerView;
    //Objet représentant l"adapter remplissant le recyclerView
    private RecyclerView.Adapter mAdapter;
    //Objet permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
    private RecyclerView.LayoutManager mLayoutManager;
    //Liste bouchon
    private ArrayList<Friend> contacts = new ArrayList<>();
    //Contact sélectionné
    Friend selectedContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS}, 14540);

        mRecyclerView = findViewById(R.id.rv_list_contact);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"go onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 14540: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED && grantResults[1] ==
                        PackageManager.PERMISSION_GRANTED)
                {
                    chargementDonnees();
                    chargementRecycler();
                }

                break;
            }
            case 2525: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED && grantResults[1] ==
                        PackageManager.PERMISSION_GRANTED)
                {
                    envoieSMS();
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            selectedContact = contacts.get(position);
            ActivityCompat.requestPermissions(ListContactActivity.this, new
                    String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.READ_PHONE_STATE}, 2525);

        }
    };

    /**
     * Permet de charger le recycler view
     */
    private void chargementRecycler() {
        //Lie le recyclerView aux fonctionnalité offerte par le linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Liaison permettant de structurer l'ensemble des sous vues contenues dans le RecyclerView.
        mAdapter = new ContactAdapter(contacts, itemClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("Range")
    private void chargementDonnees() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            @SuppressLint("Range") String name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            @SuppressLint("Range") Integer hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            // get the user's phone number
            String phone = null;
            if (hasPhone > 0) {
                Cursor cp = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                if (cp != null && cp.moveToFirst()) {
                    phone = cp.getString(cp.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    cp.close();
                }
            }

            Friend f = new Friend();
            f.setId(Integer.parseInt(id));
            f.setNom(name);
            f.setNumber(phone);

            Log.i(TAG, f.toString());
            contacts.add(f);
        }
    }

    private void envoieSMS() {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(selectedContact.getNumber(), null, "Bonjour "+selectedContact.getNom(), null, null);
        Toast.makeText(this, "SMS Envoyé", Toast.LENGTH_SHORT).show();
    }

}