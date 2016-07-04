package arturvasilov.udacity.nanodegree.popularmovies.api;

import arturvasilov.udacity.nanodegree.popularmovies.model.MoviesResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface MovieService {

    @GET("popular/")
    Observable<MoviesResponse> popularMovies();

    @GET("top_rated/")
    Observable<MoviesResponse> topRatedMovies();

}
