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
import com.sujityadav.test.Model.Contact;
import com.sujityadav.test.Model.Message;
import com.sujityadav.test.R;

import java.util.List;

/**
 * Created by sujit yadav on 1/26/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    List<Contact> contacts;
    private Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user, chatcount, favcount;
        public ImageView image1,image2;

        public MyViewHolder(View view) {
            super(view);
            user = (TextView) view.findViewById(R.id.user);
            chatcount = (TextView) view.findViewById(R.id.chatcount);
            favcount = (TextView) view.findViewById(R.id.favcount);
            image1 = (ImageView)view.findViewById(R.id.image1);
            image2 = (ImageView)view.findViewById(R.id.image2);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     Contact contact= contacts.get(position);
        holder.user.setText(contact.name);
        holder.chatcount.setText(String.valueOf(contact.chat_count));
        holder.favcount.setText(String.valueOf(contact.fav_count));

        if(!contact.url_image.equals("")){

            holder.image1.setVisibility(View.GONE);
            holder.image2.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(contact.url_image)
                    .into(holder.image2);

        }

        else {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.GONE);
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
            int color2=0;
            int color1 = 0;

            TextDrawable drawable;

                color1 = generator.getColor(contact.name);
                drawable = TextDrawable.builder()
                        .buildRound(String.valueOf(contact.name.charAt(0)), color1);

            holder.image1.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
