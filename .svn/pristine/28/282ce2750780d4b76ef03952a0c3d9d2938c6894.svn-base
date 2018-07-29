package com.hzdongcheng.parcellocker.views.deliver;


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

import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.DeliverViewmodel;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverOpenedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverOpenedFragment extends WrapperFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_open_box_tips)
    TextView tvOpenBoxTips;
    @BindView(R.id.bt_deliver_cancle)
    Button btDeliverCancle;
    @BindView(R.id.bt_deliver_continue)
    Button btDeliverContinue;
    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;

    public DeliverOpenedFragment() {

    }

    public static DeliverOpenedFragment newInstance(String param1, String param2) {
        DeliverOpenedFragment fragment = new DeliverOpenedFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_opened, container, false);
        unbinder = ButterKnife.bind(this, view);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        deliverViewmodel.model.getBoxName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (deliverViewmodel.model.getOperationType().getValue()== SysDict.OPERATION_TYPE_DELIVER)
                    tvOpenBoxTips.setText(s + getString(R.string.prompt_courier_openbox_success));
                else
                    tvOpenBoxTips.setText(s + getString(R.string.prompt_recycler_openbox_success));
            }
        });
        deliverViewmodel.model.getOperationType().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (deliverViewmodel.model.getOperationType().getValue()== SysDict.OPERATION_TYPE_DELIVER) {
                    tvTitle.setText(getString(R.string.title_courier_home));
                    btDeliverCancle.setText(getString(R.string.prompt_courier_cancle));
                    btDeliverContinue.setText(getString(R.string.prompt_courier_continue));
                }else {
                    tvTitle.setText(getString(R.string.title_courier_recycler));
                    btDeliverCancle.setText(getString(R.string.prompt_recycler_cancle));
                    btDeliverContinue.setText(getString(R.string.prompt_recycler_continue));
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_deliver_cancle)
    public void onBtDeliverCancleClicked() {
        if (deliverViewmodel.model.getOperationType().getValue() == SysDict.OPERATION_TYPE_DELIVER) {
            deliverViewmodel.initDeliveryPage();
            deliverViewmodel.model.getCurrentFragment().postValue(DeliverHomeFragment.class);
        } else {
            deliverViewmodel.model.getCurrentFragment().postValue(DeliverRecordFragment.class);
        }
    }

    @OnClick(R.id.bt_deliver_continue)
    public void onBtDeliverContinueClicked() {
        if (deliverViewmodel.model.getOperationType().getValue() == SysDict.OPERATION_TYPE_DELIVER)
            deliverViewmodel.ensureDelivery();
        else
            deliverViewmodel.ensureRecovery();
    }
}
