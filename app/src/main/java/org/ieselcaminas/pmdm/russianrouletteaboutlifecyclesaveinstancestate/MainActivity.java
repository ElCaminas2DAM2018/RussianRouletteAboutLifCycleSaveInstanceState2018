package org.ieselcaminas.pmdm.russianrouletteaboutlifecyclesaveinstancestate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private FrameLayout bangLayout;
    private TextView textBang;
    private Barrel barrel;
    private static final String TAG = "LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "In the onCreate() event");

        bangLayout = findViewById(R.id.bangLayout);
        textBang = findViewById(R.id.textViewBang);

        assignActionToReloadButton();

        barrel = findViewById(R.id.barrelLayout);
        barrel.setFireListener(new Barrel.FireListener() {
            @Override
            public void fire(boolean bang) {
                if (bang) {
                    bang();
                }
            }
        });

        Button aboutButton = findViewById(R.id.buttonAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState!=null) {
            boolean[] arrayEnabled = savedInstanceState.getBooleanArray("enabled");
            if (arrayEnabled!=null) {
                barrel.disableButtons(arrayEnabled);
            }
            if (savedInstanceState.getBoolean("gameover")) {
                bang();
            }
        }

    }

    private void assignActionToReloadButton() {
        Button reloadButton = findViewById(R.id.buttonReload);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                barrel.reset();
            }

            private void resetBackground() {
                bangLayout.setBackgroundColor(
                        getResources().
                                getColor(R.color.colorNoBang));
                textBang.setVisibility(View.INVISIBLE);
            }


        });
    }

    private void bang() {
        bangLayout.setBackgroundColor(getResources().getColor(R.color.colorBang));
        textBang.setVisibility(View.VISIBLE);
        barrel.setGameOver(true);
    }


    // ------------- Life Cycle -------
    public void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart() event");
    }
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "In the onRestart() event");
    }
    public void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume() event");
    }
    public void onPause() {
        super.onPause();
        Log.d(TAG, "In the onPause() event");
    }
    public void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop() event");
    }
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy() event");
    }


    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Toast.makeText(this, "Executing onSaveInstanceState",
                Toast.LENGTH_LONG).show();
        bundle.putBooleanArray("enabled", barrel.getDisabledButtons());
        bundle.putBoolean("gameover", barrel.isGameOver());
    }

}
