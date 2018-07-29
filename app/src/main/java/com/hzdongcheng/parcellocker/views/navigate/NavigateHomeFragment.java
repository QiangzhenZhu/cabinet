package com.hzdongcheng.parcellocker.views.navigate;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.hzdongcheng.bll.monitors.TimingTask;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigateHomeFragment extends WrapperFragment {


    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_net)
    ImageView ivNet;
    @BindView(R.id.iv_device)
    ImageView ivDevice;
    @BindView(R.id.iv_power)
    ImageView ivPower;
    @BindView(R.id.tv_date)
    TextClock tvDate;
    @BindView(R.id.tv_time)
    TextClock tvTime;
    @BindView(R.id.et_manager)
    EditText etManager;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_server_hot)
    TextView tvServerHot;
    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    @BindView(R.id.bt_delivery)
    Button btDelivery;
    @BindView(R.id.bt_pickup)
    Button btPickup;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.rl_breakdown)
    RelativeLayout rlBreakdown;
    Unbinder unbinder;

    public NavigateHomeFragment() {
        // Required empty public constructor
    }

    public static NavigateHomeFragment newInstance() {
        NavigateHomeFragment fragment = new NavigateHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MainViewmodel viewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigate_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class);
        initView();
        return view;
    }

    private void initView() {
        viewmodel.mainModel.getNetworkState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                aBoolean = aBoolean == null ? false : aBoolean;
                ivNet.setBackgroundResource(aBoolean ? R.drawable.net : R.drawable.nete);
            }
        });
        viewmodel.mainModel.getVersion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvVersion.setText(s);
            }
        });
        viewmodel.mainModel.getDeviceCode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvDeviceCode.setText(s);
            }
        });
        viewmodel.mainModel.getServerHot().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvServerHot.setText(s);
            }
        });
        etManager.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && etManager.isFocusable()) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP && etManager.getText().toString().equals("0000")) {
                        try {
                            if (HAL.getMasterStatus()==null||HAL.getMasterStatus().getManagementSwitch()==1)
                                viewmodel.performManager();
                        } catch (DcdzSystemException e) {
                            viewmodel.performManager();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        TimingTask.transport(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_delivery)
    public void onBtDeliveryClicked() {
        viewmodel.performDelivery();
    }

    @OnClick(R.id.bt_pickup)
    public void onBtPickupClicked() {
        viewmodel.performPickup();
    }


    @OnClick(R.id.iv_logo)
    public void onViewClicked() {
        etManager.getText().clear();
        etManager.requestFocus();
    }
}
