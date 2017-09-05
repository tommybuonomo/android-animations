package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.InterpolatorsAdapter;

/**
 * Created by ebiz on 05/09/17.
 */

public class InterpolatorsFragment extends Fragment {
  @BindView(R.id.interpolators_recycler) RecyclerView recyclerView;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_interpolators, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(new InterpolatorsAdapter());
  }
}
