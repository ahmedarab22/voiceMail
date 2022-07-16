package com.project.voicemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    //creating database object

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://voicemail-ffd5e-default-rtdb.firebaseio.com/");


    TextToSpeak TTS;
    private TextView firstName;
    private TextView lastName;
    private TextView userName;
    private TextView password;
    private TextView confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TTS = new TextToSpeak();

        TTS.textToSpeak(this, "TO Sign up PLEASE, Enter Your information");
    }


    public  void getSpeechInput(View view){


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 10);

        }else{
            Toast.makeText(this, "Your device doesn't support input speech ", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    setInformationSignUp(result.get(0));
                }

                break;
        }

    }
    private boolean checkFirstName(){
        firstName = findViewById(R.id.firstName);
        String st =  firstName.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
    }
    private boolean checkLastName(){
        lastName = findViewById(R.id.lastName);
        String st =  lastName.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
    }

    private boolean checkUserName(){
        userName = findViewById(R.id.userName);
        String st =  userName.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
    }

    private boolean checkPassword(){
        password = findViewById(R.id.password);
        String st =  password.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
    }
    private boolean checkConfirmPassword(){
        confirmPassword = findViewById(R.id.confirmPassword);
        String st =  confirmPassword.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
    }



    void setInformationSignUp(String st){
        if(checkFirstName()== false){
            TTS = new TextToSpeak();
            firstName = findViewById(R.id.firstName);
            firstName.setText(st);
            TTS.textToSpeak(this, "your first name is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if want to change first name say edit or say your last name");
        }
        else if(st.equals("edit first name") || st.equals("edit firstname") || st.equals("edit")  ) {
            firstName = findViewById(R.id.firstName);
            firstName.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your first name");
        }

        else if(checkLastName()== false){
            TTS = new TextToSpeak();
            lastName = findViewById(R.id.lastName);
            lastName.setText(st);
            TTS.textToSpeak(this, "your last name is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if you want to change last name say edit or say your user name");
        }
        else if(st.equals("edit last name") || st.equals("edit lastname") || st.equals("edit")  ) {
            lastName = findViewById(R.id.lastName);
            lastName.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your last name");
        }
        else if(checkUserName()== false){
            TTS = new TextToSpeak();
            userName = findViewById(R.id.userName);
            st = st.toLowerCase();
            st = st.replaceAll("\\s", "");
            userName.setText(st);
            TTS.textToSpeak(this, "your user name is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if you want to change user name say edit or say your password");
        }
        else if(st.equals("edit user name") || st.equals("edit username") || st.equals("edit") ) {
            userName = findViewById(R.id.userName);
            userName.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your user name");
        }

        else if(checkPassword()== false){
            TTS = new TextToSpeak();
            password = findViewById(R.id.password);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setText(st);
            TTS.textToSpeak(this, "your password is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if you want to change password say edit or say again your password");
        }
        else if(st.equals("edit password") || st.equals("edit password") || st.equals("edit")  ) {
            password = findViewById(R.id.password);
            password.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your password");
        }

        else if(checkConfirmPassword()== false){
            TTS = new TextToSpeak();
            confirmPassword = findViewById(R.id.confirmPassword);
            confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmPassword.setText(st);

            password = findViewById(R.id.password);
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                TTS = new TextToSpeak();
                TTS.textToSpeak(this, "password has matched ,  please say sign up");
            }

        }
        else if(st.equals("edit confirm password") || st.equals("edit confirmpassword") || st.equals("edit") ) {
            confirmPassword = findViewById(R.id.confirmPassword);
            confirmPassword.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your confirmation password");
        }
        else if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "password has not matched , please say edit");
        }
        else if(st.equals("sign up") || st.equals("signup")){

            register(firstName.getText().toString(), lastName.getText().toString(), userName.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());

        }

    }
    void register(String firstName, String lastName, String userName, String password, String confirmPassword){
        databaseReference.child("register").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if user is not registered before
                if (snapshot.hasChild(userName)){
                    TTS = new TextToSpeak();
                    TTS.textToSpeak(SignUp.this, "user is already registered");
                }
                else{
                    // using username as unique

                    databaseReference.child("register").child(userName).child("firstName").setValue(firstName);
                    databaseReference.child("register").child(userName).child("lastName").setValue(lastName);
                    databaseReference.child("register").child(userName).child("password").setValue(password);
                    databaseReference.child("register").child(userName).child("confirmPassword").setValue(confirmPassword);
                    TTS = new TextToSpeak();
                    TTS.textToSpeak(SignUp.this, "user registered Successfully!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}