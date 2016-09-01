package com.zachtib.simplechat.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zachtib.simplechat.R;
import com.zachtib.simplechat.SimpleChat;
import com.zachtib.simplechat.presenter.IChannelListPresenter;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelListView extends Fragment implements IChannelListView {

    @Inject
    IChannelListPresenter mChannelListPresenter;

    public ChannelListView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SimpleChat) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channel_list_view, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
