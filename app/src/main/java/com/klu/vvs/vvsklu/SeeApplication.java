package com.klu.vvs.vvsklu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeeApplication extends AppCompatActivity {


    DatabaseReference dba,dbs;
    RadioGroup rg;
    int selectOrReject=1;
    TextView appliNumber,name;
    AdminActivity aa;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_application);

        dba=FirebaseDatabase.getInstance().getReference("VVSFOUNDATION");
        dbs=FirebaseDatabase.getInstance().getReference("VVSSHORTLISTED");

        aa=new AdminActivity();

        rg=findViewById(R.id.selectOrReject);
        name=findViewById(R.id.name);
        appliNumber=findViewById(R.id.displayApplicationNo);
        profilePic=findViewById(R.id.profilePicture);


        final String appli=getIntent().getStringExtra("aadhar");
        appliNumber.setText("Application No: "+appli);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.selected:
                        selectOrReject=1;
                        dbs.child("Shortlist").setValue(appli);
                        dbs.child("CurrentShortListCount").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dbs.child("CurrentShortListCount").setValue(Long.toString(Long.parseLong(dataSnapshot.getValue().toString())+1));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case R.id.rejected:
                        selectOrReject=0;
                        //if already selected previously and wants to reject now........ have to find a previously selected or not
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,AdminActivity.class);
        startActivity(i);
    }
}
