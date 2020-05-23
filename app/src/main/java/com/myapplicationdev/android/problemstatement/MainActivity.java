package com.myapplicationdev.android.problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSinger, etYear;
    RadioGroup rg;
    RadioButton rb;
    Button btnInsert, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("P05-NDPSongs ~ Insert Song");

        etTitle = findViewById(R.id.etSongTitle);
        etSinger = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rg = findViewById(R.id.rg);
        btnInsert = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedButtonId = rg.getCheckedRadioButtonId();
                rb = findViewById(selectedButtonId);
                int starts = Integer.parseInt(rb.getText().toString());

                DBHelper db = new DBHelper(MainActivity.this);

                db.insertSong(etTitle.getText().toString(), etSinger.getText().toString(), Integer.parseInt(etYear.getText().toString()), starts);
                db.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowSong.class);
                startActivity(intent);
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
