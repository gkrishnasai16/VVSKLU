package com.klu.vvs.vvsklu;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.FILL;

public class AdminActivity extends AppCompatActivity {

    ListView appliList;
    DatabaseReference db;
    TextView appliCount;
    List<String> appliHeadings;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db=FirebaseDatabase.getInstance().getReference("VVSFOUNDATION");

        appliCount=findViewById(R.id.Emp_noOfApplications);
        appliList=findViewById(R.id.appliListView);

        appliHeadings=new ArrayList<>();

        db.child("Applications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                appliHeadings.clear();
                for(DataSnapshot i: dataSnapshot.getChildren() )
                {
                    String s = i.child("aadharNo").getValue().toString();
                    appliHeadings.add(s);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AdminActivity.this,android.R.layout.simple_list_item_1,appliHeadings);
                appliList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db.child("CurrentApplyCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appliCount.setText("Count of Applications : "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //ADD CODE FOR COMPARING APPLICATION LIST AND SHORTLISTED LIST

        appliList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(AdminActivity.this,SeeApplication.class);
                intent.putExtra("aadhar",appliHeadings.get(position));
                startActivity(intent);
            }
        });
    }

    public void goBack(View view) {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }


}
