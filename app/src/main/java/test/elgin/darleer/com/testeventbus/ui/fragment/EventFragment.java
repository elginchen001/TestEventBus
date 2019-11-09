package test.elgin.darleer.com.testeventbus.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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

    private TextView txtMessage;
    private Button btnSend;
    private String FragmentName;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        txtMessage = view.findViewById(R.id.txtMessage);
        btnSend = view.findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new MessageEvent("这是来自"+this.getClass().getName()+"的消息"));
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
