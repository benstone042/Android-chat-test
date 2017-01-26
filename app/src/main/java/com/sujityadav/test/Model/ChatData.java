
package com.sujityadav.test.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatData {

    @SerializedName("count")
    @Expose
    public Integer count;
    @SerializedName("messages")
    @Expose
    public List<Message> messages = null;

}
