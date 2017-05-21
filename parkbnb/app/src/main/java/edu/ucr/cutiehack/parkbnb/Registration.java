package edu.ucr.cutiehack.parkbnb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail;
    private EditText editPassword;
    private Button registerButton;
    private TextView loginRedirect;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        //Initialization of the FirebaseAuth Object
        firebaseAuth = FirebaseAuth.getInstance();

        //Checking whether a user as already Logged In
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            //Starting the User Profile Activity if the user is already Logged in
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }

        //Initialization of the ProgressDialog object
        progressDialog = new ProgressDialog(this);

        //Retrieving EditText field values from the XML and storing them in java Variables
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        registerButton = (Button) findViewById(R.id.registerButton);

        loginRedirect = (TextView) findViewById(R.id.loginRedirect);

        //Adding the listener function to both the register button and the login redirection link (Text)
        registerButton.setOnClickListener(this);
        loginRedirect.setOnClickListener(this);

    }

    private void registerNewUser() {

        //Converting EditText type variables to String type variables
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //Checking whether the email field is empty and displaying a error message through Toast
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking whether the password field is empty and displaying a error message through Toast
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a preferred password", Toast.LENGTH_SHORT).show();
            return;
        }

        //Giving the ProgressDialog a message to display while the action in is progress
        progressDialog.setMessage("In Progress...");
        //Displaying the ProgressDialog
        progressDialog.show();

        /**
         *
         * @param email user email
         * @param password user password
        ref.child("users").child(authData.uid).set({
        provider: authData.provider,
        name: userName
         */
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                //checks whether the user has been successfully registered
                if(task.isSuccessful()){
                    //Displays a registration successful message through Toast
                    Toast.makeText(Registration.this, "User Registered", Toast.LENGTH_SHORT).show();
                    finish();
                    //Redirects to the User Profile Activity
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }else{
                    //Displays a registration Unsuccessful message through Toast
                    Toast.makeText(Registration.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Method to switch from Registration page to the Login Page
    private void redirectToLogin(){
        finish();
        //starts the Login Activity
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onClick(View view) {
        //when registerButton is clicked registerNewUser method is invoked
        if(view == registerButton){
            registerNewUser();
        }

        //when loginRedirect button is clicked redirectToLogin method is invoked
        if(view == loginRedirect){
            redirectToLogin();
        }

    }
}

