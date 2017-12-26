package com.sastroman.angga.androidlearn.rest;

/**
 * Created by Angga N P on 12/13/2017.
 */



import com.sastroman.angga.androidlearn.model.MovieResponse;
import com.sastroman.angga.androidlearn.model.Products;
import com.sastroman.angga.androidlearn.model.Services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("listProductss")
    Call<Products> getProducts();


    @GET("listServices")
    Call<Services> getServices();
}