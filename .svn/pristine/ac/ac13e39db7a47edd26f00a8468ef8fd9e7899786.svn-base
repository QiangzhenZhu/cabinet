package com.hzdongcheng.parcellocker.views.navigate;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.RichEditText;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NavigateRegisterFragment extends WrapperFragment {

    MainViewmodel viewmodel;
    @BindView(R.id.et_manager_id)
    RichEditText etManagerId;
    @BindView(R.id.et_manager_pwd)
    RichEditText etManagerPwd;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;

    public NavigateRegisterFragment() {
    }

    public static NavigateRegisterFragment newInstance(String param1, String param2) {
        NavigateRegisterFragment fragment = new NavigateRegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_register, container, false);
        viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        unbinder = ButterKnife.bind(this, view);
        viewmodel.mainModel.getProcessTips().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        viewmodel.mainModel.getInitPasswd().setValue(etManagerId.getText().toString());
        viewmodel.mainModel.getServerUrl().setValue(etManagerPwd.getText().toString());
        viewmodel.deviceRegister();

    }



}
