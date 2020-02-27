package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername,edtPasword,edtEmail;
    private Button btnLogin,btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtEmail = findViewById(R.id.edtEmail1);
        edtPasword = findViewById(R.id.edtPassword1);
        edtUsername = findViewById(R.id.edtUsername1);

        btnLogin = findViewById(R.id.btnLogin1);
        btnSignUp = findViewById(R.id.btnSignUp1);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){

            transitionToWhatsAppUserActivity();

        }

    }

    @Override
    public void onClick(View v) {

      if(v.getId() == R.id.btnSignUp1){
          final ParseUser parseUser = new ParseUser();

          if (edtUsername.getText().toString().equals("") ||
                  edtPasword.getText().toString().equals("" )|| edtEmail.getText().toString().equals("")) {
              showToast("All field must be completed", FancyToast.WARNING);
              return;
          }

          parseUser.setUsername(edtUsername.getText().toString());
          parseUser.setEmail(edtEmail.getText().toString());
          parseUser.setPassword(edtPasword.getText().toString());

          parseUser.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {
                  if(e == null){

                      showToast(parseUser.getUsername()+"",FancyToast.SUCCESS);
                      transitionToWhatsAppUserActivity();


                  }else{
                      showToast(e.getMessage(),FancyToast.ERROR);

                  }
              }
          });
      }else if(v.getId() == R.id.btnLogin1){

          transitionToLoginActivity();



      }

    }

    private void transitionToWhatsAppUserActivity() {
        startActivity(new Intent(this,WhatsAppUserActivity.class));
        finish();

    }

    private void transitionToLoginActivity() {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
    }

    private void showToast(String message  , int type) {
        FancyToast.makeText(this,message,FancyToast.LENGTH_SHORT,type,false).show();
    }
    public void rootLayoutTapped(View v){
        try{

            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);


        }catch(Exception e){



        }



    }
}
