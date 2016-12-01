package com.example.administrator.mylittlesecretary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;

/**
 * Created by Administrator on 2016-08-21.
 */
public class EnterActivity_todo extends Activity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertodo);
        RadioGroup rd = (RadioGroup) this.findViewById(R.id.importance);
        rd.setAlpha(0);
        rd.setOnCheckedChangeListener(this); // 라디오버튼을 눌렸을때의 반응
    }

    public void onCheckedChanged(RadioGroup arg0, int arg1) { // 라디오버튼
        // TODO Auto-generated method stub
        ImageView iv = (ImageView) this.findViewById(R.id.importance_image);
        switch (arg1) {
            case R.id.importance_vr:
                iv.setImageResource(R.drawable.importance_verylow);
                break;
            case R.id.importance_r:
                iv.setImageResource(R.drawable.importance_low);
                break;
            case R.id.importance_m:
                iv.setImageResource(R.drawable.importance);
                break;
            case R.id.importance_h:
                iv.setImageResource(R.drawable.importance_high);
                break;
            case R.id.importance_vh:
                iv.setImageResource(R.drawable.importance_veryhigh);
                break;
        }
    }

}
