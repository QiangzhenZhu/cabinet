package com.hzdongcheng.parcellocker.views.deliver;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;

public class DeliverExpiredFragment extends WrapperFragment {


    public DeliverExpiredFragment() {
    }

    public static DeliverExpiredFragment newInstance(String param1, String param2) {
        DeliverExpiredFragment fragment = new DeliverExpiredFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deliver_expired, container, false);
    }

}
