package com.example.demo.mutitypeloadmoreadapterdemo.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.mutitypeloadmoreadapterdemo.R;

import java.util.List;

/**
 * @author ChuYinGen
 *         2017/6/14
 * @Description 加载更多适配器
 */
public abstract class LoadMoreAdapter<T> extends MultiTypeAdapter<T> {

    private static final int TYPE_FOOTER = -1;
    private RecyclerView mRecyclerView;//用来判断第一次加载是否 占满全屏
    private LoadMoreViewHolder mViewHolder;
    private boolean isCanLoad = true;//是否可以加载更多
    private boolean isLast = false;//是否是最后一条数据
    private boolean isRemoved = false;//判断当前是否移除底部加载更多view

    /**
     * 设置当数据不满一屏时 去除底部加载更多view，超过一屏时添加加载更多view
     * @param enableLoadMore
     */
    public void setEnableLoadMore(boolean enableLoadMore) {
        int oldLoadMoreCount = getLoadMoreViewCount();
        isCanLoad = enableLoadMore;
        int newLoadMoreCount = getLoadMoreViewCount();

        if (oldLoadMoreCount == 1) {
            if (newLoadMoreCount == 0) {
                int i = getItemCount() - 1;
                isRemoved = true;
                Log.e("tag", "-----remove" + i);
                notifyItemRemoved(getItemCount() - 1);
            }
        } else {
            if (newLoadMoreCount == 1) {
                isCanLoad = true;
                isRemoved = false;
                Log.e("tag", "-----insert" + getItemCount());
                notifyItemInserted(getItemCount() - 1);
            }
        }
    }

    /**
     * 判断当前是否显示加载更多view
     * @return
     */
    public int getLoadMoreViewCount() {
        if (mLoadMoreListener == null || !isCanLoad) {
            return 0;
        }
        if (mList.size() == 0) {
            return 0;
        }
        return 1;
    }

    public interface OnLoadMoreListener {
        /**
         * 加载更多的回调方法
         */
        void onLoadMore();
    }

    private OnLoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener, RecyclerView recyclerView) {
        mLoadMoreListener = loadMoreListener;
        this.mRecyclerView = recyclerView;
    }

    /**
     * 加载更多 添加数据
     * @param list
     */
    @Override
    public void addList(List<T> list) {
        super.addList(list);
        if (list != null && list.size() > 0) {
            isCanLoad = true;
        }
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (count > 0 && mLoadMoreListener != null && !isRemoved) {
            count += 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mLoadMoreListener != null
                && super.getItemCount() == position) {
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            if (!isRemoved) {
                View footerView = inflate(parent, R.layout.widget_item_status);

                mViewHolder = new LoadMoreViewHolder(footerView);
                if (isLast) {
                    mViewHolder.showLast();
                }
                return mViewHolder;
            }
        }
        return buildViewHolder(parent, viewType);
    }

    protected abstract BaseViewHolder buildViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (isCanLoad) {
                isCanLoad = false;
                mLoadMoreListener.onLoadMore();
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public void showLoad() {
        isCanLoad = true;
        if (mViewHolder != null) {
            mViewHolder.showLoad();
        }
    }

    public void showLast() {
        isCanLoad = false;
        isLast = true;
        if (mViewHolder != null) {
            mViewHolder.showLast();
        }
    }

    public void showError(View.OnClickListener listener) {
        isCanLoad = false;
        mViewHolder.showError(listener);
    }

    /**
     * 判断数据是否不满一屏
     */
    public void disableLoadMoreIfNotFullScreen() {
        setEnableLoadMore(false);
        if (mRecyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("tag", "******" + isFullScreen(linearLayoutManager));
                    if (isFullScreen(linearLayoutManager)) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                    int pos = getTheBiggestNumber(positions) + 1;
                    if (pos != getItemCount()) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        }

    }

    /**
     * 设置当LayoutManager 为GridLayout布局时 加载更多布局横向占满
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) % TYPE_FOOTER == 0)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 设置当 LayoutManager 为瀑布流时，加载更多布局横向铺满
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == (getItemCount() - 1)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    private int getTheBiggestNumber(int[] positions) {
        int tmp = -1;
        if (positions == null || positions.length == 0) {
            return tmp;
        }
        for (int num : positions) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }

    /**
     * 判断是否满屏
     *
     * @param llm
     * @return
     */
    private boolean isFullScreen(LinearLayoutManager llm) {
        return (llm.findLastCompletelyVisibleItemPosition() + 1) != getItemCount() ||
                llm.findFirstCompletelyVisibleItemPosition() != 0;
    }

}
