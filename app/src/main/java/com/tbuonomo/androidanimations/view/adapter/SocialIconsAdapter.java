package com.tbuonomo.androidanimations.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.item.SocialItem;
import java.util.List;

/**
 * Created by tommy on 06/09/17.
 */

public class SocialIconsAdapter extends RecyclerView.Adapter<SocialIconsAdapter.ViewHolder> {
  private List<SocialItem> socialItems;
  private Context context;

  public SocialIconsAdapter(List<SocialItem> socialItems) {
    this.socialItems = socialItems;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_icon, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.socialImage.setImageDrawable(ContextCompat.getDrawable(context, socialItems.get(position).getDrawableResId()));
  }

  @Override public int getItemCount() {
    return socialItems.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_social_image) ImageView socialImage;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
