package com.hzdongcheng.parcellocker.views;

import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.hzdongcheng.bll.monitors.TimingTask;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.utils.KeyboardUtils;
import com.hzdongcheng.parcellocker.utils.TimerUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.viewmodel.MainViewmodel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TimerUtils.getInstance().onTouch();
//        if (KeyboardUtils.keyboardWindow!=null)
//            KeyboardUtils.keyboardWindow.dismiss();
        return super.dispatchTouchEvent(ev);
    }

    private void initView() {
        MainViewmodel viewmodel = ViewModelProviders.of(this).get(MainViewmodel.class);
        getLifecycle().addObserver(viewmodel);
        viewmodel.mainModel.getCurrentFragment().observe(this, new Observer<Class<? extends WrapperFragment>>() {
            @Override
            public void onChanged(@Nullable Class<? extends WrapperFragment> aClass) {
                if (aClass != null && getFragmentManager().findFragmentByTag(aClass.getName()) == null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    try {
                        ft.replace(R.id.fl_container, aClass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ft.commitAllowingStateLoss();
                }
            }
        });
    }

}
