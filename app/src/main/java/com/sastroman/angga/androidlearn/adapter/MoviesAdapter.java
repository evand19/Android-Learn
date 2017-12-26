package com.sastroman.angga.androidlearn.adapter;

/**
 * Created by Angga N P on 12/13/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> implements Filterable {

    private List<Movie> movies;
    private List<Movie> filmovies;
    private Context context;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.filmovies = movies;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final Movie movie = filmovies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.data.setText(movie.getReleaseDate());
        holder.movieDescription.setText(movie.getOverview());
        holder.rating.setText(movie.getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return filmovies.size();
    }

    //@Override
    //public long getItemId(int position) {
    //    return position;
    //}

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filmovies = movies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : movies) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filmovies = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filmovies;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filmovies = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}