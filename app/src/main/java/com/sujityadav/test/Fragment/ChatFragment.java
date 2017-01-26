package com.sujityadav.test.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sujityadav.test.Adapter.ChatAdapter;
import com.sujityadav.test.Database.DatabaseHelper;
import com.sujityadav.test.Helper.GsonRequest;
import com.sujityadav.test.Helper.VolleySingleton;
import com.sujityadav.test.Model.ChatData;
import com.sujityadav.test.Model.Message;
import com.sujityadav.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    private RecyclerView recyclerView;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.RV);
        DatabaseHelper db= new DatabaseHelper(getActivity());
        List<Message> messages = new ArrayList<>();
        messages=  db.GetMessageList();
        db.close();
        if(messages.size()==0){
            setList();
        }
        else{
            setList1(messages);
        }

        return view;
    }

    private void setList1(List<Message> messages) {
        ChatAdapter chatAdapter = new ChatAdapter(messages,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);

    }

    private void setList() {

        String url = "http://haptik.mobi/android/test_data/";

        GsonRequest<ChatData> myReq = new GsonRequest<ChatData>(
                Request.Method.GET,
                url,
                ChatData.class,
                new Response.Listener<ChatData>() {
                    @Override
                    public void onResponse(final ChatData response) {
                        DatabaseHelper db=new DatabaseHelper(getActivity());
                        for(Message message:response.messages){
                            db.AddChatList(message);
                        }
                       db.close();
                            ChatAdapter chatAdapter = new ChatAdapter(response.messages,getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(chatAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"error gettting messages",Toast.LENGTH_LONG).show();
                    }
                }


        );

        VolleySingleton.getInstance(getContext()).addToRequestQueue(myReq);

    }
}
