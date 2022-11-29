package com.example.myapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Activity.PostActivity;
import com.example.myapp.Activity.BulletinBoardWriteActivity;
import com.example.myapp.StoreLink;
import com.example.myapp.R;
import com.example.myapp.PostWriteinfo;
import com.example.myapp.listener.OnPostListener;
import com.example.myapp.view.ReadContentsView;

import java.util.ArrayList;

public class BullentinBoardAdapter extends RecyclerView.Adapter<BullentinBoardAdapter.MainViewHolder> {
    private ArrayList<PostWriteinfo> mDataset;
    private Activity activity;
    private StoreLink storeLink;
    private final int MORE_INDEX = 2;

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public MainViewHolder(CardView v) {
            super(v);
            cardView = v;

        }
    }

    public BullentinBoardAdapter(Activity activity, ArrayList<PostWriteinfo> myDataset) {
        this.mDataset = myDataset;
        this.activity = activity;


        storeLink = new StoreLink(activity);
    }

    public void setOnPostListener(OnPostListener onPostListener){
        storeLink.setOnPostListener(onPostListener);
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public BullentinBoardAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PostActivity.class);
                intent.putExtra("writeinfo", mDataset.get(mainViewHolder.getBindingAdapterPosition()));
                activity.startActivity(intent);
            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getBindingAdapterPosition());
            }
        });

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);

        PostWriteinfo writeinfo = mDataset.get(position);
        titleTextView.setText(writeinfo.getTitle());

        ReadContentsView readContentsView = cardView.findViewById(R.id.readContentsView);
        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);

        if(contentsLayout.getTag() == null || !contentsLayout.getTag().equals(writeinfo)){
            contentsLayout.setTag(writeinfo);
            contentsLayout.removeAllViews();

            readContentsView.setMoreIndex(MORE_INDEX);
            readContentsView.setPostInfo(writeinfo);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View v, int position) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify:
                        startActivity(BulletinBoardWriteActivity.class,mDataset.get(position));
                        return true;
                    case R.id.delete:
                        storeLink.storageDelete(mDataset.get(position));
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }


    private void startActivity(Class c, PostWriteinfo writeinfo) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("writeinfo", writeinfo);
        activity.startActivity(intent);
    }
}
