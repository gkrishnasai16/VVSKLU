package com.klu.vvs.vvsklu;

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

public class MainAdminActivity extends AppCompatActivity {

    private long NumberOfAdmins, NumberOfClients;

    private EditText Username, Password;

    private Button Submit;

    private Switch AdminOrClient;

    private DatabaseReference DataBase = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference DataBaseTemp, Admins, Clients;

    private DataSnapshot SnapShot, AdminSnapShot, ClientSnapShot;

    private String username, password;

    private Users user = new Users();
    TextView tv,pv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_admin);

        //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

        Username = findViewById(R.id.Username);

        Password = findViewById(R.id.Password);

        AdminOrClient = findViewById(R.id.AdminOrClient);

        AdminOrClient.setChecked(false);

        tv=findViewById(R.id.textView4);
        pv=findViewById(R.id.textView3);

        //String cre=getIntent().getStringExtra("new");

        DataBase.child("Admins").addValueEventListener(new AddAdminValueEventListener());

        DataBase.child("Clients").addValueEventListener(new AddClientValueEventListener());

        Submit = findViewById(R.id.Submit);


    }



//////////////////////////////////////////////// END MAIN //////////////////////////////////////////////////

//////////////////////////////////////////////// LISTENERS /////////////////////////////////////////////////

    /*                                           ADMIN                                            */
    class AddAdminValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            AdminSnapShot = dataSnapshot;

            SnapShot = dataSnapshot.child("CurrentAdminsCount");

            NumberOfAdmins = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d("snap", "2.2");
        }
    }
    /*                                          END ADMIN                                         */

    /*                                         START CLIENT                                       */
    class AddClientValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ClientSnapShot = dataSnapshot;

            SnapShot = dataSnapshot.child("CurrentClientsCount");

            NumberOfClients = Long.parseLong(SnapShot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d("snap", "2.2");
        }
    }

    /*                                          END CLIENT                                        */

/////////////////////////////////////////////// END LISTENERS /////////////////////////////////////////////

    public void Login(View v) {
        String x;

        boolean flag;

        username = Username.getText().toString().trim();

        password = Password.getText().toString().trim();

        final String Check = CheckLogin(username, password);

        if (Check.equals("Admin")&&!username.equals("")&&!password.equals("")) {
            flag = true;

            for (DataSnapshot SnapShot : AdminSnapShot.getChildren()) {
                if (flag) {
                    flag = false;

                    continue;
                }

                x = SnapShot.child("username").getValue().toString();

                if (x.equals(username)) {
                    Log.d("Status", "Failed,User Already Exists ");

                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        } else if (Check.equals("Client")&&!username.equals("")&&!password.equals("")) {
            flag = true;

            for (DataSnapshot SnapShot : ClientSnapShot.getChildren()) {
                if (flag) {
                    flag = false;

                    continue;
                }

                x = SnapShot.child("username").getValue().toString();

                if (x.equals(username)) {
                    Log.d("Status", "Failed,User Already Exists");

                    Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
        }

        if (Check.equals("Admin")&&!username.equals("")&&!password.equals("")) {
            AddAdmins(username, password);
            Toast.makeText(this, "Admin Created", Toast.LENGTH_SHORT).show();
        } else if (Check.equals("Client")&&!username.equals("")&&!password.equals("")) {
            AddClients(username, password);

            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
        } else if (Check.equals("Fasaak")&&!username.equals("")&&!password.equals("")) {
            Toast.makeText(this, "Creation Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    public String CheckLogin(final String username, final String password) {
        if (!(AdminOrClient.isChecked())) {
            return "Admin";
        } else {
            return "Client";
        }
    }

    public void AddAdmins(String Username, String Password) {
        DataBaseTemp = DataBase.child("Admins");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("admin" + Long.toString(++NumberOfAdmins)).setValue(user);

        DataBaseTemp.child("CurrentAdminsCount").setValue(NumberOfAdmins);

        DataBaseTemp.child("admin" + Long.toString(NumberOfAdmins)).child("status").setValue("Active");

        Log.d("snap", "4");
    }

    public void AddClients(String Username, String Password) {
        DataBaseTemp = DataBase.child("Clients");

        user.setUsername(Username);

        user.setPassword(Password);

        DataBaseTemp.child("client" + Long.toString(++NumberOfClients)).setValue(user);

        DataBaseTemp.child("CurrentClientsCount").setValue(NumberOfClients);

        DataBaseTemp.child("client" + Long.toString(NumberOfClients)).child("status").setValue("Active");

        Log.d("snap", "4");
    }

    public void SetUsername(String Username) {

        user.setUsername(Username);
    }

    public void SetPassword(String Password) {
        user.setPassword(Password);
    }



    public void goBack(View view) {
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "This functionality not allowed here!! Kindly use LOGOUT", Toast.LENGTH_SHORT).show();
    }
}
