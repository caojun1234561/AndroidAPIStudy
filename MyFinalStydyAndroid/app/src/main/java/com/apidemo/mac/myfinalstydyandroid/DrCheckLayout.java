package com.apidemo.mac.myfinalstydyandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cqj on 2018/1/29.
 */

public class DrCheckLayout extends FrameLayout implements DRCheckItemLayout.OnItemSelecedListener {
    private Context context;

    private int textDefaultColor;
    private int textSelectedColor;
    private int textSize;//字体大小
    private int textGravity;//文字的位置

    private int drawableRes;
    private int drawableGravity;//图片的位置
    private boolean multi = false;//是否是多选
    private float minHeight;

    private List<ItemModel> modelList;

    private List<ItemModel> selectedList;

    private LinearLayout llContent;


    public DrCheckLayout(Context context) {
        this(context, null);
    }

    public DrCheckLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrCheckLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initField(attrs);
        init(context);
        initData();
        updateUI();
    }

    public void initField(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrCheckLayout);
        this.textSize = typedArray.getInt(R.styleable.DrCheckLayout_textSize, 15);
        this.textDefaultColor = typedArray.getResourceId(R.styleable.DrCheckLayout_textDefaultColor, 0);
        this.textSelectedColor = typedArray.getResourceId(R.styleable.DrCheckLayout_textSelectedColor, 0);
        this.textGravity = typedArray.getInt(R.styleable.DrCheckLayout_textGravity, 0);
        this.drawableRes = typedArray.getResourceId(R.styleable.DrCheckLayout_drawableRes, 0);
        this.drawableGravity = typedArray.getInt(R.styleable.DrCheckLayout_drawableGravity, 0);
        this.multi = typedArray.getBoolean(R.styleable.DrCheckLayout_multi, true);
        this.minHeight = typedArray.getDimension(R.styleable.DrCheckLayout_minHeight, 0);
        typedArray.recycle();
    }


    private void initData() {
        MyModel myModel;
        selectedList = new ArrayList<>();
        modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            myModel = new MyModel(i, String.valueOf(i) + "哈啊", false);
            modelList.add(myModel);
        }
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_checkbox, this);
        llContent = findViewById(R.id.ll_content);
    }


    private void updateUI() {
        for (ItemModel model : modelList) {
            llContent.addView(newItem(model));
        }
    }

    private View newItem(ItemModel itemModel) {
        DRCheckItemLayout layout = new DRCheckItemLayout(context, minHeight, textDefaultColor, textSelectedColor, textSize, textGravity, drawableRes, drawableGravity, multi, itemModel);
        layout.setOnItemSelecedListener(this);
        return layout;
    }

    public void reSetView() {
        if (llContent.getChildCount() != 0) {
            for (int i = 0; i < llContent.getChildCount() - 1; i++) {
                DRCheckItemLayout layout = (DRCheckItemLayout) llContent.getChildAt(i);
                layout.resetView();
            }
        }
    }

    @Override
    public void onItemSelected(boolean selected, ItemModel itemModel) {
        if (selected) {
            if (!multi) {
                if (selectedList.size() > 0) {
                    ItemModel model = selectedList.get(0);
                    int location = modelList.indexOf(model);
                    DRCheckItemLayout layout = (DRCheckItemLayout) llContent.getChildAt(location);
                    layout.resetView();
                    selectedList.clear();
                }
            }
            selectedList.add(itemModel);
        } else {
            selectedList.remove(itemModel);
        }

        Log.e("size", "is:" + selectedList.size());
    }

    public class MyModel implements ItemModel {
        int id = 0;
        String name = null;

        public MyModel(int id, String name, boolean selected) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void setId(int id) {
            this.id = id;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public interface OnSelectFinishListener {
        List<ItemModel> onSelectFinish();
    }

    public interface ItemModel {

        void setId(int id);

        void setName(String name);

        int getId();

        String getName();

    }


}
