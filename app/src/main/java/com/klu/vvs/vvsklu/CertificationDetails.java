package com.klu.vvs.vvsklu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CertificationDetails extends AppCompatActivity {

    Uri nikki,krish,bav;

    // Button bPic,uploadPic,bCert,uploadCert,bSign,uploadSign;
    ImageView picImage,certImage,signImage;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_details);
        b=(Button)findViewById(R.id.btnSelectPhoto);
        signImage = findViewById(R.id.imageViewSign);
        picImage  = findViewById(R.id.imageViewPic);
        certImage = findViewById(R.id.imageViewCert);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }




    private void selectImage() {

        final CharSequence[] options = { "Select Photograph","Select Certificate","Select Signature","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CertificationDetails.this);
        builder.setTitle("Select from the options below");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Select Photograph"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

                }
                else if (options[item].equals("Select Certificate"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Select Signature"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 3);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri nikki = data.getData();
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(nikki);
                    Bitmap image = (BitmapFactory.decodeStream(inputStream));
                    picImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "can't place image", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == 2) {
                krish = data.getData();
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(krish);
                    Bitmap image = (BitmapFactory.decodeStream(inputStream));
                    certImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "can't place image", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == 3) {
                bav = data.getData();
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(bav);
                    Bitmap image = (BitmapFactory.decodeStream(inputStream));
                    signImage.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "can't place image", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,EducationDetails.class);
        startActivity(i);
    }

    public void onSubmit(View view) {
        Toast.makeText(this, "Submitted Successfully!!VVS Foundation will contact you soon!!", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
