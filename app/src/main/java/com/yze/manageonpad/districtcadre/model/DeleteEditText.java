package com.yze.manageonpad.districtcadre.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */


public class DeleteEditText extends android.support.v7.widget.AppCompatEditText {
    private Drawable mDrawable;
    private boolean isfocus;

    public DeleteEditText(Context context) {
        super(context);
        init();
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawables[] = this.getCompoundDrawables();
        mDrawable = drawables[2];

        this.addTextChangedListener(new TextWatcherImpl());
        this.setOnFocusChangeListener(new OnFocusChangeImpl());
        setClearDrawableVisible(false);
    }

    private class OnFocusChangeImpl implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            isfocus = hasFocus;
            if (isfocus) {
                boolean isNull = getText().length() > 0 ? true : false;
                setClearDrawableVisible(isNull);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }

    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean isNull = getText().length() > 0 ? true : false;
            setClearDrawableVisible(isNull);
        }
    }

    private void setClearDrawableVisible(boolean isNull) {
        Drawable drawable;
        if (isNull) {
            drawable = mDrawable;
        } else {
            drawable = null;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], drawable,
                getCompoundDrawables()[3]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int length1 = getWidth() - getPaddingRight();
                int length2 = getWidth() - getTotalPaddingRight();
                if (event.getX() > length2 && event.getX() < length1) {
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
