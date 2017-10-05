package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;

/**
 * Created by tommy on 05/10/17.
 */

public class LottieFragment extends Fragment {

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_lottie, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }
}
