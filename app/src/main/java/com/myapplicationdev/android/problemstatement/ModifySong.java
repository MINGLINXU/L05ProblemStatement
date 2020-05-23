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
        int star = data.getStars();
        rg = (RadioGroup) findViewById(R.id.rg);

        if (star == 1){
            rg.check(R.id.radioButton1);
        } else if (star == 2){
            rg.check(R.id.radioButton2);
        } else if (star == 3){
            rg.check(R.id.radioButton3);
        } else if (star == 4){
            rg.check(R.id.radioButton4);
        }else if (star >= 5){
            rg.check(R.id.radioButton5);
        }



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setStars(getStars());
                dbh.updateSong(data);
                dbh.close();
                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                dbh.deleteNote(data.get_id());
                dbh.close();

                Intent i = new Intent();
                i.putExtra("data", data);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private int getStars() {
        int stars = 1;
        switch (rg.getCheckedRadioButtonId()) {
            case R.id.radioButton1:
                stars = 1;
                break;
            case R.id.radioButton2:
                stars = 2;
                break;
            case R.id.radioButton3:
                stars = 3;
                break;
            case R.id.radioButton4:
                stars = 4;
                break;
            case R.id.radioButton5:
                stars = 5;
                break;
        }
        return stars;
    }
}
