package com.life2smile.scrollpicker.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.life2smile.scrollpicker.library.IPickerViewOperation;
import com.life2smile.scrollpicker.library.IViewProvider;
import com.life2smile.scrollpicker.library.view.DefaultItemView;

import java.util.ArrayList;
import java.util.List;

public class ScrollPickerAdapter<T> extends RecyclerView.Adapter<ScrollPickerAdapter.ScrollPickerAdapterHolder> implements IPickerViewOperation {
    private List<T> mDataList;
    private Context mContext;
    private OnClickListener mOnItemClickListener;
    private int mSelectedItemOffset;
    private int mVisibleItemNum = 3;
    private IViewProvider mViewProvider;
    private int mLineColor;
    private int maxItemH = 0;
    private int maxItemW = 0;

    private ScrollPickerAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ScrollPickerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mViewProvider == null) {
            mViewProvider = new DefaultItemView();
        }
        return new ScrollPickerAdapterHolder(LayoutInflater.from(mContext).inflate(mViewProvider.resLayout(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollPickerAdapterHolder holder, int position) {
        mViewProvider.onBindView(holder.itemView, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getSelectedItemOffset() {
        return mSelectedItemOffset;
    }

    @Override
    public int getVisibleItemNumber() {
        return mVisibleItemNum;
    }

    @Override
    public int getLineColor() {
        return mLineColor;
    }

    @Override
    public void updateView(View itemView, boolean isSelected) {
        mViewProvider.updateView(itemView, isSelected);
        adaptiveItemViewSize(itemView);
        itemView.setOnClickListener(isSelected ? new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onSelectedItemClicked(v);
                }
            }
        } : null);
    }

    private void adaptiveItemViewSize(View itemView) {
        int h = itemView.getHeight();
        if (h > maxItemH) {
            maxItemH = h;
        }

        int w = itemView.getWidth();
        if (w > maxItemW) {
            maxItemW = w;
        }

        itemView.setMinimumHeight(maxItemH);
        itemView.setMinimumWidth(maxItemW);
    }

    static class ScrollPickerAdapterHolder extends RecyclerView.ViewHolder {
        private View itemView;

        private ScrollPickerAdapterHolder(@NonNull View view) {
            super(view);
            itemView = view;
        }
    }

    public interface OnClickListener {
        void onSelectedItemClicked(View v);
    }

    public static class ScrollPickerAdapterBuilder<T> {
        private ScrollPickerAdapter mAdapter;

        public ScrollPickerAdapterBuilder(Context context) {
            mAdapter = new ScrollPickerAdapter<T>(context);
        }

        public ScrollPickerAdapterBuilder<T> selectedItemOffset(int offset) {
            mAdapter.mSelectedItemOffset = offset;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setDataList(List<T> list) {
            mAdapter.mDataList.clear();
            mAdapter.mDataList.addAll(list);
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setOnClickListener(OnClickListener listener) {
            mAdapter.mOnItemClickListener = listener;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> visibleItemNumber(int num) {
            mAdapter.mVisibleItemNum = num;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setItemViewProvider(IViewProvider viewProvider) {
            mAdapter.mViewProvider = viewProvider;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setDivideLineColor(String colorString) {
            mAdapter.mLineColor = Color.parseColor(colorString);
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setSelectedItemTextSize(int size) {

            return this;
        }

        public ScrollPickerAdapter build() {
            adaptiveData(mAdapter.mDataList);
            mAdapter.notifyDataSetChanged();
            return mAdapter;
        }

        private void adaptiveData(List list) {
            int visibleItemNum = mAdapter.mVisibleItemNum;
            int selectedItemOffset = mAdapter.mSelectedItemOffset;
            for (int i = 0; i < mAdapter.mSelectedItemOffset; i++) {
                list.add(0, null);
            }

            for (int i = 0; i < visibleItemNum - selectedItemOffset - 1; i++) {
                list.add(null);
            }
        }
    }
}
