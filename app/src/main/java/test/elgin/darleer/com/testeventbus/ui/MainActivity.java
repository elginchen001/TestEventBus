package test.elgin.darleer.com.testeventbus.ui;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        MessageFragment1.OnFragmentInteractionListener,
        MessageFragment2.OnFragmentInteractionListener {
    private Button btnReg;
    private Button btnSend, btnAdd;
    private EditText etxtMessage;
    public int frameLayout1;
    public int frameLayout2;

    private MessageFragment1 mMessageFragment1;
    private MessageFragment2 mMessageFragment2;

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
        frameLayout1 = R.id.frameLayout1;
        frameLayout2 = R.id.frameLayout2;
        initFragment();
    }

    @Override
    public void onClick(View v) {
        int vID = v.getId();
        switch (vID) {
            case R.id.btnReg:
//                mMessageFragment1.bindFragment(mMessageFragment2);
//                mMessageFragment2.bindFragment(mMessageFragment1);
                EventBus.getDefault().register(this);
                break;
            case R.id.btnSend:
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void onHandleEventMessage(MessageEvent messageEvent)
    {
        this.etxtMessage.setText(messageEvent.getMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initFragment() {
        try {
            this.mMessageFragment1 = MessageFragment1.newInstance("frag1", "frag1");
            this.mMessageFragment2 = MessageFragment2.newInstance("frag2", "frag2");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportFragmentManager().beginTransaction().replace(frameLayout1, mMessageFragment1).commitNow();
            getSupportFragmentManager().beginTransaction().replace(frameLayout2, mMessageFragment2).commitNow();
        } catch (Exception e) {
            Log.v("TAG", e.getMessage());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
