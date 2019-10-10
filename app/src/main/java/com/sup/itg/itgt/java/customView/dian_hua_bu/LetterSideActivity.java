package com.sup.itg.itgt.java.customView.dian_hua_bu;

import android.os.Message;
import android.widget.Toast;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

public class LetterSideActivity extends ItgHandlerActivity {

    private LetterSideView letterSideView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_letter_side;
    }

    @Override
    public void initView() {

        letterSideView = findViewById(R.id.letterView);
    }

    @Override
    public void initData() {
        letterSideView.addOnTouchLetterListener(new LetterSideView.OnTouchLetterListener() {
            @Override
            public void touchLetter(String touch) {
                Toast.makeText(LetterSideActivity.this,touch,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {

    }


}
