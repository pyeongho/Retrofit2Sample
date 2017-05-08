package com.example.phkim.mycleanmvp.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.phkim.mycleanmvp.R;
import com.example.phkim.mycleanmvp.main.domain.model.SearchData;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SearchViewHolder>
        implements ImageAdapterDataModel, ImageAdapterDataView {

    private Context context;
    private List<SearchData> items;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public ImageAdapter(Context context,List<SearchData> list,OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.context = context;
        this.items = list;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        SearchData item = getItem(position);
        holder.tvTitle.setText(fromHtml(item.getTitle()));
        holder.tvTitle.setOnClickListener(view -> {
            if(onRecyclerItemClickListener!=null){
                onRecyclerItemClickListener.onItemClick(ImageAdapter.this,position);
            }
        });
        Glide.with(context)
                .load(item.getImgUrl())
                .into(holder.ivThumbnail);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    @Override
    public void add(SearchData SearchData) {
        items.add(SearchData);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public SearchData getItem(int position) {
        return items.get(position);
    }

    @Override
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public void replaceData(List<SearchData> searchData) {
        setList(searchData);
        notifyDataSetChanged();
    }

    private void setList(List<SearchData> tasks) {
        items = checkNotNull(tasks);
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivThumbnail;

        public SearchViewHolder(View itemView) {
            super(itemView);
            tvTitle =(TextView)itemView.findViewById(R.id.tv_item_search_result_title);
            ivThumbnail =(ImageView)itemView.findViewById(R.id.iv_item_search_result_thumb);
        }
    }
}
