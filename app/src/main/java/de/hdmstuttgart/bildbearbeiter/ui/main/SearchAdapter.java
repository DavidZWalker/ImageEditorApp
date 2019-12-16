package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdmstuttgart.bildbearbeiter.Photo;
import de.hdmstuttgart.bildbearbeiter.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Photo> searchList;

    public SearchAdapter(List<Photo> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create new view
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new SearchViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ImageView searchResult = holder.itemView.findViewById(R.id.image_search_result);
        TextView textView = holder.itemView.findViewById(R.id.testString);
        textView.setText(searchList.get(position).id);
        //TODO: load image from Current position

    }

    @Override
    public int getItemCount() {
        if(searchList == null || searchList.size() == 0){
            return 0;
        }
        return searchList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;

        public SearchViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.linearLayout = linearLayout;

        }
    }

    public void setSearchList(List<Photo> searchList) {
        this.searchList = searchList;
    }


}
