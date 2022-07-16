package com.project.voicemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public class SendMail extends AppCompatActivity {
    private TextToSpeak TTS;
    public TextView mEmail;
    public TextView mSubject;
    public TextView mMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        mEmail = (TextView) findViewById(R.id.to);
        mSubject = (TextView) findViewById(R.id.subject);
       mMessage = (TextView) findViewById(R.id.message);

        TTS = new TextToSpeak();

        TTS.textToSpeak(this, "YOU ARE IN COMPOSE MAIL");

       // TTS = new TextToSpeak();

        //TTS.textToSpeak(this, "");
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
                    setMailSubjectAndMessage(result.get(0));
                }

                //sendMail();

                break;
        }

    }
    private boolean isToMailEmpty(){
        boolean flag = true;
        mEmail = (TextView) findViewById(R.id.to);
        String st =  mEmail.getText().toString();
        if(st.equals(""))
            flag = false;

        return flag;
    }


    private void setMailSubjectAndMessage(String st) {
        if(isToMailEmpty() == false){
            st = st.toLowerCase();
           st = st.replaceAll("\\s", "");

           mEmail = (TextView) findViewById(R.id.to);
            mEmail.setText(st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "your email is " + st);
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "if want to change email say edit mail");
        }
        else if(st.equals("edit mail") || st.equals("edit email")){
         //   mEmail = findViewById(R.id.mailId);
            mEmail.setText("");
            TTS = new TextToSpeak();
            TTS.textToSpeak(this, "please enter email");
        }
    }
}