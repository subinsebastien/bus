package in.xtel.busandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import in.xtel.bus.Bus;
import in.xtel.bus.Subscribe;
import in.xtel.bus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<Float> floats = new ArrayList<Float>();
                floats.add(19.5f);
                Bus.getInstance().post(floats);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<String> strings = new ArrayList<String>();
                strings.add("Subin");
                Bus.getInstance().post(strings);

            }
        }).start();
    }

    @Subscribe(ThreadMode.UI_THREAD)
    public void onSomeEvent(int i) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("" + i);
    }

    @Subscribe(ThreadMode.UI_THREAD)
    public void onArrayList1(ArrayList<String> strings) {
        Log.e("-------", strings.get(0));
    }

    @Subscribe(ThreadMode.UI_THREAD)
    public void onArrayList2(ArrayList<Float> floats) {
        Log.e("-------", floats.get(0) + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bus.getInstance().subscribe(this);
    }

    @Override
    protected void onPause() {
        Bus.getInstance().unsubscribe(this);
        super.onPause();
    }
}
