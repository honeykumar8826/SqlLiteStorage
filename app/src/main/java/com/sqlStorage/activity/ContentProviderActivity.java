package com.sqlStorage.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sqlStorage.R;
import com.sqlStorage.adapter.ContactAdapter;
import com.sqlStorage.modal.ContactListModal;
import com.sqlStorage.modal.NameAndIdModal;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    //    private int hasPhoneNumber;
    private final List<NameAndIdModal> nameAndIdModalsList = new ArrayList<>();
    private final StringBuilder stringBuilder = new StringBuilder();
    /*initialize the list*/
    List<ContactListModal> contactLists = new ArrayList<>();
    /*    private String[] mColumnProjection = {
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER};*/
    private RecyclerView contactRecyclerView;
    private ProgressBar progressBar;
    private final String[] permissionList = {Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        /*initialize the id*/
        inItId();
//        check permission is enable for access the contact
       // checkPermission();
//        call the async class
        new ContactSync().execute();
        /*set layout on recyclerView*/
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void inItId() {
        contactRecyclerView = findViewById(R.id.contact_recycler);
        progressBar = findViewById(R.id.progressBar);
    }

    private void disableInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //    inner async class to sync contact
    private class ContactSync extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            disableInteraction();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor1 = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, "OrderBy Desc");
            Log.i(TAG, "onCreate: " + cursor1);
            if (cursor1 != null) {
                String contactNumber = "";
                while (cursor1.moveToNext()) {
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    Log.i(TAG, "onCreate: " + contactId);
                    String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    // int hasPhoneNumber = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                    NameAndIdModal nameAndIdModal = new NameAndIdModal(contactId, contactName);
                    nameAndIdModalsList.add(nameAndIdModal);
                    //   list.add(hasPhoneNumber);
                }
                for (int i = 0; i < nameAndIdModalsList.size(); i++) {
                    Cursor cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " -?", new String[]{nameAndIdModalsList.get(i).getId()}, null);

                    while (cursorPhone.moveToNext()) {
                        contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "onCreate:" + i + "num" + contactNumber);
                        stringBuilder.append(contactNumber);


                    }
                    ContactListModal contactListModal = new ContactListModal(nameAndIdModalsList.get(i).getName(), contactNumber);
                    contactLists.add(contactListModal);


                }

                Log.i(TAG, "onCreate:inside phone cursor " + stringBuilder);

            } else {
                Toast.makeText(getApplicationContext(), "inside else", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            ContactAdapter contactAdapter = new ContactAdapter(contactLists);
            contactRecyclerView.setAdapter(contactAdapter);
            enableInteraction();
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        int count = 0;
        if (context != null && permissions != null) {
            Log.i(TAG, "hasPermissions: " + permissions.length);
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    count++;
                    Log.i(TAG, "hasPermissions: " + count);
                    return false;
                }
            }
        }
        return true;
    }
    private void checkPermission()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (!hasPermissions(this, permissionList)) {
//            Log.i(TAG, "checkPermission: " + count++);
                ActivityCompat.requestPermissions(this, permissionList, 10);
            } else {
                Toast.makeText(this, " Permission  granted ", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "permission automatically granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10) {
            Log.i(TAG, "onRequestPermissionsResult: " + permissions);
            if (grantResults[0] == -1) {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    Log.i(TAG, "shouldShowRequestPermissionRationale:");
                    showMessageOkCancel("Location permission is required to access location",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    // Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Read Contact Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
            }
        }
    private void showMessageOkCancel(String permissionDetail, DialogInterface.OnClickListener onClickListener) {

        new AlertDialog.Builder(this).setMessage(permissionDetail)
                .setPositiveButton("Ok", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}


