package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.dbs.replsdk.ui.utils.ItemDecorator;
import com.dbs.replsdk.uimodel.DifferModel;

import java.util.List;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

/**
 * Created by deepak on 9/10/18.
 */

public abstract class DBSCarouselView<D extends DifferModel, H extends RecyclerView.ViewHolder> extends RecyclerView {


    public final DiffUtil.ItemCallback<D> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<D>() {

                @Override
                public boolean areItemsTheSame(@NonNull D d, @NonNull D t1) {
                    return d.areItemsTheSame(t1);
                }

                @Override
                public boolean areContentsTheSame(@NonNull D d, @NonNull D t1) {
                    return d.areContentsTheSame(t1);
                }
            };

    protected ListAdapter<D, H> adapter;

    private Context context;

    public DBSCarouselView(@NonNull Context context) {
        super(context);
    }

    public DBSCarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSCarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    public void setLayoutManager() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        super.setLayoutManager(layoutManager);
    }

    public void addItemDecoration() {
        final int commonPadding = dpToPixels(context, 8);
        super.addItemDecoration(new ItemDecorator(new Rect(commonPadding, commonPadding, 0, commonPadding)));
    }

    public void updateData(List<D> dtList) {
        adapter.submitList(dtList);
    }

    public abstract void bindData(H holder, D val, int pos);

    public abstract void onViewCreated(Context context, AttributeSet attrs);

    public abstract H getViewHolder(@NonNull ViewGroup viewGroup, int i);

    private void initView(AttributeSet attrs) {
        context = getContext();
        adapter = new ListAdapter<D, H>(DIFF_CALLBACK) {
            @NonNull
            @Override
            public H onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return getViewHolder(viewGroup, i);
            }

            @Override
            public void onBindViewHolder(@NonNull H h, int i) {
                bindData(h, getItem(i), i);
            }
        };
        setLayoutManager();
        addItemDecoration();
        setAdapter(adapter);
        onViewCreated(context, attrs);
    }

}