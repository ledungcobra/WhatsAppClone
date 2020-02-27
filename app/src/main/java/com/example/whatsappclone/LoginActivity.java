package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

   private EditText edtEmail,edtPassword;
   private Button btnLogin,btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail2);
        edtPassword = findViewById(R.id.edtPassword2);

        btnLogin = findViewById(R.id.btnLogin2);
        btnSignUp = findViewById(R.id.btnSignUp2);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){
            transitionToWhatsAppUserActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin2:

                ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e == null){

                            showToast(user.getUsername()+"login successfully",FancyToast.SUCCESS);
                            transitionToWhatsAppUserActivity();

                        }else{

                            showToast(e.getMessage(),FancyToast.ERROR);


                        }
                    }
                });

                break;
            case R.id.btnSignUp2:

                transitionToSignUpActivity();
                break;

        }
    }

    private void transitionToSignUpActivity() {

        startActivity(new Intent(this,SignUpActivity.class));
        finish();

    }

    private void transitionToWhatsAppUserActivity() {

        startActivity(new Intent(this,WhatsAppUserActivity.class));
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
