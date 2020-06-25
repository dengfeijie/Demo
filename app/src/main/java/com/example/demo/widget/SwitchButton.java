package com.example.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.example.demo.R;

/**
 * @author dengfeijie
 * @description:
 * @date : 2020/5/25 23:03
 */
public class SwitchButton extends AppCompatImageView {

    private SwitchButton.OnCheckedChangeListener onCheckedChangedListener;// 监听选择变化
    private boolean isCheck = false;

    public SwitchButton(Context context) {
        super(context);
        init(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrt) {
        ViewGroup.LayoutParams
                vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(vl);
        setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        setImageResource(R.drawable.icon_close);
        setOnClickListener(listsner);
    }


    private View.OnClickListener listsner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onCheckedChangedListener != null) {
                setStatus(!isCheck);
                onCheckedChangedListener.onCheckedChanged(isCheck, (ImageView) v);
            }
        }
    };

    public boolean getIsCheck() {
        return isCheck;
    }


    public void setStatus(boolean status) {
        if (status) {
            setImageResource(R.drawable.icon_open);
            isCheck = true;
        } else {
            setImageResource(R.drawable.icon_close);
            isCheck = false;
        }

    }


    /**
     * 设置监听
     *
     * @param listener listener
     */
    public void setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener listener) {
        onCheckedChangedListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, ImageView v);
    }
}
