package com.example.visitorbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OTPverification extends AppCompatActivity {
    private TextView title;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pverification);
        title=(TextView)findViewById(R.id.disotp);
        String text="We will send you an One Time Password on this mobile number";
        SpannableString ss=new SpannableString(text);

        StyleSpan bold=new StyleSpan(Typeface.BOLD);
        ss.setSpan(bold,20,37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(ss);

            button=(Button)findViewById(R.id.getotp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it1=new Intent(OTPverification.this,otpverify.class);
                   startActivity(it1);

                }
            });
    }
}