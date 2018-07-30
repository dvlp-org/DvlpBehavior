package news.dvlp.testbehavior;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import news.dvlp.testbehavior.utils.BarUtils;
import news.dvlp.testbehavior.utils.ScreenUtils;
import news.dvlp.testbehavior.utils.SizeUtils;
import news.dvlp.testbehavior.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private CoordinatorLayout top_coord;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private boolean isFold;//判断首页控件折叠状态,true为折叠
    private TextView tv_search;
    private LinearLayout linear_search;
    private TextView tv_phb,tv_bsy,tv_kh,tv_jz,tv_hd;
    private int maxSearchWidth;// 搜索扭的最大
    private ImageView ivHdTemp;// 临时变量不可用，临时标记活动图标
    private int maxFunctionWidth;// 顶部单位最大宽度
    private int maxTextHeight;//最大文字高度
    private LinearLayout linear_phb,linear_bsy,linear_kh,linear_jz,linear_hd;
    private Toolbar toolbar;
    private int maxToolbarWidth;// 最大toolbar宽度
    private View view_scroll;
    private final int MIN_SEARCH_WIDTH = SizeUtils.dp2px(40); // 最小的搜索宽度
    private int minIconWidth;//图标最小宽度
    private final int MAX_TOP_MARGIN = SizeUtils.dp2px(40);//最大顶部距离
    private final int TOP_ICON_NUMBER = 5;//功能图标个数

    private LinearLayout llTopView;
    private LinearLayout ll_head;
    private final int MAX_VERTICAL = 281;//最大偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Utils.init(this);
        top_coord=findViewById(R.id.top_coord);
        collapsingToolbarLayout=findViewById(R.id.toolbar_layout);
        mAppBarLayout=findViewById(R.id.app_bar);
        tv_search=findViewById(R.id.tv_search);
        linear_search=findViewById(R.id.linear_search);
        tv_phb=findViewById(R.id.tv_phb);
        tv_bsy=findViewById(R.id.tv_bsy);
        tv_kh=findViewById(R.id.tv_kh);
        tv_jz=findViewById(R.id.tv_jz);
        tv_hd=findViewById(R.id.tv_hd);
        linear_phb=findViewById(R.id.linear_phb);
        linear_bsy=findViewById(R.id.linear_bsy);
        linear_kh=findViewById(R.id.linear_kh);
        linear_jz=findViewById(R.id.linear_jz);
        linear_hd=findViewById(R.id.linear_hd);
        toolbar=findViewById(R.id.toolbar);
        view_scroll=findViewById(R.id.view_scroll);
        llTopView=findViewById(R.id.home_ll_top);
        ll_head=findViewById(R.id.ll_head);


        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        lp.setMargins(0, BarUtils.getStatusBarHeight(this), 0, 0);
        lp.setMargins(0, 0, 0, 0);

        top_coord.setLayoutParams(lp);
        // 初始化
        initScroll();
    }

    // 初始化滑动效果数据
    private void initScroll() {
        // 滑动效果折叠布局实现
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#1984D1"));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int _verticalOffset = -1; // 避免重复执行

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (_verticalOffset == verticalOffset) return;
                _verticalOffset = verticalOffset;
                // 初始化单位数据
                measureInitView();
                if (verticalOffset == 0) {//张开
                    isFold = false;//打开
                    // 搜索按钮
                    tv_search.setVisibility(View.VISIBLE);
                    linear_search.setBackgroundResource(R.drawable.home_search_circle_bg_on);
                    setWidthByLayoutParams(linear_search, maxSearchWidth);
                    // 功能菜单
                    tv_phb.setVisibility(View.VISIBLE);
                    tv_bsy.setVisibility(View.VISIBLE);
                    tv_kh.setVisibility(View.VISIBLE);
                    tv_jz.setVisibility(View.VISIBLE);
                    tv_hd.setVisibility(View.VISIBLE);
                    setHdTopVis(true);
                    setHeightByLayoutParams(tv_phb, maxTextHeight);
                    setHeightByLayoutParams(tv_bsy, maxTextHeight);
                    setHeightByLayoutParams(tv_kh, maxTextHeight);
                    setHeightByLayoutParams(tv_jz, maxTextHeight);
                    setHeightByLayoutParams(tv_hd, maxTextHeight);
                    setWidthByLayoutParams(linear_phb, maxFunctionWidth);
                    setWidthByLayoutParams(linear_bsy, maxFunctionWidth);
                    setWidthByLayoutParams(linear_kh, maxFunctionWidth);
                    setWidthByLayoutParams(linear_jz, maxFunctionWidth);
                    setWidthByLayoutParams(linear_hd, maxFunctionWidth);
                    setWidthByLayoutParams(toolbar, maxToolbarWidth);
                    setTopMarginByFrameParams(view_scroll, 0);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//收缩
                    isFold = true;//收缩
                    // 搜索按钮
                    tv_search.setVisibility(View.GONE);
                    linear_search.setBackgroundResource(R.drawable.home_search_circle_bg);
                    setWidthByLayoutParams(linear_search, MIN_SEARCH_WIDTH);
                    setHdTopVis(false);//顶部活动按钮
                    // 功能菜单
                    setWidthByLayoutParams(linear_phb, minIconWidth);
                    setWidthByLayoutParams(linear_bsy, minIconWidth);
                    setWidthByLayoutParams(linear_kh, minIconWidth);
                    setWidthByLayoutParams(linear_jz, minIconWidth);
                    setWidthByLayoutParams(linear_hd, minIconWidth);
                    setWidthByLayoutParams(toolbar, Math.abs((maxToolbarWidth - maxSearchWidth) + MIN_SEARCH_WIDTH));
                    setHeightByLayoutParams(tv_phb, 0);
                    setHeightByLayoutParams(tv_bsy, 0);
                    setHeightByLayoutParams(tv_kh, 0);
                    setHeightByLayoutParams(tv_jz, 0);
                    setHeightByLayoutParams(tv_hd, 0);
                    setTopMarginByFrameParams(view_scroll, MAX_TOP_MARGIN);
                } else {
                    // 功能菜单
                    tv_search.setVisibility(View.GONE);
                    linear_search.setBackgroundResource(R.drawable.home_search_circle_bg);
                    tv_phb.setVisibility(View.INVISIBLE);
                    tv_bsy.setVisibility(View.INVISIBLE);
                    tv_kh.setVisibility(View.INVISIBLE);
                    tv_jz.setVisibility(View.INVISIBLE);
                    tv_hd.setVisibility(View.INVISIBLE);
                    setHdTopVis(false);//顶部活动按钮
                    processRun(Math.abs(verticalOffset));
                }
            }
        });
        // 初始化功能View
        maxFunctionWidth = ScreenUtils.getScreenWidth() / TOP_ICON_NUMBER;
        if (llTopView.getChildCount() > 0) {
            for (int i = 0; i < llTopView.getChildCount(); i++) {
                LinearLayout childView = (LinearLayout) llTopView.getChildAt(i);
                childView.getLayoutParams().width = maxFunctionWidth;
            }
        }
    }

    // 设置view的宽度通过LayoutParams
    private void setWidthByLayoutParams(View view, int mWidth) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = mWidth;
            view.setLayoutParams(layoutParams);
        }
    }

    // 设置view的宽度通过LayoutParams
    private void setHeightByLayoutParams(View view, int height) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }
    // 计算初始化单位信息
    private void measureInitView() {
        if (maxSearchWidth < 1) maxSearchWidth = measureViewWidth(linear_search);
        if (minIconWidth < 1)
            minIconWidth = (measureViewWidth(ll_head) - SizeUtils.dp2px(70)) / TOP_ICON_NUMBER;
        if (maxTextHeight < 1) maxTextHeight = measureViewHeight(tv_phb);
        if (maxToolbarWidth < 1) maxToolbarWidth = measureViewWidth(toolbar);
    }
    // 处理活动顶部显示和隐藏
    public void setHdTopVis(boolean vis) {
        if (ivHdTemp != null) ivHdTemp.setVisibility(vis ? View.VISIBLE : View.GONE);
    }
    // 设置view的TopMargin通过FrameParams
    private void setTopMarginByFrameParams(View view, int topMargin) {
        if (view != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = topMargin;
            view.setLayoutParams(layoutParams);
        }
    }
    // 设置相关单位的数据转换
    private void processRun(int mVertical) {
        try {
            //设置搜索扭变化
            int xIndex = measureIndex(mVertical, maxSearchWidth, MIN_SEARCH_WIDTH);
            setWidthByLayoutParams(linear_search, xIndex);
            //设置菜单变化
            xIndex = measureIndex(mVertical, maxFunctionWidth, minIconWidth);
            setWidthByLayoutParams(linear_phb, xIndex);
            setWidthByLayoutParams(linear_bsy, xIndex);
            setWidthByLayoutParams(linear_kh, xIndex);
            setWidthByLayoutParams(linear_jz, xIndex);
            setWidthByLayoutParams(linear_hd, xIndex);
            // 设置toolbar
            xIndex = measureIndex(mVertical, maxToolbarWidth, Math.abs((maxToolbarWidth - maxSearchWidth) + MIN_SEARCH_WIDTH));
            setWidthByLayoutParams(toolbar, xIndex);
            // 设置文字
            xIndex = measureIndex(mVertical, maxTextHeight, 0);
            setHeightByLayoutParams(tv_phb, xIndex);
            setHeightByLayoutParams(tv_bsy, xIndex);
            setHeightByLayoutParams(tv_kh, xIndex);
            setHeightByLayoutParams(tv_jz, xIndex);
            setHeightByLayoutParams(tv_hd, xIndex);
            xIndex = Math.abs(MAX_TOP_MARGIN - measureIndex(mVertical, MAX_TOP_MARGIN, 0));
            setTopMarginByFrameParams(view_scroll, xIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ///计算view的宽度
    private int measureViewWidth(View view) {
        return view.getWidth();
    }
    ///计算view的高度
    private int measureViewHeight(View view) {
        return view.getHeight();
    }
    // 计算变化单位
    private int measureIndex(float mVertical, float maxIndex, float minIndex) {
        if (mVertical > 0) {
            float xWidth = Math.abs(maxIndex - minIndex);
            float measureWidth = Math.abs(maxIndex - xWidth / MAX_VERTICAL * mVertical);
            measureWidth = measureWidth < minIndex ? minIndex : measureWidth;
            return (int) measureWidth;
        }
        return (int) minIndex;
    }
}
