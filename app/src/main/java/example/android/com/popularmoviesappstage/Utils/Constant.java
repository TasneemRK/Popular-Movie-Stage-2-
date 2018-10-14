package example.android.com.popularmoviesappstage.Utils;

import example.android.com.popularmoviesappstage.BuildConfig;

public interface Constant {

    String apiKey = BuildConfig.ApiKey;
    String BASE_URL = "https://api.themoviedb.org/3/";
    String API_KEY = "?api_key=" + apiKey;
    //    public static final String API_VERSION = "3";
    public static final String API_KEY1 = "api_key";
    public static final String VIDEOS = "videos";
    public static final String MOVIE = "movie";
    public static final String REVIEWS = "reviews";

    // ------------------ Sort APIs ---------------------------

    final String SORT_BY_POPULARITY_API = BASE_URL + "movie/popular" + API_KEY;
    final String SORT_BY_TOP_RATES_API = BASE_URL + "movie/top_rated" + API_KEY;

    // ----------------------- Find Movie By Id -------------------

    // to get movie from its id , you should use the url as follows:
    // https://api.themoviedb.org/3/movie/ + movie id + ?api_key=
    // OR ==>  BASE_URL + "movie/" + movie id + API_KEY

    final String GET_MOVIE = BASE_URL + "movie/";

    // ------------------------- all movies ---------------------

    final String ALL_MOIVES_API = BASE_URL + "discover/movie" + API_KEY;


    // ------------------------ image url -----------------------------

    final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";


}
