package com.example.findme.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.findme.R;
import com.example.findme.control.StorageController;
import com.example.findme.model.UserModel;

import java.util.HashMap;

public class FriendlistView extends Fragment {

    private View view;
    private Button showAddFriend;
    private LinearLayout list;
    private StorageController storageController;
    private UserModel focus;
    private View selectedView; // to keep track of the selected button

    public FriendlistView(StorageController storageController, UserModel focus) {
        this.storageController = storageController;
        this.focus = focus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friendlist_view, container, false);
        list = view.findViewById(R.id.linearLayoutFriends);
        showAddFriend = view.findViewById(R.id.buttonShowAddFriend);

        showAddFriend.setOnClickListener(v -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_addfriend, null);
            EditText mName = mView.findViewById(R.id.etName);
            EditText mFriendCode = mView.findViewById(R.id.etFriendCode);
            Button mAddFriend = mView.findViewById(R.id.buttonAddFriend);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();

            mAddFriend.setOnClickListener(view -> {
                if (!mName.getText().toString().isEmpty() && !mFriendCode.getText().toString().isEmpty()) {
                    String name = mName.getText().toString();
                    int friendCode = Integer.parseInt(mFriendCode.getText().toString());

                    if (storageController.getUser(friendCode) == null) {
                        storageController.addFriend(new UserModel(name, friendCode));
                        RefreshList();
                        Toast.makeText(getActivity(), "Friend Added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Friend with this Code already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please fill any empty fields", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        });

        RefreshList();
        return view;
    }

    private void RefreshList() {
        HashMap<Integer, UserModel> userList = storageController.getUserList();

        for (UserModel value : userList.values()) {
            if (!btnExists(value.getFriendcode())) {
                Button btn = new Button(view.getContext());
                btn.setText(value.getName());
                btn.setId(value.getFriendcode());
                btn.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.rounded_button));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(50, 25, 50, 25);
                btn.setLayoutParams(params);

                btn.setOnClickListener(v -> {
                    focus = storageController.getUser(btn.getId());
                    Toast.makeText(getActivity(), "Searching " + btn.getText() + "...", Toast.LENGTH_SHORT).show();
                });

                btn.setOnLongClickListener(v -> {
                    showPopupMenu(v);
                    return true;
                });

                list.addView(btn);
            }
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_delete, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return handleMenuItemClick(item, v);
            }
        });
        popup.show();
    }

    private boolean handleMenuItemClick(MenuItem item, View v) {
        if (item.getItemId() == R.id.option_delete) {
            if (v instanceof Button) {
                storageController.deleteFriend(v.getId());
                list.removeView(v);
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    private boolean btnExists(int id) {
        for (int i = 0; i < list.getChildCount(); i++) {
            if (id == list.getChildAt(i).getId()) return true;
        }
        return false;
    }

    public UserModel getFocus() {
        return focus;
    }
}
