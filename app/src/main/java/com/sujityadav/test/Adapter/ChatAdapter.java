package com.sujityadav.test.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;
import com.sujityadav.test.Database.DatabaseHelper;
import com.sujityadav.test.Model.Message;
import com.sujityadav.test.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sujit yadav on 1/26/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int OTHER = 0;
    private final int SELF = 1;

    private List<Message> messages;
    private Context context;

    public ChatAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OTHER) {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_user1_item, parent, false);
            return new OtherViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_user2_item, parent, false);
            return new SelfViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OtherViewHolder) {
            final Message chatMessage = messages.get(holder.getAdapterPosition());
            ((OtherViewHolder) holder).messageUserName.setText(chatMessage.name);
            ((OtherViewHolder) holder).message.setText(chatMessage.body);
            ((OtherViewHolder) holder).messageTime.setText((chatMessage.messageTime).toString().substring(11, 16));
            if(chatMessage.fav==1){
                ((OtherViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));
            }
            else {
                ((OtherViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,android.R.color.darker_gray));
            }
            ((OtherViewHolder) holder).fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chatMessage.fav==0){
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.updateFav(chatMessage.messageTime);
                        db.close();
                        ((OtherViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));

                        RefreshData();
                    }

                }
            });


                Picasso.with(context)
                        .load(chatMessage.imageUrl)
                        .into(((OtherViewHolder) holder).image);





        } else if (holder instanceof SelfViewHolder) {
            final Message chatMessage = messages.get(holder.getAdapterPosition());
            ((SelfViewHolder) holder).messageSelfName.setText(chatMessage.name);
            ((SelfViewHolder) holder).messageSelf.setText(chatMessage.body);
            ((SelfViewHolder) holder).messageTimeSelf.setText((chatMessage.messageTime).toString().substring(11, 16));
            if(chatMessage.fav==1){
                ((SelfViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));
            }
            else {
                ((SelfViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,android.R.color.darker_gray));
            }
            ((SelfViewHolder) holder).fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(chatMessage.fav==0){
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.updateFav(chatMessage.messageTime);
                        db.close();
                        ((SelfViewHolder) holder).fav.setColorFilter(ContextCompat.getColor(context,R.color.colorAccent));
                        RefreshData();
                    }

                }
            });
        }
    }

    private void RefreshData() {
        DatabaseHelper db= new DatabaseHelper(context);
        messages.clear();
        messages=db.GetMessageList();
        db.close();
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).imageUrl.equals(""))
            return 1;
        else return 0;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView messageTime, messageUserName, message;
        ImageView fav,image;

        public OtherViewHolder(View view) {
            super(view);
            messageTime = (TextView) view.findViewById(R.id.message_time);
            messageUserName = (TextView) view.findViewById(R.id.message_user_name);
            message = (TextView) view.findViewById(R.id.message);
            fav=(ImageView)view.findViewById(R.id.fav);
            image=(ImageView)view.findViewById(R.id.image2);
        }
    }

    public static class SelfViewHolder extends RecyclerView.ViewHolder {
        TextView messageTimeSelf, messageSelfName, messageSelf;
        ImageView fav;

        public SelfViewHolder(View view) {
            super(view);
            messageTimeSelf = (TextView) view.findViewById(R.id.message_time_self);
            messageSelfName = (TextView) view.findViewById(R.id.message_self_name);
            messageSelf = (TextView) view.findViewById(R.id.message_self);
            fav=(ImageView)view.findViewById(R.id.fav);
        }
    }
}