package com.sastroman.angga.androidlearn.view;

/**
 * Created by Angga N P on 12/13/2017.
 */

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.adapter.MoviesAdapter;
import com.sastroman.angga.androidlearn.model.Movie;
import com.sastroman.angga.androidlearn.model.MovieResponse;
import com.sastroman.angga.androidlearn.rest.ApiClient;
import com.sastroman.angga.androidlearn.rest.ApiInterface;
import com.sastroman.angga.androidlearn.view.addon.RecyclerItemClickListener;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sastroman.angga.androidlearn.app.AppController.TAG;

public class FragmentProduct1 extends Fragment {


    private final static String API_KEY = "d9f59b02507acaeaed5b851d7e5d353f";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private android.support.v7.widget.Toolbar toolbar;
    private AppBarLayout appbar;
    private ViewPager viewpager;
    private LinearLayout container_toolbar;
    private MoviesAdapter adapter;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product1, container, false);

        getData();

        //actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar = getActivity().findViewById(R.id.toolbar);
        appbar = getActivity().findViewById(R.id.appbar);
        viewpager = getActivity().findViewById(R.id.viewpager);
        container_toolbar= getActivity().findViewById(R.id.container_toolbar);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.i(TAG, "onItemClick: " + position);
                        final Rect viewRect = new Rect();
                        view.getGlobalVisibleRect(viewRect);

                        // create Explode transition with epicenter
                        Transition explode = new Explode()
                                .setEpicenterCallback(new Transition.EpicenterCallback() {
                                    @Override
                                    public Rect onGetEpicenter(Transition transition) {
                                        return viewRect;
                                    }
                                });
                        explode.setDuration(1000);
                        TransitionManager.beginDelayedTransition(recyclerView, explode);

                        // remove all views from Recycler View
                        recyclerView.setAdapter(null);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.action_search);

        SearchView sv = new SearchView(getActivity());
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });

        search.setActionView(sv);
    }


    //Menu Setting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id == R.id.action_delete){
            Toast.makeText(getContext(), "Delete action is clicked!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        if (API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "API?", Toast.LENGTH_LONG).show();
        }
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                if (response.toString() !=null || response.toString().isEmpty()){
                    //movies.clear();
                    movieList = response.body().getResults();
                    adapter = new MoviesAdapter(movieList, getActivity().getApplicationContext());
                    //adapter.notifyDataSetChanged();
                    //Log.d(TAG, "Number of movies received: " + movies.size());
                    //new MoviesAdapter(movieList, getActivity().getApplicationContext())
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(), "Couldn't fetch data! Please try again later.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}