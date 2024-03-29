package com.project.voicemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class LogIn extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://voicemail-ffd5e-default-rtdb.firebaseio.com/");
    private TextToSpeak TTS;
    private TextView userName;
    private  TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TTS = new TextToSpeak();

        TTS.textToSpeak(this, "TO LOGIN PLEASE, SAY YOUR USERNAME AND PASSWORD");


    }
    private boolean checkUsrName(){
       userName = findViewById(R.id.logInUser);
       String st =  userName.getText().toString();
       if(st.equals("")){
           return false;
       }
       return true;
    }

    private boolean checkPassword(){
        password = findViewById(R.id.logInPassword);
        String st =  password.getText().toString();
        if(st.equals("")){
            return false;
        }
        return true;
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
                    setUserNameAndPassword(result.get(0));
                }

                break;
        }

    }

    private void setUserNameAndPassword(String st) {

        if(checkUsrName()== false){
            st = st.toLowerCase();
            st = st.replaceAll("\\s", "");
            TTS = new TextToSpeak();
            userName = findViewById(R.id.logInUser);
            userName.setText(st);
            TTS.textToSpeak(this, "your user name is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if want to change user name say edit user or say your password");
        }
        else if(st.equals("edit user")){
            userName = findViewById(R.id.logInUser);
            userName.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your user name");
        }

        else if(checkPassword() == false){
            password = findViewById(R.id.logInPassword);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setText(st);
            TTS.textToSpeak(this, "you entered your password ");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if want to change your password say edit password or say login to enter the application");

        }
        else if(st.equals("edit password")){
            password = findViewById(R.id.logInPassword);
            password.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter your password");
        }
        else if(st.equals("login") || st.equals("log in")){
            checkLogin(userName.getText().toString(), password.getText().toString());

        }

        else{
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "You have enter all your information please say login");

        }

    }
    private void checkLogin(String userName, String password){
        databaseReference.child("register").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if user is exist in firebase database
                if(snapshot.hasChild(userName)){
                    // user name is exist in firebase database
                    // now get password of user firebase database and
                   final String getPassword = snapshot.child(userName).child(("password")).getValue(String.class);
                   if(getPassword.equals(password)){
                       Intent menu = new Intent(LogIn.this ,Menu.class);
                       startActivity(menu);
                   }
                   else{
                       TTS = new TextToSpeak();
                       TTS.textToSpeak(LogIn.this, "user name or password is incorrect please try again");
                   }

                }
                else{
                    TTS = new TextToSpeak();
                    TTS.textToSpeak(LogIn.this, "user name or password is incorrect please try again");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}