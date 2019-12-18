package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdmstuttgart.bildbearbeiter.APIInterface;
import de.hdmstuttgart.bildbearbeiter.Photo;
import de.hdmstuttgart.bildbearbeiter.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;
import utilities.Constants;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ImageAdapter searchAdapter;
    private List<Photo> photoList;
    private List<Bitmap> bitmapList;
    private Map<String, String> urls;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        urls = new HashMap<>();
        bitmapList = new ArrayList<>();
        //inflate view to work with it
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        //setting onClick on SearchButton
        searchButton = view.findViewById(R.id.buttonSearchPictures);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.progress_search).setVisibility(View.VISIBLE);
                searchPicturesOnline();
            }
        });

        //assign recycle
        recyclerView = view.findViewById(R.id.recyclerSearch);
        linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter
        searchAdapter = new ImageAdapter(bitmapList);
        recyclerView.setAdapter(searchAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        // TODO: Use the ViewModel
    }

    private void searchPicturesOnline() {
        photoList = new ArrayList<>();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIInterface unsplashAPI = retrofit.create(APIInterface.class);

        Call<Photo> call = unsplashAPI.getRandomPicture(Constants.UNSPLASH_ACCESS_KEY);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                //get url
                urls.put("THUMB", response.body().urls.thumb);
                urls.put("REGULAR", response.body().urls.regular);

                //download bitmap
                getBitmapfromUrl();
                //insert into map
                searchAdapter.notifyDataSetChanged();
                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);
            }
        });


    }

    private void getBitmapfromUrl() {
        BitmapTask bitmapTask = new BitmapTask();
        bitmapTask.execute();
    }


    private class BitmapTask extends AsyncTask<String, Integer, Long> {

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            searchAdapter.notifyDataSetChanged();
        }

        protected Long doInBackground(String... string) {
            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            long totalSize = 0;
            for (int i = 0; i < urls.size(); i++) {

                try {
                    URL url = new URL(urls.get("THUMB"));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    bitmapList.add(bitmap);
                    totalSize++;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            searchAdapter.setBitmapList(bitmapList);

            return totalSize;
        }


    }

}

