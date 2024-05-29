package com.example.myapplication;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Random;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.getImage() != null) {
            holder.messageImageView.setVisibility(View.VISIBLE);
            holder.imageStrokeView.setVisibility(View.VISIBLE);
            holder.messageTextView.setVisibility(View.GONE);
            holder.messageImageView.setImageBitmap(message.getImage());
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE);
            holder.messageImageView.setVisibility(View.GONE);
            holder.imageStrokeView.setVisibility(View.GONE);
            holder.messageTextView.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public ImageView messageImageView;
        public ImageView imageStrokeView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.textMessage);
            messageImageView = itemView.findViewById(R.id.imageMessage);
            imageStrokeView = itemView.findViewById(R.id.imageStroke);
        }
    }
}
