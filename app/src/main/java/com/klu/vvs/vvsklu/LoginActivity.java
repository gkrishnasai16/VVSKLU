package com.klu.vvs.vvsklu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private boolean Flag;

    private Button FacButton,StuButton;

    private int a =1;

    private long NumberOfAdmins , NumberOfClients ;

    private EditText Username , Password ;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference() ;

    private DatabaseReference DataBaseTemp ;

    private DataSnapshot SnapShot ;

    private String username , password ;

    private Users user = new Users() ;

    TextView tlogin;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username =  findViewById(R.id.username);

        Password =  findViewById(R.id.password);

        FacButton = findViewById(R.id.Faculty_Switch_Button);

        StuButton = findViewById(R.id.Student_Switch_Button);

        tlogin=findViewById(R.id.loginTextView);

        tlogin.setVisibility(View.VISIBLE);
        tlogin.setText("Employee");

        DataBase.child("Admins").addValueEventListener(new AddAdminValueEventListener());

        DataBase.child("Clients").addValueEventListener(new AddClientValueEventListener());

        //AdminOrClient = findViewById(R.id.AdminClientSwitch);

        //AdminOrClient.setChecked(true);

        FacButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=1;
                tlogin.setVisibility(View.VISIBLE);
                tlogin.setText("EMPLOYEE");
            }
        });
        StuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=2;
                tlogin.setVisibility(View.VISIBLE);
                tlogin.setText("STUDENT");
            }
        });


    }


    public void Login(View v)
    {
        username = Username.getText().toString().trim();

        password = Password.getText().toString().trim();

        if( username.length() == 0 || password.length() == 0 )
        {
            Toast.makeText(this,"Enter Required Credentials!!",Toast.LENGTH_SHORT).show();

            return ;
        }

        //Log.d("values","1");
        if(username.equals("mainad") && password.equals("1234")){
            Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,MainAdminActivity.class);
            //i.putExtra("log","kluform");
            startActivity(i);
        }
        if (username.equals("vvsklu")&&password.equals("1234")){
            Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,Report.class);
            //i.putExtra("log","kluform");
            startActivity(i);
        }
        if( a==1/*!(AdminOrClient.isChecked()) */)
        {
            CheckAdminsLogin();

            DataBaseTemp = DataBase.child("Admins");
        }

        else if( a==2/*AdminOrClient.isChecked() */)
        {
            CheckClientsLogin();

            DataBaseTemp = DataBase.child("Clients");
        }
    }

///////////////////////////////////////// MISC /////////////////////////////////////////////////////

    /*                              START CHECKING USERS                                          */
    public void CheckAdminsLogin()
    {
        Log.d("values","3");

        DataBaseTemp = DataBase.child("Admins");

        DataBaseTemp.addListenerForSingleValueEvent(new SearchUsersSingleValueEventListener());
    }

    public void CheckClientsLogin()
    {
        Log.d("values","4");

        DataBaseTemp = DataBase.child("Clients");

        DataBaseTemp.addListenerForSingleValueEvent(new SearchUsersSingleValueEventListener());
    }
    /*                             END CHECKING USERS                                             */

    /*                             START ADDING USERS                                             */
    public void AddAdmins(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin"+Long.toString(++NumberOfAdmins)).setValue(user);

        DataBaseTemp.child("CurrentAdminsCount").setValue(NumberOfAdmins);
    }

    public void AddClients(String Username,String Password)
    {
        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client"+Long.toString(++NumberOfClients)).setValue(user);

        DataBaseTemp.child("CurrentClientsCount").setValue(NumberOfClients);
    }
    /*                                END ADDING USERS                                            */

    /*                                 START SETTERS                                              */
    public void SetUsername(String Username)
    {
        user.setUsername(Username);
    }

    public void SetPassword(String Password)
    {
        user.setPassword(Password);
    }
    /*                                  END SETTERS                                               */

    /*                             START INTERFACE SELECT                                         */
    public void InterfaceSelect(String Status)
    {
        Log.d("Snap",""+Boolean.toString(Flag));

        if ( Flag )
        {
            Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            //Log.d("LoginStatus","Successful");

            if(a==2)
            {
                //UserType = "Client";

                Log.d("Interface123","Client");
                Intent i=new Intent(this,Report.class);
                //i.putExtra("data","user");
                startActivity(i);
            }

            else if (a==1)
            {
                //UserType = "Admin";

                Log.d("Interface123","Admin");
                Intent i=new Intent(this,AdminActivity.class);
                i.putExtra("usersno",""+String.valueOf(NumberOfClients));
                //Intent i=new Intent(this,RegistrationForm.class);
                Log.d("clients",String.valueOf(NumberOfClients));
                startActivity(i);
            }
        }

        else
        {
            if(!username.equals("kluform")||!password.equals("svr18")) {
                if (Status.equals("Blocked")) {
                    Log.d("LoginStatus", "You are Blocked.");
                    Toast.makeText(this, "Incorret Login Credentials", Toast.LENGTH_SHORT).show();
                    //Changes are made here from below toast to above toast
                    //Toast.makeText(this, "You are Blocked.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("LoginStatus", "Incorrect Login Credentials");
                    Toast.makeText(this, "Incorret Login Credentials", Toast.LENGTH_SHORT).show();
                }
            }

        }
        /*else {
            Toast.makeText(this, "Incorret Login Credentials", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void developers(View view) {
        Intent i=new Intent(this,DevelopersActivity.class);
        startActivity(i);

    }

    public void applyScholarship(View view) {
        Intent i=new Intent(this,ApplyActivity.class);
        startActivity(i);

    }


    /*                                    END INTERFACE SELECT                                          */

/////////////////////////////////////////// END MISC ///////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////

    /*                                           ADMIN                                            */
    class AddAdminValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentAdminsCount");

            NumberOfAdmins = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    /*                                          END ADMIN                                         */

    /*                                         START CLIENT                                       */
    class AddClientValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            SnapShot = dataSnapshot.child("CurrentClientsCount");

            NumberOfClients = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    /*                                          END CLIENT                                        */

    /*                                      START ADMIN CLIENT                                    */
    public class SearchUsersSingleValueEventListener implements ValueEventListener
    {
        boolean flag;

        String TempUsername , TempPassword ;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            Log.d("values","5");

            String Temp = null;

            Flag = false;

            flag = true;

            for( DataSnapshot SnapShot : dataSnapshot.getChildren() )
            {
                if( flag )
                {
                    flag = false;

                    continue;
                }

                TempUsername = SnapShot.child("username").getValue().toString();

                TempPassword = SnapShot.child("password").getValue().toString();

                Log.d("CheckPassword",""+TempUsername+TempPassword);

                Log.d("CheckPassword",""+username+password);

                Temp = SnapShot.child("status").getValue().toString();

                if( TempUsername.equals(username) && TempPassword.equals(password) )
                {
                    if( Temp.equals("Active") )
                    {
                        Flag = true;
                    }

                    break;
                }
            }

            InterfaceSelect(Temp);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    }
    /*                                     END ADMIN CLIENT                                      */

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
