package com.example.findme.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.findme.R;

public class FriendlistView extends Fragment {


    View view;

    Button addButton;

    int count;



    public FriendlistView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_friendlist_view, container, false);

        addButton = view.findViewById(R.id.buttonAddFriend);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdder();
            }
        });


        return view;
    }

    private void buttonAdder() {
        Button btn = new Button(view.getContext());
        btn.setId(Integer.parseInt(String.valueOf(count)));
        count++;
        LinearLayout layout = view.findViewById(R.id.linearLayoutFriends);
        layout.addView(btn);
    }


}