package com.marsjiang.mystatebutton;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 自定义多种状态的按钮
 * Created by Jiang on 2017-07-15.
 */

public class MyStateButton extends FrameLayout {
    private Context context;
    private Button button;
    private ProgressBar progressBar;
    private ButtonClickListener buttonClickListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progressBar.clearAnimation();
                    progressBar.setVisibility(View.GONE);
                    button.setTextColor(getResources().getColor(R.color.colorWhite));
                    button.setText(msg.obj.toString());
                    button.setBackgroundResource(R.drawable.unclickshape);
                    Toast.makeText(context, "关注失败！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    progressBar.setVisibility(View.GONE);
                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                    button.setText(msg.obj.toString());
                    button.setBackgroundResource(R.drawable.clickshape);
                    Toast.makeText(context, "关注成功！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public interface ButtonClickListener {
        void innerClick();
    }

    //点击监听
    public void setOnInnerClickeListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    //完成方法
    public void setOnInnerFinish(String text) {
        Message message = Message.obtain();
        message.obj = text;
        message.what = 1;
        handler.sendMessage(message);
    }

    //失败完成方法
    public void setOnInnerUnFinish(final String text) {
        Message message = Message.obtain();
        message.obj = text;
        message.what = 0;
        handler.sendMessage(message);
    }

    public MyStateButton(Context context) {
        super(context);
        this.context = context;
    }

    public MyStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 初始化布局
     */
    private void initView() {
        int totalWidth = getMeasuredWidth();
        int totalHeight = getMeasuredHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (child instanceof Button) {
                button = (Button) getChildAt(i);
                //摆放子View，参数分别是子View矩形区域的左、上、右、下边
                child.layout(0, 0, totalWidth, totalHeight);
            }

            if (getChildAt(i) instanceof ProgressBar) {
                progressBar = (ProgressBar) getChildAt(i);
                //摆放子View，参数分别是子View矩形区域的左、上、右、下边
                child.layout(0, 0, totalWidth, totalHeight);
            }

        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("已关注".equals(button.getText().toString().trim())) {
                    return;
                }
                if (buttonClickListener != null) {
                    buttonClickListener.innerClick();
                    progressBar.setVisibility(View.VISIBLE);
                    button.setText("");
                }
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int a = childView.getMeasuredWidth();
            int b = childView.getMeasuredHeight();
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        initView();
    }
}
