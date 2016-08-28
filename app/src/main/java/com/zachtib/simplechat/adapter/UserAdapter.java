package com.zachtib.simplechat.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zachtib.simplechat.R;
import com.zachtib.simplechat.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends ArrayAdapter<User> {

    public static class UserViewHolder {
        public CircleImageView userImageView;
        public TextView userNameView;
        public TextView userEmailView;

        public UserViewHolder(View v) {
            userImageView = (CircleImageView) v.findViewById(R.id.userImageView);
            userNameView = (TextView) v.findViewById(R.id.userNameView);
            userEmailView = (TextView) v.findViewById(R.id.userEmailView);
        }
    }
    UserViewHolder holder;

    private final Activity context;
    private final List<User> users;

    public UserAdapter(Activity context, List<User> users) {
        super(context, R.layout.item_user, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = users.get(position);

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_user, parent, false);
            holder = new UserViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UserViewHolder) convertView.getTag();
        }

        holder.userEmailView.setText(user.email);
        holder.userNameView.setText(user.username);
        Glide.with(context)
                .load(user.photoUrl)
                .into(holder.userImageView);
        return convertView;
    }
}
