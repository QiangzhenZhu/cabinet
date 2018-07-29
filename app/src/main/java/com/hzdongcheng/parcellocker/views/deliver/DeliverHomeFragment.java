package com.hzdongcheng.parcellocker.views.deliver;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class DeliverHomeFragment extends WrapperFragment {

    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.btn_expired)
    Button btnExpired;
    @BindView(R.id.tv_box_surplus)
    TextView tvBoxSurplus;
    @BindView(R.id.btn_large_box)
    Button btnLargeBox;
    @BindView(R.id.btn_middle_box)
    Button btnMiddleBox;
    @BindView(R.id.btn_small_box)
    Button btnSmallBox;

    public DeliverHomeFragment() {
    }

    public static DeliverHomeFragment newInstance(String param1, String param2) {
        DeliverHomeFragment fragment = new DeliverHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);

        deliverViewmodel.model.getExpirePackageNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                if(s>0)
                    btnExpired.setText(getString(R.string.prompt_courier_expired) + s);
            }
        });
        deliverViewmodel.model.getUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvExplain.setText(getString(R.string.prompt_courier_gerrt) + s);
            }
        });
        deliverViewmodel.model.getBoxUsableNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                tvBoxSurplus.setText(getString(R.string.prompt_courier_surplus)+s);
            }
        });
        deliverViewmodel.model.getSmallBoxUsableNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                btnSmallBox.setText(s + getString(R.string.prompt_courier_small_box));
            }
        });
        deliverViewmodel.model.getMiddleBoxUsableNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                btnMiddleBox.setText(s + getString(R.string.prompt_courier_middle_box));
            }
        });
        deliverViewmodel.model.getLargeBoxUsableNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                btnLargeBox.setText(s + getString(R.string.prompt_courier_large_box));
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

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        ViewModelProviders.of((FragmentActivity) getActivity()).get(MainViewmodel.class).mainModel.getCurrentFragment().setValue(NavigateHomeFragment.class);
    }

    @OnClick(R.id.btn_expired)
    public void onBtnExpiredClicked() {
        deliverViewmodel.getInBoxDelivery();
    }

    @OnClick(R.id.btn_large_box)
    public void onBtnLargeBoxClicked() {
        if (deliverViewmodel.model.getLargeBoxUsableNum().getValue() > 0) {
            deliverViewmodel.initPackagePage(2);
        }
    }

    @OnClick(R.id.btn_middle_box)
    public void onBtnMiddleBoxClicked() {
        if (deliverViewmodel.model.getMiddleBoxUsableNum().getValue() > 0) {
            deliverViewmodel.initPackagePage(1);
        }
    }

    @OnClick(R.id.btn_small_box)
    public void onBtnSmallBoxClicked() {
        if (deliverViewmodel.model.getSmallBoxUsableNum().getValue()>0) {
            deliverViewmodel.initPackagePage(0);

        }
    }
}
