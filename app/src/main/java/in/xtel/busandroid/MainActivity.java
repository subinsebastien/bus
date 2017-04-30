package in.xtel.busandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import in.xtel.bus.Bus;
import in.xtel.bus.Subscribe;
import in.xtel.bus.ThreadMode;

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

        //findViewById(R.id.editText).setVisibility(100);

    }

    public void onButtonClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bus.getInstance().post(130);
            }
        }).start();
    }

    @Subscribe(ThreadMode.UI_THREAD)
    public void onSomeEvent(int i) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("" + i);
    }

    @Override
    protected void onPause() {
        Bus.getInstance().unsubscribe(this);
        super.onPause();
    }
}
