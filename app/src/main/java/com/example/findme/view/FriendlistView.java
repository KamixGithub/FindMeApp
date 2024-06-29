package com.example.findme.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.example.findme.R;
import com.example.findme.control.StorageController;
import com.example.findme.model.UserModel;

import org.intellij.lang.annotations.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FriendlistView extends Fragment {


    View view;

    Button showAddFriend;
    LinearLayout list;

    StorageController storageController;


    public FriendlistView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friendlist_view, container, false);
        list = view.findViewById(R.id.linearLayoutFriends);
        showAddFriend = view.findViewById(R.id.buttonShowAddFriend);
        showAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_addfriend, null);
                EditText mName = mView.findViewById(R.id.etName);
                EditText mFriendCode = mView.findViewById(R.id.etFriendCode);
                Button mAddFriend = mView.findViewById(R.id.buttonAddFriend);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();

                mAddFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                    }
                });

                dialog.show();

            }
        });

        RefreshList();
        return view;
    }


    private void RefreshList() {
        HashMap<Integer, UserModel> userList = storageController.getUserList();

        for (UserModel value : userList.values()) {
            if (!btnExit(value.getFriendcode())) {
                Button btn = new Button(view.getContext());
                btn.setText(value.getName());
                btn.setId(value.getFriendcode());
                btn.setBackgroundColor(Color.DKGRAY);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                list.addView(btn);
            }
        }

    }

    private boolean btnExit(int id) {
        for (int i = 1; i < list.getChildCount(); i++) {
            if (id == list.getChildAt(i).getId()) return true;
        }
        return false;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }
}