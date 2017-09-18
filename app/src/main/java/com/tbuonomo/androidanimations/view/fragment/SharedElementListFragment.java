package com.tbuonomo.androidanimations.view.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.activity.FragmentNavigation;
import com.tbuonomo.androidanimations.view.adapter.NatureItemsAdapter;
import com.tbuonomo.androidanimations.view.adapter.item.NatureItem;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import com.tbuonomo.androidanimations.view.util.DrawableUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.List;
import java.util.Map;

/**
 * Created by tommy on 04/09/17.
 */

public class SharedElementListFragment extends Fragment implements NatureItemsAdapter.OnItemClickListener {

  @BindView(R.id.shared_element_recycler_view) RecyclerView recyclerView;
  private boolean startAnimated;
  private FragmentNavigation fragmentNavigation;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_shared_element, container, false);
    ButterKnife.bind(this, rootView);
    fragmentNavigation = (FragmentNavigation) getActivity();
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    NatureItemsAdapter adapter = new NatureItemsAdapter(DrawableUtils.getAllNatureItems(getContext()));
    adapter.setOnItemClickListener(this);
    recyclerView.setHasFixedSize(true);

    setExitSharedElementCallback(new SharedElementCallback() {
      @Override public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        super.onMapSharedElements(names, sharedElements);
      }

      @Override public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        return super.onCreateSnapshotView(context, snapshot);
      }
    });

    LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2) {
      @Override public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (!startAnimated) {
          initSpruce();
          startAnimated = true;
        }
      }
    };
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  private void initSpruce() {
    ObjectAnimator translationY = ObjectAnimator.ofFloat(recyclerView, View.TRANSLATION_Y, DimenUtils.toDp(getContext(), 100), 0f).setDuration(300);
    translationY.setInterpolator(new DecelerateInterpolator());
    new Spruce.SpruceBuilder(recyclerView).sortWith(new DefaultSort(80))
        .animateWith(translationY, ObjectAnimator.ofFloat(recyclerView, View.ALPHA, 0, 1).setDuration(300))
        .start();
  }

  @Override public void onItemClick(NatureItem natureItem, View natureView) {
    fragmentNavigation.navigateToNatureDetailFragment(natureItem.getDrawableResId(), natureView);
  }
}
