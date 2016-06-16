package com.github.nitrico.mystickyscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.nitrico.stickyscrollview.StickyScrollView;

public class MainActivity extends AppCompatActivity implements StickyScrollView.OnStickyScrollViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StickyScrollView sticky = (StickyScrollView) findViewById(R.id.sticky);
        sticky.addOnStickyScrollViewListener(this);
    }

    @Override
    public void onScrollChanged(int x, int y, int oldX, int oldY) {

    }

}
