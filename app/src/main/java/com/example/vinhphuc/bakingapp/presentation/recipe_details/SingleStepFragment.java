package com.example.vinhphuc.bakingapp.presentation.recipe_details;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.vinhphuc.bakingapp.R;
import com.example.vinhphuc.bakingapp.data.model.Step;
import com.example.vinhphuc.bakingapp.presentation.base.BaseFragment;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.NotNull;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleStepFragment
        extends BaseFragment
        implements ExoPlayer.EventListener {

    public static final String STEP_KEY = "STEP_KEY";
    @BindView(R.id.recipe_step_desc_card)
    CardView descriptionCard;
    @BindView(R.id.recipe_step_image)
    ImageView stepThumbnail;
    @BindView(R.id.recipe_step_desc)
    TextView descTextView;
    @BindView(R.id.recipe_step_video)
    SimpleExoPlayerView exoPlayerView;

    @BindBool(R.bool.isTablet)
    boolean isTwoPane;

    SimpleExoPlayer exoPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    private long playerPosition;
    private Uri videoUri;
    private boolean playWhenReady = true;
    private int state;

    public SingleStepFragment() {}

    public static SingleStepFragment newInstance(Step step) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STEP_KEY, step);
        SingleStepFragment fragment = new SingleStepFragment();
        fragment.setArguments(bundle);

<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
        return fragment;
    }

=======
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
=======
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean("auto_play");
            state = savedInstanceState.getInt("state");
            playerPosition = savedInstanceState.getLong("position");
            Timber.d("RIP MINE, playerPosition: " + playerPosition);
        }

>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
        Step step = getArguments().getParcelable(STEP_KEY);
        String description = step != null ? step.getDescription() : "";
        descTextView.setText(description);

        String imageUrl = step != null ? step.getThumbnailURL() : "";
        if (imageUrl != null && !imageUrl.isEmpty()) {
            //Load & show Image view
            Glide.clear(stepThumbnail);
            Glide.with(this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(stepThumbnail);
            setViewVisibility(stepThumbnail, true);
        } else {
            //Hide Image view
            setViewVisibility(stepThumbnail, false);
        }

        int orientation = getResources().getConfiguration().orientation;
        String video = step != null ? step.getVideoURL() : "";
        setUri(Uri.parse(video));

        if (video != null && !video.isEmpty()) {
            //Init and show video view
            setViewVisibility(exoPlayerView, true);
            initializeMediaSession();
            initializePlayer(videoUri);

            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
                //Expand video, hide description, hide system UI
                expandVideoView(exoPlayerView);
                setViewVisibility(descriptionCard, false);
                hideSystemUI();
            }
        } else {
            //Hide video view
            setViewVisibility(exoPlayerView, false);
        }
    }

    private void setViewVisibility(View view, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), "SingleStepPage");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE
                );
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                exoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                exoPlayer.seekTo(0);
            }
        });
        mediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "StepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
            if (playerPosition != C.TIME_UNSET)
                exoPlayer.seekTo(playerPosition);
=======
            exoPlayer.seekTo(playerPosition);
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void expandVideoView(@NotNull SimpleExoPlayerView exoPlayerView) {
        exoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_step, container, false);

        setUnbinder(ButterKnife.bind(this,view));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(videoUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || exoPlayer == null)
            initializePlayer(videoUri);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
            playerPosition = exoPlayer.getCurrentPosition();
=======
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }

        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
        }
        if (mediaSession == null)
            initializeMediaSession();
        mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity() {}

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            state = exoPlayer.getCurrentWindowIndex();
<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
            playerPosition = Math.max(0, exoPlayer.getCurrentPosition());
=======
            playerPosition = exoPlayer.getCurrentPosition();
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
        }
        bundle.putBoolean("auto_play", playWhenReady);
        bundle.putInt("state", state);
        bundle.putLong("position", playerPosition);
<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
=======
        Timber.d("RIP CHELSEA, playerPosition: " + playerPosition);
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
    }

    public void setUri(Uri uri) {
        this.videoUri = uri;
    }
<<<<<<< HEAD:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_step/SingleStepFragment.java
=======

    public long getPlayerPosition() {
        return playerPosition;
    }
>>>>>>> c54b69a... Fix video player not resuming video when changing device's state, playing from the beginning instead:app/src/main/java/com/example/vinhphuc/bakingapp/presentation/recipe_details/SingleStepFragment.java
}
