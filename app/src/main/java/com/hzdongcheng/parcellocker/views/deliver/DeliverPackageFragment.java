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
import android.widget.EditText;
import android.widget.TextView;

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
 * Use the {@link DeliverPackageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverPackageFragment extends WrapperFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_go_back)
    Button btnGoBack;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    Unbinder unbinder;
    DeliverViewmodel deliverViewmodel;
    @BindView(R.id.et_package_id)
    EditText etPackageId;
    @BindView(R.id.et_customer_mobile)
    EditText etCustomerMobile;

    public DeliverPackageFragment() {
        // Required empty public constructor
    }

    public static DeliverPackageFragment newInstance(String param1, String param2) {
        DeliverPackageFragment fragment = new DeliverPackageFragment();
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
        View view = inflater.inflate(R.layout.fragment_deliver_package, container, false);
        unbinder = ButterKnife.bind(this, view);
        etPackageId.requestFocus();
        etPackageId.setFocusableInTouchMode(true);
        deliverViewmodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(DeliverViewmodel.class);
        deliverViewmodel.model.getErrorTips().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvTips.setText(s);

            }
        });
        deliverViewmodel.model.getPackageID().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.length()>0) {
                    etPackageId.setText(s);
                    etCustomerMobile.requestFocus();
                    etCustomerMobile.setFocusableInTouchMode(true);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        deliverViewmodel.getScannerData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        deliverViewmodel.cancelScanner();
    }

    @OnClick(R.id.btn_go_back)
    public void onBtnGoBackClicked() {
        deliverViewmodel.initDeliveryPage();
        deliverViewmodel.model.getCurrentFragment().postValue(DeliverHomeFragment.class);
    }

    @OnClick(R.id.btn_confirm)
    public void onBtnConfirmClicked() {
        String packgeId=etPackageId.getText().toString();
        if (packgeId.isEmpty()) {
            tvTips.setText(getString(R.string.error_courier_package_003));
            return;
        }
        if (packgeId.length() < 4 ||packgeId.length()  > 30) {
            tvTips.setText(R.string.error_courier_package_002);
            return;
        }
        deliverViewmodel.checkOrder(packgeId);
        if (deliverViewmodel.model.localCheck) {
            String customerMobile = etCustomerMobile.getText().toString();
            if (customerMobile.isEmpty()) {
                tvTips.setText(getString(R.string.error_courier_telphone_001));
                return;
            }
            if (customerMobile.length() != 11) {
                tvTips.setText(getString(R.string.error_courier_telphone_003));
                return;
            }
            deliverViewmodel.model.getPackageID().setValue(etPackageId.getText().toString());
            deliverViewmodel.model.getCustomerMobile().setValue(customerMobile);
            deliverViewmodel.checkCustomerMobile();
        }
    }
}
