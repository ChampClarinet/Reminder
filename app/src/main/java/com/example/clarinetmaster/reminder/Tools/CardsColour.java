package com.example.clarinetmaster.reminder.Tools;

import com.example.clarinetmaster.reminder.R;

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

    public static int getColour(DateTime date){
        long remains = date.getRemainingTime();
        if(remains < 0) return passed;
        if(remains < 2) return urgent;
        if(remains < 4) return near;
        if(remains < 7) return quiteNear;
        return far;
    }

}