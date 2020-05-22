package com.myapplicationdev.android.problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModifySong extends AppCompatActivity {

    TextView tvID;
    EditText etTitle, etSinger, etYear;
    RadioGroup rg;
    RadioButton rb;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    int rbCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);
        setTitle("P05-NDPSongs ~ Modify Song");

        tvID = findViewById(R.id.tv_showID);
        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.rg);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);


        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        tvID.setText(String.valueOf(data.get_id()));
        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText( String.valueOf(data.getYear()));
        rbCheck = data.getStars();
        RadioButton rb = findViewById(rbCheck);
        String rgChoice = rb.getText().toString();
        if(rgChoice.equals(rb)){
            rb.setChecked(true);
        }



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                dbh.updateSong(data);
                dbh.close();
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                dbh.deleteNote(data.get_id());
                dbh.close();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowSong.class);
                startActivity(intent);
            }
        });
    }
}
