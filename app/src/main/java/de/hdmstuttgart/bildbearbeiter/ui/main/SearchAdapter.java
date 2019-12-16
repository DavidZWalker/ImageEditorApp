package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Bitmap> searchList;

    public SearchAdapter(List<Bitmap> searchList) {
        this.searchList = searchList;
    }

    public void addToSearchList (Bitmap bitmap){
        this.searchList.add(bitmap);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new SearchViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ImageView searchResult = holder.itemView.findViewById(R.id.image_search_result);

        //TODO: SET IMAGES INTO recycler

    }

    @Override
    public int getItemCount() {
        if(searchList == null || searchList.size() == 0){
            return 0;
        }
        return searchList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SearchViewHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }

    public void setSearchList(List<Bitmap> searchList) {
        this.searchList = searchList;
    }


}
