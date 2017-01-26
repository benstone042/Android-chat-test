
package com.sujityadav.test.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("body")
    @Expose
    public String body;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("image-url")
    @Expose
    public String imageUrl;
    @SerializedName("message-time")
    @Expose
    public String messageTime;
    @SerializedName("fav")
    @Expose
    public Integer fav=0;

}
