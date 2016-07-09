package pom.poly.com.tabatatimer.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Adapter.MessageRecycleAdapter;
import pom.poly.com.tabatatimer.Firebase.Message;
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMessageFragment extends Fragment {


    @BindView(R.id.revMessage)
    RecyclerView revMessage;
    @BindView(R.id.imvProfilePicture)
    SimpleDraweeView imvProfilePicture;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.txLikeNumber)
    TextView txLikeNumber;


    private DatabaseReference messagesRef;
    private ChildEventListener childListener;
    private String uID;
    private ArrayList<Message> messageArrayList;
    private MessageRecycleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ValueEventListener userListener;
    private DatabaseReference userRef;

    public UserMessageFragment() {
        // Required empty public constructor


    }

    @Override
    public void onResume() {
        super.onResume();
        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages").child(uID);

        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                message.firebaseKey = dataSnapshot.getKey();
                messageArrayList.add(message);

                adapter.notifyDataSetChanged();


                Log.d("messages", adapter.getItemCount() + "");
                /*String toastShow = "from: " + message.fromID + " content: " + message.message;
                Log.d("messages", toastShow);*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //no implement

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                messageArrayList.remove(message);
                adapter.notifyDataSetChanged();

              /*  for (Message m:messageArrayList) {
                    String toastShow = "from: " + m.fromID + " content: " + m.message;
                    Log.d("messages remove: ", toastShow);

                }*/
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
        if (childListener != null && messagesRef != null) {
            messagesRef.removeEventListener(childListener);
        }
        if (userListener != null && userRef != null) {
            userRef.removeEventListener(userListener);
        }


    }

    private void setTheUserDataShow(Context context) {

        Uri defaulrUri = new Uri.Builder().scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(String.valueOf(R.drawable.ic_account_circle_white_24dp)).build();
        String defaulrUriString = defaulrUri.toString();
        String profilePURL = context.getSharedPreferences(getString(R.string.name_sharepreference), context.MODE_PRIVATE).getString(getString(R.string.SharePreferenceDownloadLinkKey), defaulrUriString);
        imvProfilePicture.setImageURI(profilePURL);

        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                //set the user name and like number
                tvName.setText(user.userName);
                txLikeNumber.setText(user.likeNumber + "");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userRef.addValueEventListener(userListener);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_messagev2, container, false);
        ButterKnife.bind(this, view);

        messageArrayList = new ArrayList<>();


        layoutManager = new LinearLayoutManager(getContext());
        revMessage.setLayoutManager(layoutManager);

        adapter = new MessageRecycleAdapter(messageArrayList);
        revMessage.setAdapter(adapter);
        //set the recycle swipe action
        ItemTouchHelper.SimpleCallback callback = createCallback(adapter);

        ItemTouchHelper toouch = new ItemTouchHelper(callback);
        toouch.attachToRecyclerView(revMessage);

        setTheUserDataShow(getContext());


        return view;
    }

    private ItemTouchHelper.SimpleCallback createCallback(final MessageRecycleAdapter adapter) {

        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        // delete the iteamin arraylist
                        int position = viewHolder.getPosition();
                        String key = new String(adapter.getMessageArra().get(position).firebaseKey);
                        adapter.remove(position);
                        adapter.notifyItemRemoved(position);
                        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference().child("Messages").child(uID).child(key);
                        messageRef.setValue(null);

                        break;
                }

            }
        };
    }


}
