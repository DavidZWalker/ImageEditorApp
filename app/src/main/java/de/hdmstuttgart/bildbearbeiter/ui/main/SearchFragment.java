package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
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
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.APIInterface;
import de.hdmstuttgart.bildbearbeiter.Photo;
import de.hdmstuttgart.bildbearbeiter.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utilities.Constants;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchAdapter searchAdapter;
    private List<Photo> photoList;
    private List<Bitmap> bitmapList;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter
        searchAdapter = new SearchAdapter(bitmapList);
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
                photoList.add(response.body());

                searchAdapter.notifyDataSetChanged();

                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);
            }
        });



    }
}
