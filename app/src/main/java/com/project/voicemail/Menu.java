package com.project.voicemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://voicemail-ffd5e-default-rtdb.firebaseio.com/");

    private TextToSpeak TTS = new TextToSpeak();
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textViewResult = findViewById(R.id.textViewResult);

        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "welcome to Menu ");
        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "say compose to send an Email ");
        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "say inbox to see new Emails ");
        TTS = new TextToSpeak();
        TTS.textToSpeak(this, "say sent to view emails that you sent before ");
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.UK);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak now");

        if (intent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(intent, 10);

        } else {
            Toast.makeText(this, "Your device doesn't support input speech ", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //textViewResult.setText(result.get(0));
                    operation(result.get(0));
                }

                break;
        }

    }

    private void operation(String st) {


        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
        st = st.toLowerCase();
        if (st.equals("compose") || st.equals("composed")) {
            Intent send = new Intent(this ,SendMail.class);
             startActivity(send);

        }
        else if(st.equals("inbox")){

        }
        else if(st.equals("send") || st.equals("sent")){

        }
    }
}