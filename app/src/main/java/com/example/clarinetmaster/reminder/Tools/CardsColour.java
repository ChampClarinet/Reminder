package com.example.clarinetmaster.reminder.Tools;

import android.util.Log;

import com.example.clarinetmaster.reminder.R;

import java.util.Calendar;

public class CardsColour {

    /**
     * to set cardView color, use cardView.setBackgroundResource(colorID);
     */

    private static CardsColour instance = null;
    private static final int far = R.color.codeGreen;
    private static final int quiteNear = R.color.codeYellow;
    private static final int near = R.color.codeOrange;
    private static final int urgent = R.color.codeRed;
    private static final int passed = R.color.codeGrey;

    public static CardsColour getInstance(){
        if(instance == null) instance = new CardsColour();
        return instance;
    }

    public static int getColour(Calendar date){
        long remains = Utils.getRemainingTime(date);
        if(remains < 0) return passed;
        remains /=  24 * 60 * 60 * 1000;
        if(remains < 2) return urgent;
        if(remains < 4) return near;
        if(remains < 7) return quiteNear;
        return far;
    }

}