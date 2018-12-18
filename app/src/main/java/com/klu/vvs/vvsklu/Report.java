package com.klu.vvs.vvsklu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    ListView mAuditList;
    List<AuditList> lst;
    TextView Surname,Name,Mobile,Email,tv;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Surname=(TextView)findViewById(R.id.textView);
        Name=(TextView)findViewById(R.id.textView3);
        Mobile=(TextView)findViewById(R.id.textView4);
        Email=(TextView)findViewById(R.id.textView5);
        tv=(TextView)findViewById(R.id.textView6);
        photo=(ImageView)findViewById(R.id.imageView);

        mAuditList = findViewById(R.id.AuditList);

        lst = new ArrayList<AuditList>();
        lst.clear();

        lst.add(new AuditList("audit 1","21-10-18","80%","10,000","10th"));
        lst.add(new AuditList("audit 2","21-10-18","80%","10,000","10th"));
        lst.add(new AuditList("audit 3","21-10-18","80%","10,000","10th"));
        lst.add(new AuditList("audit 4","21-10-18","80%","10,000","10th"));
        lst.add(new AuditList("audit 5","21-10-18","80%","10,000","10th"));
        lst.add(new AuditList("audit 6","21-10-18","80%","10,000","10th"));


        CustomAdapter a = new CustomAdapter(lst,getApplicationContext());
        mAuditList.setAdapter(a);


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);

    }
}

