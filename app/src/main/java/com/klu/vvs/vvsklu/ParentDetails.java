package com.klu.vvs.vvsklu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ParentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_details);
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,ApplyActivity.class);
        startActivity(i);

    }

    public void gotoNext(View view) {
        Intent i=new Intent(this,EducationDetails.class);
        startActivity(i);
    }
}
