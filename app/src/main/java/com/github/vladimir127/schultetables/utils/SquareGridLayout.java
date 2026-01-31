package com.github.vladimir127.schultetables.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class SquareGridLayout extends GridLayout {

    public SquareGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, widthSpec);
    }
}
