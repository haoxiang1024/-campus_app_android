package com.hx.campus.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.hx.campus.adapter.entity.SearchInfo;
import com.hx.campus.databinding.SearchInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchInfoAdapter extends BaseAdapter {
    private final Context context;//上下文
    private final List<SearchInfo> searchInfoList = new ArrayList<>();//数据源

    public void setData(List<SearchInfo> data, int pageIndex) {
        if (pageIndex == 0) {
            data.clear();
        }
        searchInfoList.addAll(data);
        notifyDataSetChanged();//刷新界面
    }

    public SearchInfoAdapter(Context context) {
        this.context = context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return searchInfoList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public SearchInfo getItem(int position) {
        return searchInfoList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *                 we want.
     * @param view     The old view to reuse, if possible. Note: You should check that this view
     *                 is non-null and of an appropriate type before using. If it is not possible to convert
     *                 this view to display the correct data, this method can create a new view.
     *                 Heterogeneous lists can specify their number of view types, so that this View is
     *                 always of the right type (see {@link #getViewTypeCount()} and
     *                 {@link #getItemViewType(int)}).
     * @param parent   The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        com.hx.campus.databinding.SearchInfoBinding searchInfoBinding;
        if (view == null) {
            searchInfoBinding = SearchInfoBinding.inflate(LayoutInflater.from(context));
            view = searchInfoBinding.getRoot();
            view.setTag(searchInfoBinding);
        } else {
            searchInfoBinding = (SearchInfoBinding) view.getTag();
        }
        SearchInfo searchInfo = getItem(position);
        searchInfoBinding.lostTitle.setText(searchInfo.getTitle());
        searchInfoBinding.authorName.setText(searchInfo.getNickname());
        searchInfoBinding.tvLostContent.setText(searchInfo.getContent());
        //加载图片
        if (TextUtils.isEmpty(searchInfo.getImg())) {
            searchInfoBinding.lostImg.setVisibility(View.GONE);
        } else {
            searchInfoBinding.lostImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(searchInfo.getImg()).into(searchInfoBinding.lostImg);
        }
        return view;
    }
}
