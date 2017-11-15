package com.hotsun.mqxxgl.busi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hotsun.mqxxgl.R;

/**
 * Created by yuan on 2017-11-01.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    //顶部View
            private View topView;
    //第一个可见View
           int firstVisibleItem;
    //顶部View的高度
           int headerHeight;
    // listview 当前滚动状态；
            int scrollState;
   // 标记，当前是在listview最顶端摁下的；
            boolean isRefresh;
    // 摁下时的Y值；
           int startY;

    //底部View
    private View footerView;
    //ListView item个数
    int totalItemCount = 0;
    //最后可见的Item
    int lastVisibleItem = 0;
    //是否加载标示
    boolean isLoading = false;

            int state;// 当前的状态；
    final int NONE = 0;// 正常状态；
   final int PULL = 1;// 提示下拉状态；
    final int RELESE = 2;// 提示释放状态；
    final int REFLASHING = 3;// 刷新状态；

            public RefreshListView(Context context) {
               super(context);
               initView(context);
            }

            public RefreshListView(Context context, AttributeSet attrs) {
                super(context, attrs);
              initView(context);

            }

           public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
                initView(context);
           }

           /**
     * 初始化ListView
    */
            private void initView(Context context){
               LayoutInflater mInflater = LayoutInflater.from(context);
               topView = mInflater.inflate(R.layout.top, null);
                footerView = mInflater.inflate(R.layout.busi_include_page_footer, null);
                footerView.setVisibility(View.GONE);
                this.setOnScrollListener(this);
                //添加底部View
                this.addFooterView(footerView);

             measureView(topView);
               headerHeight = topView.getMeasuredHeight();
              //初始状态设置隐藏
              topPadding(-headerHeight);

                this.setOnScrollListener(this);
               this.addHeaderView(topView);
            }

            @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {

                this.scrollState = scrollState;

               //当滑动到底端，并滑动状态为 not scrolling
                if(lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE){

                        //设置可见
                        footerView.setVisibility(View.VISIBLE);
                        //加载数据
                        onPaginationListener.onPagination();

                }
           }

          /**
    * 通知父布局，占用的宽，高；
    *
     * @param view
    */
            private void measureView(View view) {
              ViewGroup.LayoutParams p = view.getLayoutParams();
               if (p == null) {
                       p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                       ViewGroup.LayoutParams.WRAP_CONTENT);
                   }
                int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
               int height;
                int tempHeight = p.height;
                if (tempHeight > 0) {
                        height = MeasureSpec.makeMeasureSpec(tempHeight,
                                        MeasureSpec.EXACTLY);
                   } else {
                       height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                   }
                view.measure(width, height);
            }

            /**
     * 设置header 布局 上边距；
    *
      * @param topPadding
     */
          private void topPadding(int topPadding) {
               topView.setPadding(topView.getPaddingLeft(), topPadding,
                              topView.getPaddingRight(), topView.getPaddingBottom());
              topView.invalidate();
           }

           @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
           int visibleItemCount, int totalItemCount) {
               this.firstVisibleItem = firstVisibleItem ;
           }

            @Override
   public boolean onTouchEvent(MotionEvent ev) {
              // TODO Auto-generated method stub
                switch (ev.getAction()) {
                  //往下
                    case MotionEvent.ACTION_DOWN:
                           if (firstVisibleItem == 0) {
                                    isRefresh = true;
                                   startY = (int) ev.getY();
                              }
                           break;
                    //移动
                   case MotionEvent.ACTION_MOVE:
                          onMove(ev);
                          break;
                   //向上
                  case MotionEvent.ACTION_UP:
                         if (state == RELESE) {
                                    state = REFLASHING;
                                   // 加载最新数据；
                                   reflashViewByState();
                                   onRefreshListener.onRefresh();
                             } else if (state == PULL) {
                                    state = NONE;
                                  isRefresh = false;
                                    reflashViewByState();
                               }
                            break;
                   }
             return super.onTouchEvent(ev);
            }

           /**
     * 判断移动过程操作；
    *
    * @param ev
    */
            private void onMove(MotionEvent ev) {
               if (!isRefresh) {
                       return;
                   }
              int tempY = (int) ev.getY();
              int space = tempY - startY;
                int topPadding = space - headerHeight;
                switch (state) {
                    case NONE:
                           if (space > 0) {
                                  state = PULL;
                                  reflashViewByState();
                            }
                         break;
                   case PULL:
                           topPadding(topPadding);
                            if (space > headerHeight + 30
                                   && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                               state = RELESE;
                                reflashViewByState();
                          }
                     break;
                   case RELESE:
                           topPadding(topPadding);
                          if (space < headerHeight + 30) {
                                    state = PULL;
                                   reflashViewByState();
                                } else if (space <= 0) {
                                state = NONE;
                                  isRefresh = false;
                                  reflashViewByState();
                              }
                        break;
                   }
          }

         /**
    * 根据当前状态，改变界面显示；
     */
            private void reflashViewByState() {
              TextView tip = (TextView) topView.findViewById(R.id.tip);

              ProgressBar progress = (ProgressBar) topView.findViewById(R.id.progress_refresh);
               RotateAnimation anim = new RotateAnimation(0, 180,
                               RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                               RotateAnimation.RELATIVE_TO_SELF, 0.5f);
               anim.setDuration(500);
               anim.setFillAfter(true);
              RotateAnimation anim1 = new RotateAnimation(180, 0,
                               RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                             RotateAnimation.RELATIVE_TO_SELF, 0.5f);
               anim1.setDuration(500);
               anim1.setFillAfter(true);
               switch (state) {
                   case NONE:

                           topPadding(-headerHeight);
                          break;

                    case PULL:

                          progress.setVisibility(View.GONE);
                          tip.setText("下拉可以刷新！");

                           break;
                   case RELESE:

                           progress.setVisibility(View.GONE);
                           tip.setText("松开可以刷新！");

                            break;
                  case REFLASHING:
                           topPadding(50);

                           progress.setVisibility(View.VISIBLE);
                          tip.setText("正在刷新...");

                           break;
                   }
           }

          private OnRefreshListener onRefreshListener;
  public void setOnRefreshListener(OnRefreshListener onRefreshListener){
               this.onRefreshListener = onRefreshListener;
           }

   /**
   * 加载数据接口
     * @author Administrator
    *
    */
   public interface OnRefreshListener{
       void onRefresh();
   }
    private OnPaginationListener onPaginationListener;
    public void setOnPaginationListener(OnPaginationListener onPaginationListener){
        this.onPaginationListener = onPaginationListener;
    }

    /**
     * 加载数据接口
     * @author Administrator
     *
     */
    public interface OnPaginationListener{
        void onPagination();
    }


           /**
   * 数据加载完成
    */
    public void refreshComplete(){
               state = NONE;
              isRefresh = false;
               reflashViewByState();

           }
//    public void loadComplete(){
//        footerView.setVisibility(View.GONE);
//        isLoading = false;
//        this.invalidate();
//    }

 }
