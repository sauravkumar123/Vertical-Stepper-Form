package com.example.visitorbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private ViewPager slidepage;
    private LinearLayout linearLayout;
    private TextView[] dots;
    private SliderAdapter sliderAdapter;
    private FloatingActionButton button;
    private int currentpage=0;
    private TextView skip;
    ImageView back ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidepage=(ViewPager)findViewById(R.id.viewpager);
        button=(FloatingActionButton) findViewById(R.id.nextbutton);
        back=(ImageView)findViewById(R.id.arrowback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidepage.setCurrentItem(currentpage-1);

            }
        });
        skip=(TextView)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, OTPverification.class);
                startActivity(it);
                finish();

            }
        });
        linearLayout=(LinearLayout)findViewById(R.id.dotslayout);
        sliderAdapter= new SliderAdapter(this);
        slidepage.setAdapter(sliderAdapter);
        adddotsindicator(0);
        slidepage.addOnPageChangeListener(viewlistener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidepage.setCurrentItem(currentpage+1);

                if(currentpage==2) {
                    currentpage = 3;
                }
                if(currentpage==3) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent it = new Intent(MainActivity.this, OTPverification.class);
                            startActivity(it);
                            finish();
                        }
                    });
                }

            }
        });


    }

    public void adddotsindicator(int j){
        dots=new TextView[3];
        linearLayout.removeAllViews();
        for (int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colortransparentlight));
            linearLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[j].setTextColor(getResources().getColor(R.color.colortransparent));
        }
    }

    ViewPager.OnPageChangeListener viewlistener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            adddotsindicator(position);
            currentpage=position;

            if(position==0)
            {
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

            }
            else  if(position==dots.length-1)
                {
                    back.setEnabled(true);
                    back.setVisibility(View.VISIBLE);

                }
            else {
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
            }



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}