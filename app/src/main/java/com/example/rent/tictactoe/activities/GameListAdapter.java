package com.example.rent.tictactoe.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rent.tictactoe.R;
import com.example.rent.tictactoe.models.gameList.model.GameListItem;

import java.util.List;

/**
 * Created by RENT on 2017-02-04.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListItemHolder> {

    List<GameListItem> list;
    OnGameListItemClickedListener listener;

    public GameListAdapter(List<GameListItem> list, OnGameListItemClickedListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public GameListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        return new GameListItemHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(GameListItemHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
        holder.item = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GameListItemHolder extends  RecyclerView.ViewHolder{


        TextView textView;
        GameListItem item;
        OnGameListItemClickedListener listener;

        public GameListItemHolder(View v, final OnGameListItemClickedListener listener) {
            super(v);
            this.listener = listener;
            textView = (TextView) v.findViewById(R.id.game_list_item_textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGameListItemClicked(item);
                }
            });
        }
    }

    public interface OnGameListItemClickedListener{
        public void onGameListItemClicked(GameListItem item);
    }
}
