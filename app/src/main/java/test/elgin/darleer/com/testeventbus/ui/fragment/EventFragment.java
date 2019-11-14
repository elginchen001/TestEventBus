package test.elgin.darleer.com.testeventbus.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import test.elgin.darleer.com.testeventbus.R;
import test.elgin.darleer.com.testeventbus.message.MessageEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private TextView txtMessage,txtFragment;
    private Button btnSend,btnTest;
    public String FragmentName;

    public EventFragment() {
        // Required empty public constructor
    }

    public void setClassName(String s)
    {
        this.FragmentName = s;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        View v = this.getView();
        if(v!=null)
            Log.v("TAG",v.getClass().getName());
        txtMessage = view.findViewById(R.id.txtMessage);
        txtFragment = view.findViewById(R.id.txtFragment);
        txtFragment.setText(FragmentName+"收到消息如下：");
        btnSend = view.findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new MessageEvent("这是来自"+FragmentName+"的消息"));
                    }
                }
        );
        btnTest = view.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("TAG",getView().getClass().toString());
                    }
                }
        );
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessageEvent(MessageEvent messageEvent)
    {
        txtMessage.setText(messageEvent.getMessage());
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

}
