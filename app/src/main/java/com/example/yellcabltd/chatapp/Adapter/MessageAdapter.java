package com.example.yellcabltd.chatapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yellcabltd.chatapp.Model.Chat;
import com.example.yellcabltd.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private static final int SEND_MSG = 0;
    private static final int RECV_MSG = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser firebaseUser;

    public  MessageAdapter(Context mContext, List<Chat> mChat, String imageurl) {
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SEND_MSG) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.send_message, parent, false);
            return new ViewHolder(view);
        }
        else if (viewType == RECV_MSG){
            View view = LayoutInflater.from(mContext).inflate(R.layout.recv_message, parent, false);
            return new ViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());


        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.drawable.ic_avatar);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public ImageView profile_image;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            imageView = itemView.findViewById(R.id.username);

        }


    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(firebaseUser.getUid())) {
            return SEND_MSG; //if current user is the sender
        }
        else {
            return RECV_MSG; //if some other user sent the message
        }
    }
}

