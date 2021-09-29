package com.example.task1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdaptor extends ArrayAdapter<StateModel> {
    private  Context context;
    private List<StateModel> stateModelList;
    private List<StateModel> stateModelListFilter;

    public CustomAdaptor( Context context, List<StateModel> stateModelList) {
        super(context, R.layout.list_custom_item,stateModelList);
        this.context = context;
        this.stateModelList=stateModelList;
        this.stateModelListFilter=stateModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView stateName=view.findViewById(R.id.stateName);

        stateName.setText(stateModelListFilter.get(position).getState());
        return view;
    }

    @Override
    public int getCount() {
        return stateModelListFilter.size();
    }

    @Nullable
    @Override
    public StateModel getItem(int position) {
        return stateModelListFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length()==0){
                    filterResults.count= stateModelList.size();
                    filterResults.values=stateModelList;
                }
                else{
                    List<StateModel> resultModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(StateModel itemModel: stateModelList){
                        if(itemModel.getState().toLowerCase().contains(searchStr)){
                            resultModel.add(itemModel);
                        }
                        filterResults.count= resultModel.size();
                        filterResults.values=resultModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stateModelListFilter = (List<StateModel>)  results.values;
                Home.stateModelList=(List<StateModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}
