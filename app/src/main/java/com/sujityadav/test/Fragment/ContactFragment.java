package com.sujityadav.test.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujityadav.test.Adapter.ChatAdapter;
import com.sujityadav.test.Adapter.ContactAdapter;
import com.sujityadav.test.Database.DatabaseHelper;
import com.sujityadav.test.Model.Contact;
import com.sujityadav.test.Model.Message;
import com.sujityadav.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private RecyclerView recyclerView;
ArrayList<Message> messages;
    public ContactFragment() {
        // Required empty public constructor
    }

    private boolean isLoaded =false,isVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        if(isVisibleToUser && isAdded() ){
            loadData();
            isLoaded =true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.RV);
        if(isVisibleToUser && (!isLoaded)){
            loadData();
            isLoaded=true;
        }

        return view;
    }

    private void loadData() {

        DatabaseHelper db = new DatabaseHelper(getContext());

        messages=new ArrayList<>();
        messages=db.GetMessageList();
        db.close();

        ArrayList<Contact> contacts= new ArrayList<>();
        Map<String,Integer> check = new HashMap<>();
        for(Message message:messages){

            if(!check.containsKey(message.username)){
                check.put(message.username,1);
                Contact contact = new Contact();
                contact.name=message.name;
                contact.url_image=message.imageUrl;
                DatabaseHelper db1 = new DatabaseHelper(getContext());
                contact.chat_count=db1.GetChatCount(message.name);
                contact.fav_count=db1.GetFavCount(message.name);
                db1.close();

                contacts.add(contact);

            }

        }

        setList(contacts);
    }

    private void setList(ArrayList<Contact> contacts) {

        ContactAdapter contactAdapter = new ContactAdapter(contacts,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

    }

}
