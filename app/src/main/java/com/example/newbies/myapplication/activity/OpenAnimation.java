package com.example.newbies.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.newbies.myapplication.R;
import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;


/**
 *
 * @author NewBies
 * @date 2017/12/4
 */
public class OpenAnimation extends BaseActivity {

    private  ImageView open_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.open_anim);
        initView();
        ImageView open_img = (ImageView) findViewById(R.id.open_img);
        initView();
        initListener();
        AlphaAnimation anima = new AlphaAnimation(0.1f, 1.0f);
        // 设置动画显示时间
        anima.setDuration(1500);
        open_img.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }


    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    public void initView() {
    }

    @Override
    public void initListener() {

    }
}
