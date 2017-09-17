package com.tbuonomo.androidanimations.view.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.NatureItemsAdapter;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import com.tbuonomo.androidanimations.view.util.DrawableUtils;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.sort.DefaultSort;

/**
 * Created by tommy on 04/09/17.
 */

public class SharedElementFragment extends Fragment {

  @BindView(R.id.shared_element_recycler_view) RecyclerView recyclerView;
  private boolean startAnimated;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_shared_element, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    NatureItemsAdapter adapter = new NatureItemsAdapter(DrawableUtils.getAllNatureItems(getContext()));
    recyclerView.setHasFixedSize(true);

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
    new Spruce.SpruceBuilder(recyclerView).sortWith(new DefaultSort(100))
        .animateWith(ObjectAnimator.ofFloat(recyclerView, View.TRANSLATION_Y, DimenUtils.toDp(getContext(), 200), 0f).setDuration(800),
            ObjectAnimator.ofFloat(recyclerView, View.ALPHA, 0, 1))
        .start();
  }
}
