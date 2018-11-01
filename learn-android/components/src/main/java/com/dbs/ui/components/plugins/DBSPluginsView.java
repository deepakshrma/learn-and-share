package com.dbs.ui.components.plugins;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.components.R;
import com.dbs.ui.BaseView;
import com.dbs.ui.components.DBSRecyclerView;
import com.dbs.ui.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * The DBSPluginsView is custom view which holds a list of plugins.
 *
 * @author DBS Bank, AppStudio Team
 * @see DBSPlugin
 */
public class DBSPluginsView extends BaseView {

    private PluginAdapter adapter;

    public DBSPluginsView(Context context) {
        super(context);
    }

    public DBSPluginsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSPluginsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void viewInflated(View view, String type, AttributeSet attrs) {
        DBSRecyclerView pluginsView = view.findViewById(R.id.plugins_holder);
        adapter = new PluginAdapter(new ArrayList<>());
        pluginsView.setRecyclerViewAdapter(adapter);
        pluginsView.setOrientation(VERTICAL);
    }

    @Override
    protected int provideLayoutId(String type) {
        return R.layout.dbs_plugins_view;
    }

    /**
     * This method will add plugin to plugins list
     *
     * @param plugin
     */
    public void addPlugin(DBSPlugin plugin) {
        adapter.addPlugin(plugin);
    }

    /**
     * This method will update the list of plugins with new list
     *
     * @param plugins
     */
    public void updatePlugins(List<DBSPlugin> plugins) {
        adapter.updatePlugins(plugins);
    }

    private class PluginAdapter extends RecyclerView.Adapter<PluginViewHolder> {
        private List<DBSPlugin> plugins;

        PluginAdapter(List<DBSPlugin> plugins) {
            this.plugins = plugins;
        }

        @NonNull
        @Override
        public PluginViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.plugin_view, parent, false);
            return new PluginViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PluginViewHolder holder, int position) {
            holder.bind(plugins.get(position));
        }

        @Override
        public int getItemCount() {
            return plugins.size();
        }

        void updatePlugins(List<DBSPlugin> plugins) {
            this.plugins = plugins;
            notifyDataSetChanged();
        }

        void addPlugin(DBSPlugin plugin) {
            plugins.add(plugin);
            notifyItemInserted(plugins.size() - 1);
        }
    }

    private class PluginViewHolder extends RecyclerView.ViewHolder {

        private final CardView parent;

        PluginViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.plugin_container);
        }

        public void bind(DBSPlugin plugin) {
            parent.removeAllViews();
            LayoutParams layoutParams = getLayoutParams(plugin.getPluginType());
            parent.setLayoutParams(layoutParams);
            View view = plugin.onCreatePluginView(parent);
            ViewUtils.removeFromParent(view);
            parent.addView(view);
        }

        @NonNull
        private LayoutParams getLayoutParams(DBSPluginType pluginType) {
            int heightInPixels = getContext().getResources()
                    .getDimensionPixelOffset(pluginType.getHeightDimenResourceId());
            return new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    heightInPixels);
        }
    }
}