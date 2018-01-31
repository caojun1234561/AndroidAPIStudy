package com.apidemo.mac.myfinalstydyandroid.CheckBox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apidemo.mac.myfinalstydyandroid.R;

/**
 * Created by cqj on 2018/1/29.
 */

public class DRCheckItemLayout extends FrameLayout {
    private float minHeight;
    private int textDefaultColor;
    private int textSelectedColor;
    private int textSize;//字体大小
    private int textGravity;//文字的位置

    private int drawableRes;
    private int drawableGravity;//图片的位置
    private boolean multi = false;//是否是多选

    private Context context;
    private boolean selected;
    private DrCheckLayout.ItemModel itemModel;

    private TextView tvName;
    private CheckBox checkBox;
    private RelativeLayout llContent;
    private OnItemSelecedListener onItemSelecedListener;

    /**
     * @param context
     * @param textDefaultColor
     * @param textSelectedColor
     * @param textSize
     * @param textGravity
     * @param drawableRes
     * @param drawableGravity
     * @param multi
     * @param itemModel
     */
    public DRCheckItemLayout(@NonNull Context context, float minHeight, int textDefaultColor, int textSelectedColor, int textSize, int textGravity, int drawableRes, int drawableGravity, boolean multi, DrCheckLayout.ItemModel itemModel) {
        super(context);
        this.context = context;
        this.minHeight = minHeight;
        this.textDefaultColor = textDefaultColor;
        this.textSelectedColor = textSelectedColor;
        if (this.textSelectedColor == 0)
            this.textSelectedColor = textDefaultColor;
        this.textSize = textSize;
        this.textGravity = textGravity;
        this.drawableRes = drawableRes;
        this.drawableGravity = drawableGravity;
        this.multi = multi;
        this.itemModel = itemModel;
        initView();
    }

    public void setOnItemSelecedListener(OnItemSelecedListener onItemSelecedListener) {
        this.onItemSelecedListener = onItemSelecedListener;
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_checkbox_item, this);

        tvName = findViewById(R.id.name);
        checkBox = findViewById(R.id.box);
        llContent = findViewById(R.id.ll_content);
        if (minHeight != 0) {
            llContent.setMinimumHeight((int) minHeight);
        }
        llContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = !selected;
                if (onItemSelecedListener != null) {
                    onItemSelecedListener.onItemSelected(selected, itemModel);
                }
                updateBox();
                updateName();
            }
        });
        initName();
        initBox();
    }

    /**
     * 初始化
     */
    private void initName() {
        if (itemModel == null || itemModel.getName() == null) {
            tvName.setVisibility(GONE);
            return;
        }
        tvName.setVisibility(VISIBLE);
        tvName.setText(itemModel.getName());
        tvName.setTextSize(textSize);
        tvName.setTextColor(!selected ? context.getResources().getColor(textDefaultColor, null) : context.getResources().getColor(textSelectedColor, null));
        switch (textGravity) {
            case 0: {
                tvName.setGravity(Gravity.LEFT);
            }
            break;
            case 1: {
                tvName.setGravity(Gravity.RIGHT);
            }
            break;
            case 2: {
                tvName.setGravity(Gravity.CENTER);
            }
            break;
        }
    }

    /**
     * 刷新选择状态
     */
    private void updateName() {
        tvName.setTextColor(!selected ? context.getResources().getColor(textDefaultColor, null) : context.getResources().getColor(textSelectedColor, null));
    }

    private void initBox() {
        if (drawableRes == 0 || !multi) {
            checkBox.setVisibility(GONE);
            return;
        }
        checkBox.setClickable(false);
        checkBox.setVisibility(VISIBLE);
        checkBox.setButtonDrawable(context.getResources().getDrawable(drawableRes, null));
        checkBox.setChecked(selected);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) checkBox.getLayoutParams();
        switch (drawableGravity) {
            case 0: {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            break;
            case 1: {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            break;
        }

    }

    private void updateBox() {
        checkBox.setChecked(selected);
    }


    public void resetView() {
        selected = false;
        updateBox();
        updateName();
    }

    public interface OnItemSelecedListener {
        void onItemSelected(boolean selected, DrCheckLayout.ItemModel itemModel);
    }

    /**
     * 是否被选中
     *
     * @return
     */
    public boolean isSeleced() {
        return selected;
    }


}
