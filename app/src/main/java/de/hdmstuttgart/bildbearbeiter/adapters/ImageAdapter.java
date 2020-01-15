package de.hdmstuttgart.bildbearbeiter.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.models.ImageLibrary;
import de.hdmstuttgart.bildbearbeiter.views.BottomSheetFragment;
import de.hdmstuttgart.bildbearbeiter.views.ImageEditorActivity;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Bitmap> bitmapList;
    private ImageLibrary imageLibrary;

    public ImageAdapter(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        notifyDataSetChanged();
    }

    public ImageAdapter(List<Bitmap> bitmapList, ImageLibrary imageLibrary){
        this(bitmapList);
        this.imageLibrary = imageLibrary;
    }

    public void addToBitmapList(Bitmap bitmap) {
        this.bitmapList.add(bitmap);
        notifyItemInserted(bitmapList.size() - 1);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //create new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(), ImageEditorActivity.class);
            ImageView imageView1 = (ImageView) v;
            Bitmap bmp = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
            ImageFileHandler ifh = new ImageFileHandler(parent.getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_TMP);
            try {
                ifh.saveImage(bmp, "tmpImage");
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra("imageURI", "tmpImage");
            parent.getContext().startActivity(intent);
        });
        if(imageLibrary != null){
            imageView.setOnLongClickListener(v -> {
                BottomSheetFragment dialog = new BottomSheetFragment(this,imageView,imageLibrary);
                FragmentManager fm = ((AppCompatActivity)parent.getContext()).getSupportFragmentManager();
                dialog.show(fm, "bla");
                return true;
            });
        }
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

    public void clearBitmapList() {
        this.bitmapList.clear();
        notifyDataSetChanged();
    }

    public void removeBitmap(Bitmap bmptoRemove) {
        int pos = this.bitmapList.indexOf(bmptoRemove);
        this.bitmapList.remove(bmptoRemove);
        notifyItemRemoved(pos);
    }
}
