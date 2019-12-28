package com.gabrieldev525.zfiletransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.gabrieldev525.zfiletransfer.adapter.FTPBase;
import com.gabrieldev525.zfiletransfer.adapter.FtpListAdapter;
import com.gabrieldev525.zfiletransfer.database.Controller;
import com.gabrieldev525.zfiletransfer.database.FTPDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<FTPBase> ftpConnList;
    private ListView ftpConnListView;
    private FloatingActionButton newConnectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the list view
        ftpConnList = new ArrayList<FTPBase>();

        Controller controller = new Controller(getBaseContext());
        Cursor cursor = controller.getConnections();

        // iterate with the cursor data and set it in the list view
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Toast.makeText(getBaseContext(), "Listando", Toast.LENGTH_SHORT).show();

            FTPBase ftpConn = new FTPBase();

            // get the current iterator cursor data to set in the list
            String name = cursor.getString(cursor.getColumnIndex(FTPDB.NAME));
            String host = cursor.getString(cursor.getColumnIndex(FTPDB.HOST));
            int port = cursor.getInt(cursor.getColumnIndex(FTPDB.PORT));

            // set the data in the class to appear in the list
            ftpConn.setHost(host);
            ftpConn.setPort(port);
            ftpConn.setName(name);

            ftpConnList.add(ftpConn);
            cursor.moveToNext();
        }

        // set the adapter with items to list
        ftpConnListView = (ListView) findViewById(R.id.ftp_listview);
        ftpConnListView.setAdapter(new FtpListAdapter(this, ftpConnList));

        // initialize the new connection button
        newConnectionBtn = (FloatingActionButton) findViewById(R.id.newConnectionBtn);
        newConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewConnection.class);
                startActivity(i);
            }
        });
    }
}