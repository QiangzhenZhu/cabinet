package com.hzdongcheng.parcellocker.views.deliver;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DeliverLoginFragment extends WrapperFragment {

    @BindView(R.id.et_courier_id)
    EditText etCourierId;
    @BindView(R.id.et_courier_pwd)
    EditText etCourierPwd;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    DeliverViewmodel deliverViewmodel;

    public DeliverLoginFragment() {
        // Required empty public constructor
    }

    public static DeliverLoginFragment newInstance(String param1, String param2) {
        DeliverLoginFragment fragment = new DeliverLoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        etCourierId.setOnKeyListener(keyDownEvent);
        etCourierId.requestFocus();
        etCourierId.setFocusableInTouchMode(true);
        etCourierPwd.setOnKeyListener(keyDownEvent);
        deliverViewmodel.model.getErrorTips().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);
            }
        });
        return view;
    }

    View.OnKeyListener keyDownEvent = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (view.getId() == R.id.et_courier_id) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP && !etCourierId.getText().toString().isEmpty()) {
                        etCourierPwd.requestFocus();
                        etCourierPwd.setFocusableInTouchMode(true);
                    }
                    return true;
                }
            } else {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP && !etCourierPwd.getText().toString().isEmpty()) {
                        onBtnLoginClicked();
                    }
                    return true;
                }
            }
            deliverViewmodel.model.getErrorTips().setValue("");
            return false;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBack() {
//        Intent startIntent = new Intent();
//        startIntent.putExtra("dowload_url","http://192.168.8.239:8081/app-debug.apk");
//        startIntent.putExtra("md5","648B64EE47FE235404AE28FEA1E3066B".toLowerCase());
//        startIntent.putExtra("force_start",true);
//        startIntent.putExtra("package_name","com.hzdongcheng.parcellocker");
//        startIntent.setAction("com.hzdongcheng.dzapplicationmanager.action.updateservices");
//        startIntent.addCategory(Intent.CATEGORY_DEFAULT);
//        getActivity().startService(startIntent);
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.class);
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        if (etCourierId.getText().toString().isEmpty()) {
            tvTips.setText(getString(R.string.prompt_courier_user_name));
            return;
        }
        if (etCourierPwd.getText().toString().isEmpty()) {
            tvTips.setText(getString(R.string.prompt_courier_user_pwd));
            return;
        }
        deliverViewmodel.model.getUserName().setValue(etCourierId.getText().toString());
        deliverViewmodel.model.getPassword().setValue(etCourierPwd.getText().toString());
        deliverViewmodel.login();
    }

}
