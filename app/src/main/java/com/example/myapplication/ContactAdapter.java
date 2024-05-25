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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private ClickListener clickListener;
    private Random random;

    public ContactAdapter(List<Contact> contactList, ClickListener clickListener) {
        this.contactList = contactList;
        this.clickListener = clickListener;
        this.random = new Random();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.statusTextView.setText(contact.getStatus());

        // تنظیم گرادینت پویا برای تصویر پروفایل
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                getRandomGradientColors());
        gradientDrawable.setShape(GradientDrawable.OVAL);

        holder.profileImageView.setBackground(gradientDrawable);
    }

    private int[] getRandomGradientColors() {
        int startColor = getRandomColor();
        int endColor = adjustColorBrightness(startColor, 0.5f);
        return new int[]{startColor, endColor};
    }

    private int getRandomColor() {
        // تولید یک رنگ تصادفی شاد
        return 0xff000000 | random.nextInt(0x00ffffff);
    }

    private int adjustColorBrightness(int color, float factor) {
        int a = (color >> 24) & 0xff;
        int r = Math.min((int)(((color >> 16) & 0xff) * factor), 255);
        int g = Math.min((int)(((color >>  8) & 0xff) * factor), 255);
        int b = Math.min((int) ((color       & 0xff) * factor), 255);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView statusTextView;
        public ImageView profileImageView;
        private ClickListener clickListener;

        public ContactViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            statusTextView = itemView.findViewById(R.id.status);
            profileImageView = itemView.findViewById(R.id.profile_image);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}
