package com.mysasse.afyasmart.ui.fragments.notifications;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mysasse.afyasmart.R;
import com.mysasse.afyasmart.data.models.Notification;

public class NotificationsFragment extends Fragment implements NotificationsAdapter.NotificationClickListener {

    private RecyclerView notificationsRecyclerView;
    private NotificationsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notifications_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Register the necessary views
        notificationsRecyclerView = view.findViewById(R.id.notification_recycler_view);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        mViewModel.getAllNotifications().observe(getViewLifecycleOwner(), notifications -> {
            NotificationsAdapter adapter = new NotificationsAdapter(this, notifications);
            notificationsRecyclerView.setAdapter(adapter);
        });

    }

    @Override
    public void onClick(Notification notification) {
        CharSequence[] items = new CharSequence[]{"Switch Account Mode", "Delete notification"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    NotificationsFragmentDirections.ActionNotificationsFragmentToSwitchRoleFragment action =
                            NotificationsFragmentDirections.actionNotificationsFragmentToSwitchRoleFragment(notification.getUserId(), notification.getExpertise());
                    Navigation.findNavController(notificationsRecyclerView).navigate(action);
                    break;
                case 1:
                    mViewModel.deleteNotification(notification);
                    break;
                default:
                    Toast.makeText(getContext(), "Select from the available options please", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();

    }
}
