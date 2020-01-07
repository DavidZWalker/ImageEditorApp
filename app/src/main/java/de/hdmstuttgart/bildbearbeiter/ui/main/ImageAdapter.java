package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.Constants;
import utilities.ImageFileHandler;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Bitmap> bitmapList;

    public ImageAdapter(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        notifyDataSetChanged();
    }

    public void addToBitmapList(Bitmap bitmap){
        this.bitmapList.add(bitmap);
        notifyItemInserted(bitmapList.size() - 1);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //create new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), FullscreenImageActivity.class);
                ImageView imageView = (ImageView) v;
                Bitmap bmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ImageFileHandler ifh = new ImageFileHandler(parent.getContext(), Constants.IMAGES_TMP_FULLSCREEN);
                try {
                    ifh.saveImage(bmp, "tmpImage");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("imageURI", "tmpImage");
                parent.getContext().startActivity(intent);
            }
        });
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.image_search_result);
        imageView.setImageBitmap(bitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmapList == null ? 0 : bitmapList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        notifyDataSetChanged();
    }
}
