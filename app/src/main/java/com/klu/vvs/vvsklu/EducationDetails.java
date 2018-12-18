package com.klu.vvs.vvsklu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EducationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_details);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,ParentDetails.class);
        startActivity(i);
    }

    public void gotoNext(View view) {
        Intent i=new Intent(this,CertificationDetails.class);
        startActivity(i);

    }
}
