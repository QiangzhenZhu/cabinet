package com.hzdongcheng.parcellocker.views.pickup;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.PickupViewmodel;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickupOpenedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickupOpenedFragment extends WrapperFragment {


    @BindView(R.id.tv_open_box_tips)
    TextView tvOpenBoxTips;
    @BindView(R.id.bt_to_home)
    Button btToHome;
    @BindView(R.id.bt_pickup_continue)
    Button btPickupContinue;
    Unbinder unbinder;

    public PickupOpenedFragment() {
        // Required empty public constructor
    }


    public static PickupOpenedFragment newInstance(String param1, String param2) {
        PickupOpenedFragment fragment = new PickupOpenedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    PickupViewmodel pickupViewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup_opened, container, false);
        unbinder = ButterKnife.bind(this, view);
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        initView();
        return view;
    }

    private void initView() {
        pickupViewmodel.pickupModel.getBoxOpenSuccess().observe(this, new Observer<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@Nullable String s) {
                tvOpenBoxTips.setText(s + getString(R.string.prompt_pickup_success));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_to_home)
    public void onBtToHomeClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().postValue(NavigateHomeFragment.class);
    }

    @OnClick(R.id.bt_pickup_continue)
    public void onBtPickupContinueClicked() {
        pickupViewmodel.pickupContinue();
    }
}
