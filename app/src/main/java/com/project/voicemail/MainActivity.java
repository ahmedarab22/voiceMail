package com.project.voicemail;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextToSpeak TTS = new TextToSpeak();
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"connection success" , Toast.LENGTH_SHORT).show();
        textViewResult = findViewById(R.id.textViewResult);

        // welcome to void mail
        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "welcome to voice mail");

        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "Please, say logIn to Enter the Application or Sign up to create new Account");




    }

    public  void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak now");

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


                    textViewResult.setText(result.get(0));
                    operation(result.get(0));
                }

                break;
        }

    }

    private void operation(String st) {


        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
        st = st.toLowerCase();
        if(st.equals("login") || st.equals("log in")){
            Intent login = new Intent(this ,LogIn.class);
            startActivity(login);

        }
        if(st.equals("signup") || st.equals("sign up")){
            Intent signup = new Intent(this ,SignUp.class);
            startActivity(signup);

        }

        else{
            TTS.textToSpeak(this, "please  say LogIn");

        }

    }




}

