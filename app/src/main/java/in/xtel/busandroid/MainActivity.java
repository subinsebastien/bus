package in.xtel.busandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.xtel.bus.Bus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        Bus.getInstance().subscribe(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
