package com.lucky.e_wallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText firstname, lastname, email, password, repassword;
    private TextView Llink;
    private Button register;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firstname = (EditText) findViewById(R.id.FName);
        lastname = (EditText) findViewById(R.id.LName);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        repassword = (EditText) findViewById(R.id.Repassword);
        Llink = (TextView) findViewById(R.id.LinkLogin);
        Llink.setOnClickListener(this);
        register = (Button) findViewById(R.id.btnSignup);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignup:
                registerUser();
                break;
            case R.id.LinkLogin:
                startActivity(new Intent(this, LoginActivity.class));
        }
    }
    public void registerUser() {
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();

        if(fname.isEmpty()){
            firstname.setError("First name is required!");
            firstname.requestFocus();
            return;
        }
        if(lname.isEmpty()){
            lastname.setError("Last name is required!");
            lastname.requestFocus();
            return;
        }
        if(mail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(repass.isEmpty()){
            repassword.setError("Confirmation password is required!");
            repassword.requestFocus();
            return;
        }
        if(!pass.equals(repass)){
            Toast.makeText(RegisterActivity.this,"Passwords are not matching.", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fname, lname, mail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"Registered Successfully",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                                            }else {
                                                Toast.makeText(RegisterActivity.this,"Failed to register!123",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed to register!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /*private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.LinkLogin);
        linkTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }*/
}



/*                if (fname.equals("") || lname.equals("") || user.equals("") ||
                        mail.equals("") || pass.equals("") ||
                        repass.equals(""))
                    Toast.makeText(RegisterActivity.this,"Please enter all the fields",
                            Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser == false){
                            Boolean insert = DB.insertData(fname,lname,user,mail,pass);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this,"Registered Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this,"Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"User Already Exists!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Passwords are nor matching.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupHyperlink();
    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.LinkLogin);
        linkTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }



}*/
