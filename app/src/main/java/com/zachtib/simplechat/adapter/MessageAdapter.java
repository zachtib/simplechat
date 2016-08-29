package com.zachtib.simplechat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zachtib.simplechat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter {
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
