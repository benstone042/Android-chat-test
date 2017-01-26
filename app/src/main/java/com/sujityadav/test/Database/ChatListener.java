package com.sujityadav.test.Database;

import com.sujityadav.test.Model.Message;

import java.util.ArrayList;

/**
 * Created by sujit yadav on 1/26/2017.
 */

public interface ChatListener {

    void AddChatList(Message message);

    ArrayList<Message> GetMessageList();

    int GetFavCount(String username);

    int GetChatCount(String username);

    void updateFav(String msgTime);


}
