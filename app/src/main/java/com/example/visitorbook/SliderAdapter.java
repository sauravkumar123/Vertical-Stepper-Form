package com.example.visitorbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arraysss

    public int slidearr[]={
            R.drawable.bear_strong_dumbbell_gym_vector_preview,
            R.drawable.school2,
            R.drawable.download
    };

    public String heading[]={
            "Play The Beat",
            "Live The Life",
            "Capture The Moment"

    };
    public String slidetext[]={
            "Most begiiner producers earn make  creating by simple beats",
            "In our daily lives,we often rush tasks trying to get them finish",
            "You are not alone.You have unique ability to go to another world"


    };
    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView imageView=view.findViewById(R.id.imageview1);
        TextView title=view.findViewById(R.id.title1);
        TextView discriptions=view.findViewById(R.id.dis);

        imageView.setImageResource(slidearr[position]);
        title.setText(heading[position]);
        discriptions.setText(slidetext[position]);

        container.addView(view);

     return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
