package com.hfad.camera;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class settings extends AppCompatActivity {
    Vibrator vibrator;
    private float x;
    private float y;
    SharedPref sharedPref;
    private Switch myswitch;
    private TextToSpeech t1;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ScrollView layout;
    SeekBar sb;
    TextView tv;
    TextView tv2;
    TextView tv3;
    Switch sw;
    Switch sw2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        sharedPref = new SharedPref(this);
       // AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (sharedPref.loadNightModeState() == true) {
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myswitch = (Switch) findViewById(R.id.switch2);
        if (sharedPref.loadNightModeState() == true) {
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPref.setNightModeState(true);
                    restartApp();

                } else {
                    sharedPref.setNightModeState(false);
                    restartApp();

                }
            }
        });



        sb = findViewById(R.id.scroll1);
        tv = findViewById(R.id.tv3);
        tv2 = findViewById(R.id.tv4);
        tv3 = findViewById(R.id.tv5);
        sw = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        btn = findViewById(R.id.rate);
        layout = findViewById(R.id.layout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x=event.getX();
                y=event.getY();
                int action = event.getAction();
                if (action != MotionEvent.ACTION_DOWN
                        && action != MotionEvent.ACTION_MOVE
                        && action != MotionEvent.ACTION_UP) return false;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vb.vibrate(100);

                }
                return false;
            }


        });


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                tv.setTextSize(Float.valueOf(i));
                tv2.setTextSize(Float.valueOf(i));
                tv3.setTextSize(Float.valueOf(i));
                sw.setTextSize(Float.valueOf(i));
                sw2.setTextSize(Float.valueOf(i));
                btn.setTextSize(Float.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        sw.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toSpeak = "Text to speech";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return true;
            }
        });

        sw2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toSpeak = "Night Mode";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return true;
            }
        });

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toSpeak = "Text Size";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return true;
            }
        });

        tv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toSpeak = "Contact us!";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return true;
            }
        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String toSpeak = "Rate us!";
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return true;
            }
        });

        tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String text;
                text = tv3.getText().toString();

                myClip = ClipData.newPlainText("Visionary@email.com", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Visionary@email.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "User feedback");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    ;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + "com.android.chrome")));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome")));
                }
            }});


       // vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);



    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

}

