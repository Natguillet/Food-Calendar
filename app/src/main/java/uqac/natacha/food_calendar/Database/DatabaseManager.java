package uqac.natacha.food_calendar.Database;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import uqac.natacha.food_calendar.Modele.User;

/**
 * Created by Natiassa on 2017-11-30.
 */

public class DatabaseManager {

    private final static String USERS = "users";


    private Reference ref;

    private static DatabaseManager INSTANCE = new DatabaseManager();

    private DatabaseManager()
    {
        ref = new Reference();
    }

    public static DatabaseManager getInstance()
    {
        return INSTANCE;
    }

    // ----- USERS ----- //


    public void getUser(final String uid, final Result<User> result)
    {
        ref.users.child(uid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    result.onSuccess(user.setUid(uid));
                } else {
                    result.onFailure();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                result.onError(databaseError);
            }
        });
    }

    public Task<Void> setUser(final User user)
    {
        return ref.users.child(user.getUid()).setValue(user);
    }

    public void addUserEventListener(final String uid, final ResultListener<User> result)
    {
        if (result.listener == null) {
            result.listener = new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        result.onSuccess(user.setUid(uid));
                    } else {
                        result.onFailure();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    result.onError(databaseError);
                }
            };
        }

        if (!result.added) {
            ref.users.child(uid).addValueEventListener(result.listener);
            result.added = true;
        }
    }

    public void removeUserEventListener(final String uid, final ResultListener<User> result)
    {
        ref.users.child(uid).removeEventListener(result.listener);
        result.added = false;
    }

    // ----- INNER CLASS ----- //

    private class Reference
    {
        private DatabaseReference database;
        private DatabaseReference users;

        Reference()
        {
            database = FirebaseDatabase.getInstance().getReference();
            users = database.child(USERS);
        }
    }

    public static abstract class Result<T>
    {
        public void onComplete() {}

        public abstract void onSuccess(T t);

        public void onFailure()
        {
            onError(DatabaseError.fromException(new Exception("Data not found")));
        }

        public void onError(DatabaseError error)
        {
            Log.d("DATABASE ERROR", error.getMessage());
        };
    }

    public static abstract class ResultListener<T> extends Result<T>
    {
        private boolean added;
        private ValueEventListener listener;

        public ResultListener<T> setListener(ValueEventListener listener)
        {
            this.listener = listener;
            return this;
        }
    }
}
