package com.mostafa.myapplication.DataModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mostafa.myapplication.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    List<Property> properties;

    public PropertyAdapter(List<Property> properties) {
        this.properties = properties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_list, parent, false);
        return new PropertyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(properties.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public void refresh(List<Property> properties) {
        this.properties.clear();
        this.properties.addAll(properties);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,extend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            extend = itemView.findViewById(R.id.textView4);
        }

        public void bind(Property property) {
            title.setText(property.getTitle());
            extend.setText(property.getExtend());
        }
    }
}
