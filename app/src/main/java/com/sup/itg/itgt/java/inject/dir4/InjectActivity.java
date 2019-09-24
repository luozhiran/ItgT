package com.sup.itg.itgt.java.inject.dir4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sup.itg.itgt.R;
import com.sup.itg.viewinject.ContentView;
import com.sup.itg.viewinject.OnClick;
import com.sup.itg.viewinject.ViewInject;
import com.sup.itg.viewinject.ViewInjectUtils;

@ContentView(R.layout.activity_inject)
public class InjectActivity extends AppCompatActivity {

    @ViewInject(R.id.btn1)
    private Button mBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);


    }


    @OnClick({R.id.btn1})
    public void clickBtnInvoked(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                break;
        }
    }




}
