package com.sup.itg.itgr;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;

    private SparseArray<View> mHeaderView;
    private SparseArray<View> mFootView;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public HeaderViewRecyclerAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, SparseArray<View> head, SparseArray<View> foot) {
        if (head == null) {
            head = new SparseArray<>();
        }
        if (foot == null) {
            foot = new SparseArray<>();
        }
        mHeaderView = head;
        mFootView = foot;
        mAdapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return createViewHolder(mHeaderView.get(viewType));
        } else if (isFooterViewType(viewType)) {
            return createViewHolder(mFootView.get(viewType));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mFootView.size() + mHeaderView.size();
    }


    public int getHeadersCount() {
        return mHeaderView.size();
    }

    public int getFootersCount() {
        return mFootView.size();
    }

    public boolean isHeader(int position) {
        if (position < mHeaderView.size()) {
            return true;
        }
        return false;
    }

    public boolean isFooter(int position) {
        int totalNum = mHeaderView.size() + mAdapter.getItemCount() + mFootView.size();
        if (position >= mHeaderView.size() + mAdapter.getItemCount() && position < totalNum) {
            return true;
        }
        return false;
    }

    private boolean isHeaderViewType(int itemType) {
        int position = mHeaderView.indexOfKey(itemType);
        return position >= 0;
    }

    private boolean isFooterViewType(int itemType) {
        return mFootView.indexOfKey(itemType) >= 0;
    }


    public boolean removeHeader(View v) {
        int position = mHeaderView.indexOfValue(v);
        if (position < 0) return false;
        mHeaderView.remove(position);
        notifyItemChanged(position);
        return true;
    }

    public boolean removeFooter(View v) {
        int position = mFootView.indexOfValue(v);
        if (position < 0) return false;
        mFootView.remove(position);
        notifyItemChanged(position);
        return true;
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderView.keyAt(position);
        } else if (isFooter(position)) {
            position = position - mHeaderView.size() - mAdapter.getItemCount();
            return mFootView.keyAt(position);
        }
        return super.getItemViewType(position);
    }

    private RecyclerView.ViewHolder createViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }
}
