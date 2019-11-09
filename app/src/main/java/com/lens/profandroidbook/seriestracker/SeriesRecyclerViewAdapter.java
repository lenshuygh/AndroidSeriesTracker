package com.lens.profandroidbook.seriestracker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lens.profandroidbook.seriestracker.databinding.ListItemSeriesBinding;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SeriesRecyclerViewAdapter extends RecyclerView.Adapter<SeriesRecyclerViewAdapter.ViewHolder> {
    private final List<Series> seriesList;

    public SeriesRecyclerViewAdapter(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSeriesBinding binding = ListItemSeriesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_series,parent,false);
        return new ViewHolder(binding,view,null);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Series series = seriesList.get(position);

        holder.binding.setSeries(series);
        holder.binding.executePendingBindings();

        holder.myListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                if(myItemClickedListener != null){
                    myItemClickedListener.onItemClicked(seriesList.get(position));
                }
            }
        };
    }

    public interface AdapterClick{
        void onItemClicked(Series selectedItem);
    }

    AdapterClick myItemClickedListener;

    public void setOnAdapterClick(AdapterClick adapterClickHandler){
        myItemClickedListener = adapterClickHandler;
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public final ListItemSeriesBinding binding;
        public View.OnClickListener myListener;

        public ViewHolder(ListItemSeriesBinding binding,View view,View.OnClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

            myListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: XXX ");
            if(myListener != null){
                myListener.onClick(view);
            }
        }
    }
}
