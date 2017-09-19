package com.tbuonomo.androidanimations.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.item.NatureItem;
import java.util.List;

/**
 * Created by tommy on 06/09/17.
 */

public class NatureItemsAdapter extends RecyclerView.Adapter<NatureItemsAdapter.ViewHolder> {
  private List<NatureItem> natureItems;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public NatureItemsAdapter(List<NatureItem> natureItems) {
    this.natureItems = natureItems;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nature, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    int drawableResId = natureItems.get(position).getDrawableResId();
    holder.natureImage.setTransitionName(String.valueOf(drawableResId));
    Glide.with(context).load(drawableResId).listener(new RequestListener<Drawable>() {
      @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
      }

      @Override
      public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
      }
    }).into(holder.natureImage);

    holder.itemView.setOnClickListener(view -> {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClick(natureItems.get(position), holder.natureImage);
      }
    });
  }

  @Override public int getItemCount() {
    return natureItems.size();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onItemClick(NatureItem natureItem, View natureView);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_nature_image) public ImageView natureImage;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
