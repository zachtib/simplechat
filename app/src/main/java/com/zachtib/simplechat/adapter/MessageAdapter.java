package com.zachtib.simplechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.zachtib.simplechat.R;
import com.zachtib.simplechat.model.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter
        extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder> {

    private Context context;
    public MessageAdapter(Context context, Query query) {
        super(Message.class, R.layout.item_message, MessageViewHolder.class, query);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
        String dt = "";
        if (model.timeStamp != null) {
            DateFormat df = new SimpleDateFormat("MMM dd h:mm a");
            dt = " - " + df.format(new Date((long)model.timeStamp));
        }

        viewHolder.messageTextView.setText(model.text);
        viewHolder.messengerTextView.setText(model.sender + dt);
        if (model.profilePhotoUrl != null) {
            Glide.with(context)
                    .load(model.profilePhotoUrl)
                    .into(viewHolder.messengerImageView);
        }
    }

    public static final class MessageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MessageAdapter";

        private View mView;

        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            mView = v;

            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }

        public View getView() {
            return mView;
        }
    }
}
