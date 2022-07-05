package com.project.voicemail;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;


public class TextToSpeak  {
    private TextToSpeech TTS;
    public TextToSpeak(){
        //
    }

    public void textToSpeak(Context context,String speak) {
        TTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = TTS.setLanguage(Locale.UK);

                    if(result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED ){

                        TTS.speak("Language not supported", TextToSpeech.QUEUE_FLUSH, null );

                       Log.e("TTS", "Language not supported");
                    }

                }
                else{
                    TTS.speak("Fail to Speak", TextToSpeech.QUEUE_FLUSH, null );
                    Log.e("TTS", "Fail to Speak");

                }

                speak(speak);

            }
        });
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private void speak(String speak){
        TTS.speak(speak, TextToSpeech.QUEUE_FLUSH, null );

    }



}
