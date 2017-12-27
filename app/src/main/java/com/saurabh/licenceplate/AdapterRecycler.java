package com.saurabh.licenceplate;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by saurabh on 12/26/2017.
 */

public class AdapterRecycler  extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private LayoutInflater inflater;

    List<Model> data = Collections.emptyList();

    private Activity activity;

    public AdapterRecycler(Activity activity, List<Model> data){

        this.activity = activity;
        this.data = data;
    }


    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (inflater == null)
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.recyclerviewnew, parent, false);

        AdapterRecycler.ViewHolder viewHolder = new AdapterRecycler.ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolder holder, int position) {

        Model current = data.get(position);
        holder.name.setText(current.getName());
        holder.phon.setText(current.getNumber());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView phon, name;

        public ViewHolder(View itemView) {
            super(itemView);

            phon = (TextView) itemView.findViewById(R.id.phon);
            name = (TextView) itemView.findViewById(R.id.name);

        }
    }
}
