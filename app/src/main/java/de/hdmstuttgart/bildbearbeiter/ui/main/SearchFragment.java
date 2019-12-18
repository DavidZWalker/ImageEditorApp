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
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdmstuttgart.bildbearbeiter.APIInterface;
import de.hdmstuttgart.bildbearbeiter.Result;
import de.hdmstuttgart.bildbearbeiter.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utilities.Constants;
import utilities.SearchResponse;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ImageAdapter searchAdapter;
    private List<Result> resultList;
    private List<Bitmap> bitmapList;
    private Map<String, String> urls;
    private EditText editText;
    private List<String> urlStrings;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        urls = new HashMap<>();
        bitmapList = new ArrayList<>();
        urlStrings = new ArrayList<>();
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
        resultList = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.UNSPLASH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIInterface unsplashAPI = retrofit.create(APIInterface.class);

        //get Search String from User
        editText = getActivity().findViewById(R.id.editTextSearchPictures);
        String query = editText.getText().toString();
        editText.setText("");


        Call<SearchResponse> call = unsplashAPI.getSearchResults(query, Constants.UNSPLASH_PAGE, Constants.UNSPLASH_PER_PAGE, Constants.UNSPLASH_ACCESS_TOKEN);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                //get urls TODO FIX ME bruv
                resultList = response.body().result;

                //puts urls with constants in map


                //download bitmap

                //insert into map
                searchAdapter.notifyDataSetChanged();
                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                getActivity().findViewById(R.id.progress_search).setVisibility(View.GONE);
            }
        });


    }


}

