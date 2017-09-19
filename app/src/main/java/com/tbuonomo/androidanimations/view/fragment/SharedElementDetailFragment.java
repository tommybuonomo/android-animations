package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tbuonomo.androidanimations.R;

/**
 * Created by tommy on 04/09/17.
 */

public class SharedElementDetailFragment extends Fragment {

  public static final String NATURE_RES_ID = "NATURE_RES_ID";
  @BindView(R.id.item_nature_image) ImageView natureImage;

  public static SharedElementDetailFragment newInstance(@DrawableRes int natureResId) {
    SharedElementDetailFragment sharedElementDetailFragment = new SharedElementDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(NATURE_RES_ID, natureResId);
    sharedElementDetailFragment.setArguments(bundle);
    return sharedElementDetailFragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Wait for launching enter transition
    postponeEnterTransition();
    setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.shared_element_trasition));
    setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.shared_element_trasition));
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_shared_element_detail, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    int resId = getArguments().getInt(NATURE_RES_ID);

    natureImage.setTransitionName(String.valueOf(resId));

    Glide.with(getContext()).load(resId).listener(new RequestListener<Integer, GlideDrawable>() {
      @Override public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
        startPostponedEnterTransition();
        return false;
      }

      @Override public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache,
          boolean isFirstResource) {
        startPostponedEnterTransition();
        return false;
      }
    }).into(natureImage);
  }
}
