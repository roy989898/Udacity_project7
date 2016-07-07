package pom.poly.com.tabatatimer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Firebase.Message;
import pom.poly.com.tabatatimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMessageFragment extends Fragment {


    @BindView(R.id.revMessage)
    RecyclerView revMessage;
    private DatabaseReference messagesRef;
    private ChildEventListener childListener;
    private String uID;
    private ArrayList<Message> messageArrayList;

    public UserMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages").child(uID);

        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                String toastShow = "from: " + message.fromID + " content: " + message.message;
                messageArrayList.add(message);

                Log.d("messages", toastShow);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //no implement

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                messageArrayList.remove(message);

                for (Message m:messageArrayList) {
                    String toastShow = "from: " + m.fromID + " content: " + m.message;
                    Log.d("messages remove: ", toastShow);

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //no implement

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //mo implement
            }
        };

        messagesRef.addChildEventListener(childListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (childListener != null) {
            messagesRef.removeEventListener(childListener);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_message, container, false);
        ButterKnife.bind(this, view);

         messageArrayList = new ArrayList<>();
        return view;
    }

}
