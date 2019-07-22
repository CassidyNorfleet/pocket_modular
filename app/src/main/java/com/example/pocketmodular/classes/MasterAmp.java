package com.example.pocketmodular.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pocketmodular.MyApplication;
import com.example.pocketmodular.R;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;

import org.puredata.core.PdBase;

public class MasterAmp extends FrameLayout {
    /*vars*/
    private MyApplication mApplication;
    private Boolean isCollapsed;

    /*ui*/
    private TextView mMasterAmpNameText;
    private FrameLayout mMasterAmpNameLayout;
    private LinearLayout mMasterAmpControls;
    private RangeSeekBar mMasterSeekBar;

    public MasterAmp(Context context) {
        super(context);
        /*vars*/
        mApplication = ((MyApplication)context.getApplicationContext());
        isCollapsed = false;

        /*ui*/
        LayoutInflater.from(context).inflate(R.layout.layout_masteramp, this);
        mMasterAmpNameText = findViewById(R.id.masterAmpNameText);
        mMasterAmpNameLayout = findViewById(R.id.masterAmpNameFrameLayout);
        mMasterAmpControls = findViewById(R.id.masterAmpControlsLinearLayout);
        mMasterSeekBar = findViewById(R.id.masterSeekBar);

        mMasterAmpNameText.setText("master");
        mMasterAmpNameLayout.setLayoutParams(new LinearLayout.LayoutParams(mApplication.getModuleNameWidth(), mMasterAmpNameLayout.getLayoutParams().height));
        mMasterAmpControls.setLayoutParams(new LinearLayout.LayoutParams(mApplication.getModuleControlsWidth()/2, mMasterAmpControls.getLayoutParams().height));

        mMasterSeekBar.setIndicatorTextDecimalFormat("0.0");

        /*OnClick*/
        mMasterAmpNameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int moduleSize = (isCollapsed)? mApplication.getModuleControlsWidth()/2 : 0;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(moduleSize, mMasterAmpControls.getHeight());
                mMasterAmpControls.setLayoutParams(params);
                isCollapsed = !isCollapsed;
            }
        });

        mMasterSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                PdBase.sendFloat("master_amp_GAIN", leftValue/100);

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {}
            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {}
        });

    }
}