package test.elgin.darleer.com.testeventbus.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import test.elgin.darleer.com.testeventbus.R;
import test.elgin.darleer.com.testeventbus.message.MessageEvent;

public class SecondActivity extends AppCompatActivity {

    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnSend = findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new MessageEvent("这是来自SecondActivity的消息"));
                        finish();
                    }
                }
        );
    }
}
