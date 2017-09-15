package com.wzx.contractmvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzx.contractmvp.R;
import com.wzx.contractmvp.utils.DeviceUtils;

import java.util.List;

import static android.view.Gravity.CENTER;

/**
 * 可向下展开view
 * Created by Administrator on 2017/8/4.
 */

public class DropMenuView extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器，包含popupMenuViews，maskView
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;

    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //tab字体大小
    private int menuTextSize = 14;

    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;

    private float menuHeighPercent = 0.5f;

    private int tabheight = dpTpPx(36);//tabheight默认36dp


    public DropMenuView(Context context) {
        super(context, null);
    }

    public DropMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;
        int underlineColor = 0xffcccccc;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropMenuView);
        underlineColor = a.getColor(R.styleable.DropMenuView_ddunderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.DropMenuView_dddividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.DropMenuView_ddtextSelectedColor, textSelectedColor);
        textUnselectedColor = a.getColor(R.styleable.DropMenuView_ddtextUnselectedColor, textUnselectedColor);
        menuBackgroundColor = a.getColor(R.styleable.DropMenuView_ddmenuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.DropMenuView_ddmaskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.DropMenuView_ddmenuTextSize, menuTextSize);
        menuSelectedIcon = a.getResourceId(R.styleable.DropMenuView_ddmenuSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a.getResourceId(R.styleable.DropMenuView_ddmenuUnselectedIcon, menuUnselectedIcon);
        menuHeighPercent = a.getFloat(R.styleable.DropMenuView_ddmenuMenuHeightPercent, menuHeighPercent);
        tabheight = a.getDimensionPixelSize(R.styleable.DropMenuView_ddtabHeight, tabheight);
        Log.d("自定义view",""+tabheight);

        a.recycle();

        //初始化tabMenuView并添加到tabMenuView
        tabMenuView = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setBackgroundColor(menuBackgroundColor);
        tabMenuView.setLayoutParams(params);
        addView(tabMenuView, 0);


        //为tabMenuView添加下划线
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(0.5f)));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);

        //初始化containerView并将其添加到DropMenuView
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(containerView, 2);

    }

    /**
     * 初始化DropMenuView
     *
     * @param tabTexts
     * @param popupViews
     */
    public void setDropMenuView(@NonNull List<String> tabTexts, @NonNull List<View> popupViews, View contentView) {
        int viewposition = 0;
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }

        if(contentView != null){
            containerView.addView(contentView, viewposition);
            viewposition ++;
        }

        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts, i);
        }

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(v -> closeMenu());
        containerView.addView(maskView, viewposition);
        viewposition ++;
        maskView.setVisibility(GONE);
        if (containerView.getChildAt(2) != null) {
            containerView.removeViewAt(2);
        }

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (DeviceUtils.getScreenSize(getContext()).y * menuHeighPercent)));
        popupMenuViews.setVisibility(GONE);
        containerView.addView(popupMenuViews, viewposition);

        for (int i = 0; i < popupViews.size(); i++) {
            popupViews.get(i).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(popupViews.get(i), i);
        }

    }

    private void addTab(@NonNull List<String> tabTexts, int i) {
        LinearLayout tabView = new LinearLayout(getContext());
        tabView.setLayoutParams(new LayoutParams(0, tabheight, 1.0f));
        tabView.setOrientation(HORIZONTAL);
        tabView.setGravity(CENTER);


        TextView title = new TextView(getContext());
        title.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tabheight));
        title.setSingleLine();
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setGravity(CENTER);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        title.setTextColor(textUnselectedColor);
        title.setPadding(dpTpPx(5), dpTpPx(3), dpTpPx(5), dpTpPx(3));
        title.setText(tabTexts.get(i));

        ImageView state = new ImageView(getContext());
        state.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        state.setImageDrawable(ContextCompat.getDrawable(getContext(), menuUnselectedIcon));
        state.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tabView.addView(title);
        tabView.addView(state);

        tabView.setOnClickListener(v -> switchMenu(tabView));

        tabMenuView.addView(tabView);

        //添加分割线
        if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            LayoutParams params = new LayoutParams(1, LayoutParams.MATCH_PARENT);
            params.setMargins(0, 22, 0, 22);
            view.setLayoutParams(params);
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    public void setTabText(String text) {
        if (current_tab_position != -1) {
            ((TabView) tabMenuView.getChildAt(current_tab_position)).setText(text);
        }
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
            tabMenuView.getChildAt(i).setClickable(clickable);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {

        if (current_tab_position != -1) {
            LinearLayout tab = (LinearLayout) tabMenuView.getChildAt(current_tab_position);
            ((TextView) tab.getChildAt(0)).setTextColor(textUnselectedColor);
            ((ImageView) tab.getChildAt(1)).setImageDrawable(ContextCompat.getDrawable(getContext(), menuUnselectedIcon));

            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.drop_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.drop_mask_out));
            current_tab_position = -1;
        }

    }

    /**
     * DropMenuView是否处于可见状态
     *
     * @return
     */
    public boolean isShowing() {
        return current_tab_position != -1;
    }

    /**
     * 切换菜单
     *
     * @param target
     */
    private void switchMenu(View target) {
        System.out.println(current_tab_position);
        for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
            if (target == tabMenuView.getChildAt(i)) {
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        popupMenuViews.setVisibility(View.VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.drop_menu_in));
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.drop_mask_in));
                        popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    current_tab_position = i;

                    LinearLayout tab = (LinearLayout) tabMenuView.getChildAt(i);
                    ((TextView) tab.getChildAt(0)).setTextColor(textSelectedColor);
                    ((ImageView) tab.getChildAt(1)).setImageDrawable(ContextCompat.getDrawable(getContext(), menuSelectedIcon));
                }
            } else {
                LinearLayout tab = (LinearLayout) tabMenuView.getChildAt(i);
                ((TextView) tab.getChildAt(0)).setTextColor(textUnselectedColor);
                ((ImageView) tab.getChildAt(1)).setImageDrawable(ContextCompat.getDrawable(getContext(), menuUnselectedIcon));

                popupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
