package com.klu.vvs.vvsklu;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.klu.vvs.vvsklu.LoginActivity;
import com.klu.vvs.vvsklu.R;
import com.klu.vvs.vvsklu.RegisterFormValues;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ApplyActivity extends AppCompatActivity {


    long ApplicationCount;
    RadioGroup rg;
    RegisterFormValues rv;
    TextView ApplyID,tv2,course;
    DatePicker picker;
    EditText sname,fname,religi,phoneno,caste,emailid,nation,statet,address,aadhar;  // Personal Details EditTexts
    EditText annual,faName,fSurName,fMobile,fEmail,fEdu,fOccu,mName,mSurname,mMobile,mEmail,mEdu,mOccu;//ParentsDetails
    EditText et1,et3,et4,et6,courseET;    //Educational Details EditTexts
    ImageView certi;//CertificateDetails
    DatabaseReference db;
    StorageReference picReference,certReference,signReference;
    Uri nikki;
    StorageReference ref;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        db= FirebaseDatabase.getInstance().getReference("VVSFOUNDATION");


        ApplyID=findViewById(R.id.applicationId);
        sname=findViewById(R.id.surname);
        fname=findViewById(R.id.name);
        religi=findViewById(R.id.religion);
        phoneno=findViewById(R.id.mobileno);
        caste=findViewById(R.id.caste);
        emailid=findViewById(R.id.emailid);
        nation=findViewById(R.id.nationality);
        statet=findViewById(R.id.state);
        address=findViewById(R.id.address);
        aadhar=findViewById(R.id.aadharno);
        picker=findViewById(R.id.birth);

        et1=(EditText)findViewById(R.id.et1);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        et6=(EditText)findViewById(R.id.et6);
        tv2=findViewById(R.id.tv2);
        course=findViewById(R.id.higherTV);
        courseET=findViewById(R.id.higherEdit);
        rg=findViewById(R.id.typeRg);

        tv2.setVisibility(View.GONE);
        courseET.setVisibility(View.GONE);
        course.setVisibility(View.GONE);

        annual=findViewById(R.id.annuincome);
        faName=findViewById(R.id.fname);
        fSurName=findViewById(R.id.fsurname);
        fMobile=findViewById(R.id.fmobile);
        fEmail=findViewById(R.id.femail);
        fEdu=findViewById(R.id.fedu);
        fOccu=findViewById(R.id.foccu);
        mName=findViewById(R.id.mname);
        mSurname=findViewById(R.id.msurname);
        mMobile=findViewById(R.id.mmobile);
        mEmail=findViewById(R.id.memail);
        mEdu=findViewById(R.id.medu);
        mOccu=findViewById(R.id.moccu);

        certi=findViewById(R.id.image);  //CertificateDetails

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.school:
                        tv2.setVisibility(View.VISIBLE);
                        course.setVisibility(View.GONE);
                        courseET.setVisibility(View.VISIBLE);
                        break;
                    case R.id.college:
                        tv2.setVisibility(View.GONE);
                        course.setVisibility(View.VISIBLE);
                        courseET.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }

    //image storing code
    public void select(View view){

        //  Intent intent = new Intent();
        // intent.setType("image/*");
        // intent.setAction(Intent.ACTION_PICK);
        //startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                nikki = data.getData();
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(nikki);
                    Bitmap image = (BitmapFactory.decodeStream(inputStream));
                    certi.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "can't place image", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }


    public void upload() {
        try {

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int n = Integer.parseInt(dataSnapshot.child("SupportersCount").getValue().toString()) + 1;
                    picReference = ref.child("supporter"+n+"/supporter" + n + "Photograph.jpg");
                    if (nikki != null) {
                        picReference.putFile(nikki)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progress.setProgress(0);
                                            }
                                        }, 500);

                                        Toast.makeText(ApplyActivity.this, "Photograph Upload successful", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ApplyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress1 = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progress.setProgress((int) progress1);
                                    }
                                });
                    } else {
                        Toast.makeText(ApplyActivity.this, "No file selected", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(ApplyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Do you want to exit? You will loose all the data!!")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(ApplyActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }


    public void onSubmit(View view) {

        String surName,firstName,religion,phoneNumber,casteOfStudent,emailIdOfStudent,nationality,stateOfStudent,addressOfStudent,aadharNo,dateOfBirth,
                annualIncome,fatherName,fatherSurName,fatherMobile,fatherEmail,fatherEdu,fatherOccu,
                motherName,motherSurname,motherMobile,motherEmail,motherEdu,motherOccu,
                placeOfStudy,standardOfStudy,nameOfTheInstitute,percentageOrCGPA,collegeOrSchoolID;
        //Add CertificateDetails

        surName=sname.getText().toString()+" ";
        firstName=fname.getText().toString()+" ";
        religion=religi.getText().toString()+" ";
        phoneNumber=phoneno.getText().toString()+" ";
        casteOfStudent=caste.getText().toString()+" ";
        emailIdOfStudent=emailid.getText().toString()+" ";
        nationality=nation.getText().toString()+" ";
        stateOfStudent=statet.getText().toString()+" ";
        addressOfStudent=address.getText().toString()+" ";
        aadharNo=aadhar.getText().toString()+" ";
        dateOfBirth=picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear()+" ";

        annualIncome=annual.getText().toString()+" ";
        fatherName=faName.getText().toString()+" ";
        fatherSurName=fSurName.getText().toString()+" ";
        fatherMobile=fMobile.getText().toString()+" ";
        fatherEmail=fEmail.getText().toString()+" ";
        fatherEdu=fEdu.getText().toString()+" ";
        fatherOccu=fOccu.getText().toString()+" ";

        motherName=mName.getText().toString()+" ";
        motherSurname=mSurname.getText().toString()+" ";
        motherMobile=mMobile.getText().toString()+" ";
        motherEmail=mEmail.getText().toString()+" ";
        motherEdu=mEdu.getText().toString()+" ";
        motherOccu=mOccu.getText().toString()+" ";


        placeOfStudy=et1.getText().toString()+" ";
        standardOfStudy=courseET.getText().toString()+" ";
        nameOfTheInstitute=et3.getText().toString()+" ";
        percentageOrCGPA=et4.getText().toString()+" ";
        collegeOrSchoolID=et6.getText().toString()+" ";


        rv=new RegisterFormValues(surName,firstName,religion,phoneNumber,casteOfStudent,emailIdOfStudent,nationality,stateOfStudent,addressOfStudent,aadharNo,
                dateOfBirth,annualIncome,fatherName,fatherSurName,fatherMobile,fatherEmail,fatherEdu,fatherOccu,
                motherName,motherSurname,motherMobile,motherEmail,motherEdu,motherOccu,
                placeOfStudy,standardOfStudy,nameOfTheInstitute,percentageOrCGPA,collegeOrSchoolID);

        db.child("Applications").child(aadharNo).setValue(rv);
        db.child("CurrentApplyCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ApplicationCount=Long.parseLong(dataSnapshot.getValue().toString());
                db.child("CurrentApplyCount").setValue(++ApplicationCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        new AlertDialog.Builder(this)
                .setTitle("Successfully Registered!!")
                .setMessage("Take a note of APPLICATION ID for further interactions "+aadharNo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(ApplyActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

    }

}