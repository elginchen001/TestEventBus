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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import test.elgin.darleer.com.testeventbus.R;
import test.elgin.darleer.com.testeventbus.message.MessageEvent;
import test.elgin.darleer.com.testeventbus.ui.fragment.EventFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnReg;
    private Button btnSend, btnAdd;
    private EditText etxtMessage;
    private EventFragment fragment1,fragment2;
    private FrameLayout frameLayout1,frameLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();
    }

    private void initViews()
    {
        btnReg = findViewById(R.id.btnReg);
        btnReg.setOnClickListener(this);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        etxtMessage = (EditText) findViewById(R.id.etxtMessage);
        frameLayout1 = findViewById(R.id.frameLayout1);
        frameLayout2 = findViewById(R.id.frameLayout2);
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
            case R.id.btnSend:
                EventBus.getDefault().post(new MessageEvent(etxtMessage.getText().toString()));
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

    /**
     * EventFragment类中不要有静态变量，否则只初始化一个对象。
     * FragmentTransaction要定义两个变量，否则报错
     */
    private void initFragments()
    {
        //EventFragment类中不要有静态变量，否则只初始化一个对象。
        fragment2 = new EventFragment();
        fragment2.setClassName("fragment2");
        android.app.FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
        fragmentTransaction2.add(R.id.frameLayout2,fragment2,"fragment2").show(fragment2).commit();
        fragment1 = new EventFragment();
        fragment1.setClassName("fragment1");
        android.app.FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        fragmentTransaction1.add(R.id.frameLayout1,fragment1,"fragment1").show(fragment1).commit();
    }
}
