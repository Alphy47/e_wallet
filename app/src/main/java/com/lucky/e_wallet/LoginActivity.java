package com.lucky.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText ml, pw;
    private TextView link;
    private Button login;
    private FirebaseAuth mAuth;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ml = (EditText) findViewById(R.id.EMail);
        pw = (EditText) findViewById(R.id.LPassword);
        link = (TextView) findViewById(R.id.LinkSignin);
        link.setOnClickListener(this);
        login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.LinkSignin:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void userLogin() {
        String mail = ml.getText().toString();
        String pass = pw.getText().toString();

        if (mail.isEmpty()) {
            ml.setError("Email is required!");
            ml.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            pw.setError("Password is required!");
            pw.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful! ", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    GlobalVar.currentUser = snapshot.getValue(User.class);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }else{
                    Toast.makeText(LoginActivity.this, "Failed to Login! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.LinkLogin);
        linkTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }*/
}
