package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    PowerConnectedReciever receiver;
    ImageView imgOpener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setTitle("Onur AYDEMIR 16011039");

        imgOpener = findViewById(R.id.imageOpener);

        getSupportActionBar().hide();
        imgOpener.setImageResource(R.drawable.openerscreen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        }, 1000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new PowerConnectedReciever();

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, ifilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}