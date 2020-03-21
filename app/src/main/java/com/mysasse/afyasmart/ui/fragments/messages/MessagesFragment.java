package com.mysasse.afyasmart.ui.fragments.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mysasse.afyasmart.R;

public class MessagesFragment extends Fragment {

    public MessagesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MessagesViewModel mViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Register the views
        RecyclerView messagesRecyclerView = view.findViewById(R.id.messages_threads_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messagesRecyclerView.setHasFixedSize(true);

        FloatingActionButton startThreadFab = view.findViewById(R.id.start_thread_fab);

        startThreadFab.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.allChatUsersFragment));
    }
}
