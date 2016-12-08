package hu.adam.sibilum.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hu.adam.sibilum.models.Channel;
import hu.adam.sibilum.models.Message;
import hu.adam.sibilum.models.User;

public class ApiHelper {

    /*
    public static User newUser(String name, int port) throws IOException, JSONException {
        return User.fromJson(new JSONObject(Api.newUser(name, port)));
    }

    public static List<User> getUsers() throws IOException, JSONException {
        JSONObject json         = new JSONObject(Api.getUsers());
        JSONArray json_users    = json.getJSONArray("users");

        List<User> users = new ArrayList<>();

        for(int i = 0; i < json_users.length(); i++) {
            users.add(User.fromJson(json_users.getJSONObject(i)));
        }

        return users;
    }

    public static Channel newChannel(String name) throws IOException, JSONException {
        return Channel.fromJson(new JSONObject(Api.newChannel(name)));
    }

    public static List<Channel> getChannels() throws IOException, JSONException {
        JSONObject json             = new JSONObject(Api.getChannels());
        JSONArray json_channels     = json.getJSONArray("channels");

        List<Channel> channels = new ArrayList<>();

        for(int i = 0; i < json_channels.length(); i++) {
            channels.add(Channel.fromJson(json_channels.getJSONObject(i)));
        }

        return channels;
    }

    public static Message newMessage(int channel, int user, String message) throws IOException, JSONException {
        return Message.fromJson(new JSONObject(Api.newMessage(channel, user, message)));
    }

    public static List<Message> getMessages(int channel) throws IOException, JSONException {
        JSONObject json             = new JSONObject(Api.getMessages(channel));
        JSONArray json_messages     = json.getJSONArray("messages");

        List<Message> messages = new ArrayList<>();

        for(int i = 0; i < json_messages.length(); i++) {
            messages.add(Message.fromJson(json_messages.getJSONObject(i)));
        }

        return messages;
    } */
}
