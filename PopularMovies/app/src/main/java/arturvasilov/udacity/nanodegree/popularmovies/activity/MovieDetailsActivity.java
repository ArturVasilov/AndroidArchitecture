package arturvasilov.udacity.nanodegree.popularmovies.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import arturvasilov.udacity.nanodegree.popularmovies.R;
import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;
import arturvasilov.udacity.nanodegree.popularmovies.presenter.MovieDetailsPresenter;
import arturvasilov.udacity.nanodegree.popularmovies.utils.Images;
import arturvasilov.udacity.nanodegree.popularmovies.view.MovieView;

public class MovieDetailsActivity extends AppCompatActivity implements MovieView {

    //Most of transitions code here is from http://antonioleiva.com/collapsing-toolbar-layout/

    public static final String IMAGE = "image";
    public static final String EXTRA_MOVIE = "extraMovie";

    private TextView mTitleTextView;
    private TextView mOverviewTextView;
    private TextView mRatingTextView;

    private MovieDetailsPresenter mPresenter;

    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage,
                                @NonNull Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new MovieDetailsPresenter(this, this);
        mPresenter.onPrepareActivity();

        setContentView(R.layout.activity_movie_details);

        mTitleTextView = (TextView) findViewById(R.id.title);
        mOverviewTextView = (TextView) findViewById(R.id.overview);
        mRatingTextView = (TextView) findViewById(R.id.rating);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        mPresenter.init(movie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onHomeButtonPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void showToolbarTitle(@NonNull String title) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
    }

    @Override
    public void showImage(@NonNull Movie movie, @NonNull String width) {
        final ImageView image = (ImageView) findViewById(R.id.image);
        Images.loadMovie(image, movie, width);
    }

    @Override
    public void showMovieTitle(@NonNull String title, @NonNull String year) {
        mTitleTextView.setText(getString(R.string.movie_title, title, year));
    }

    @Override
    public void showMovieOverview(@NonNull String overview) {
        mOverviewTextView.setText(overview);
    }

    @Override
    public void showAverageRating(@NonNull String average, @NonNull String max) {
        mRatingTextView.setText(getString(R.string.rating, average, max));
    }

    @Override
    public void closeScreen() {
        onBackPressed();
    }
}
