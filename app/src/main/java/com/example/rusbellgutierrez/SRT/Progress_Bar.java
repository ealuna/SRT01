package com.example.rusbellgutierrez.SRT;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by Russbell on 22/03/2017.
 */

public class Progress_Bar {

    public void Progreso_Pre(FrameLayout frame, Button button,AlphaAnimation inAnimation){

        //experimental-->simulando metodo OnPreExecute
        button.setEnabled(false);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        frame.setAnimation(inAnimation);
        frame.setVisibility(View.VISIBLE);
        //termina metodo OnPreExecute
    }

    public void Progreso_Post(FrameLayout frame,Button button,AlphaAnimation outAnimation){

        //experimental-->simulando metodo OnPostExecute
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        frame.setAnimation(outAnimation);
        frame.setVisibility(View.GONE);
        button.setEnabled(true);
        //termina metodo OnPostExecute
    }
}
