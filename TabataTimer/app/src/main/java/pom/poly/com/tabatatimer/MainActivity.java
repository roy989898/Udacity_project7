package pom.poly.com.tabatatimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pom.poly.com.tabatatimer.ContentProvider.Contract;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.Fragment.CalenderFragment;
import pom.poly.com.tabatatimer.Fragment.RankingFragment;
import pom.poly.com.tabatatimer.Fragment.TimerFragment;
import pom.poly.com.tabatatimer.Fragment.UserMessageFragment;
import pom.poly.com.tabatatimer.Utility.Utility;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ContentResolver resolver;
    private ValueEventListener eventLister;
    private ChildEventListener childListener;
    private DatabaseReference ref;
    private FrameLayout tabletContainer;


    @Override
    protected void onPause() {
        Log.d("MainActivity", "in onPause");
        removeallTheFireBaseListener();
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "in onStop");
    }

    private void removeallTheFireBaseListener() {
        Log.d("MainActivity", "removeallTheFireBaseListener");
        if (eventLister != null && ref != null) {
            ref.removeEventListener(eventLister);
            Log.d("MainActivity", "removeEventListener");

        }
        if ((childListener != null && ref != null)) {
            ref.removeEventListener(childListener);
            Log.d("MainActivity", "removeEventListener");
        }
    }

    private void deleteAllUserData(ContentResolver resolver) {
        resolver.delete(Contract.UserEntry.CONTENT_URI, null, null);

    }

    private void deleteUser(ContentResolver resolver, User user) {
        resolver.delete(Contract.UserEntry.CONTENT_URI, Contract.UserEntry.COLUMN_EMAIL + "=?", new String[]{user.email + ""});
    }

    private void readAllFromFirebase(final ContentResolver resolver) {

        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        eventLister = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot ds : children) {
                    User user = ds.getValue(User.class);
                    Log.i("Firebase", user.userName);

                    insertUserData(resolver, user);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("read users", "The read failed: " + databaseError.getMessage());
            }
        };
//        ref.addListenerForSingleValueEvent(eventLister);//read the use data
        ref.addValueEventListener(eventLister);
    }

    private void insertUserData(ContentResolver resolver, User user) {
        ContentValues values = new ContentValues();
        values.put(Contract.UserEntry.COLUMN_EMAIL, user.email);
        values.put(Contract.UserEntry.COLUMN_LIKENUMBER, user.likeNumber);
        values.put(Contract.UserEntry.COLUMN_PROFILE_LINK, user.profileLink);
        values.put(Contract.UserEntry.COLUMN_TOTAL_TIME, user.totaltime);
        values.put(Contract.UserEntry.COLUMN_USERID, user.userID);
        values.put(Contract.UserEntry.COLUMN_NAME, user.userName);
        values.put(Contract.UserEntry.COLUMN_LAST_UPDTAETIME, user.lastupdateTime_totalTime);

        try {
            resolver.insert(Contract.UserEntry.CONTENT_URI, values);
        }catch (Exception e){
            Log.d("insert error",e.toString());
        }

    }

    private void updateUserDate(ContentResolver resolver, User user) {
        ContentValues values = new ContentValues();
        values.put(Contract.UserEntry.COLUMN_EMAIL, user.email);
        values.put(Contract.UserEntry.COLUMN_LIKENUMBER, user.likeNumber);
        values.put(Contract.UserEntry.COLUMN_PROFILE_LINK, user.profileLink);
        values.put(Contract.UserEntry.COLUMN_TOTAL_TIME, user.totaltime);
        values.put(Contract.UserEntry.COLUMN_USERID, user.userID);
        values.put(Contract.UserEntry.COLUMN_NAME, user.userName);



        try {
            //        resolver.insert(Contract.UserEntry.CONTENT_URI, values);
            resolver.update(Contract.UserEntry.CONTENT_URI, values, Contract.UserEntry.COLUMN_EMAIL + "=?", new String[]{user.email + ""});
        }catch (Exception e){
            Log.d("update error",e.toString());
        }

    }

    private void readWhenupdate(final ContentResolver resolver) {

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

//        ref.addListenerForSingleValueEvent(eventLister);//read the use data
        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//             Must call this one time at the first time
                User user = dataSnapshot.getValue(User.class);
                insertUserData(resolver, user);

                Log.i("Firebase", "onChildAdded: " + user.userName);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                updateUserDate(resolver, user);

                Log.i("Firebase", "onChildChanged: " + user.userName);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                deleteUser(resolver, user);

                Log.i("Firebase", "onChildRemoved: " + user.userName);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "postComments:onCancelled", databaseError.toException());
            }
        };
        ref.addChildEventListener(childListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);

        //fin the fragment_message_container,if,is tablet,has;
        tabletContainer = (FrameLayout) findViewById(R.id.fragment_message_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        //set the tab icon
        setTabIcon(tabLayout);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        resolver = getContentResolver();


        /*if (Utility.getUid() == null) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentuser = mAuth.getCurrentUser();
            String uidSave = currentuser.getUid();
            Utility.setUid(getApplicationContext(), uidSave);
        }*/

    }

    private void setTabIcon(TabLayout tabLayout) {
        if (tabletContainer != null) {
// is tablet
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_stars_white_24px).setContentDescription(getString(R.string.cd_ranking_tab));
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_timer_white_24px).setContentDescription(getString(R.string.cd_timer_tab));
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_date_range_white_24px).setContentDescription(getString(R.string.cd_calcender_tab));
        } else {
//            not tablet
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_stars_white_24px).setContentDescription(getString(R.string.cd_ranking_tab));
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_timer_white_24px).setContentDescription(getString(R.string.cd_timer_tab));
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_date_range_white_24px).setContentDescription(getString(R.string.cd_calcender_tab));
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_dns_white_24px).setContentDescription(getString(R.string.cd_received_message_tab));
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        deleteAllUserData(resolver);
        readWhenupdate(resolver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent SeetingIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(SeetingIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (tabletContainer != null) {
//                is tablet
                switch (position) {

                    case 0:
                        return new RankingFragment();
                    case 1:
                        return new TimerFragment();

                    case 2:
                        CalenderFragment calenderFragment = new CalenderFragment();
                        Eventinf.registerObserver(calenderFragment);//register to the Eventinf as a observer,when ssave a new Eventif or delete,notify the calenderFragment,data has chanhe.
                        return calenderFragment;

                    default:
                        return null;
                }
            } else {
//                not tablet
                switch (position) {

                    case 0:
                        return new RankingFragment();
                    case 1:
                        return new TimerFragment();

                    case 2:
                        CalenderFragment calenderFragment = new CalenderFragment();
                        Eventinf.registerObserver(calenderFragment);//register to the Eventinf as a observer,when ssave a new Eventif or delete,notify the calenderFragment,data has chanhe.
                        return calenderFragment;

                    case 3:
                        return new UserMessageFragment();

                    default:
                        return null;
                }
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            if (tabletContainer != null) {
//                is tablet
                return 3;
            } else {
//                not tablet
                return 4;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                /*case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                */
                default:
                    return "";
            }

        }
    }
}