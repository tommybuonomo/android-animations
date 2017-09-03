package com.tbuonomo.androidanimations.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.materialsquareloading.MaterialSquareLoading;

/**
 * Created by tommy on 03/09/17.
 */

public class WelcomeFragment extends Fragment {

  @BindView(R.id.welcome_square_loading_view) MaterialSquareLoading materialSquareLoading;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    materialSquareLoading.show();
  }

}
