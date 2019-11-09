package test.elgin.darleer.com.testeventbus.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import test.elgin.darleer.com.testeventbus.R;
import test.elgin.darleer.com.testeventbus.message.MessageEvent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnReg;
    private Button btnSend, btnAdd;
    private EditText etxtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        etxtMessage = (EditText) findViewById(R.id.etxtMessage);
    }

    @Override
    public void onClick(View v) {
        int vID = v.getId();
        switch (vID) {
            case R.id.btnReg:
                EventBus.getDefault().register(this);
                break;
            case R.id.btnAdd:
                startActivity(new Intent(this,SecondActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 必须要有@Subcribe注释
     * 必须是public方法
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent)
    {
        this.etxtMessage.setText(messageEvent.getMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
