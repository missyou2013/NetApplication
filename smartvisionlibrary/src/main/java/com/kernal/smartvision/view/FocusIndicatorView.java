package com.kernal.smartvision.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.kernal.smartvision.inteface.FocusIndicator;

// A view that indicates the focus area or the metering area.
public class FocusIndicatorView extends View implements FocusIndicator {
	private Context context;
    public FocusIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void setDrawable(int resid) {
        setBackgroundDrawable(getResources().getDrawable(resid));
    }

    @Override
    public void showStart() {
//    	R.drawable.ic_focus_focusing
        setDrawable(context.getResources().getIdentifier("ic_focus_focusing", "drawable", context.getPackageName()));
    }

    @Override
    public void showSuccess() {
//    	ic_focus_focused
        setDrawable(context.getResources().getIdentifier("ic_focus_focused", "drawable", context.getPackageName()));
    }

    @Override
    public void showFail() {
//    	ic_focus_failed
        setDrawable(context.getResources().getIdentifier("ic_focus_failed", "drawable", context.getPackageName()));
    }

    @Override
    public void clear() {
        setBackgroundDrawable(null);
    }
}
