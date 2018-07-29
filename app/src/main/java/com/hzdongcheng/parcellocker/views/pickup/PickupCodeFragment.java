package com.hzdongcheng.parcellocker.views.pickup;


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
import android.widget.EditText;
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
 * Use the {@link PickupCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickupCodeFragment extends WrapperFragment {

    @BindView(R.id.et_open_code)
    EditText etOpenCode;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.btn_go_back)
    Button btnExit;
    Unbinder unbinder;
    PickupViewmodel pickupViewmodel;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    public PickupCodeFragment() {
        // Required empty public constructor
    }

    public static PickupCodeFragment newInstance() {
        PickupCodeFragment fragment = new PickupCodeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pickup_code, container, false);
        pickupViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(PickupViewmodel.class);
        pickupViewmodel.pickupModel.getInputError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);
            }
        });
        unbinder = ButterKnife.bind(this, view);
        etOpenCode.requestFocus();
        etOpenCode.setFocusableInTouchMode(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_submit)
    public void onBtSubmitClicked() {
        if (etOpenCode.getText().toString().isEmpty()){
            return;
        }
        pickupViewmodel.pickupModel.getOpenBoxCode().setValue(etOpenCode.getText().toString());
        pickupViewmodel.doPickupCheck();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.class);
    }
}
