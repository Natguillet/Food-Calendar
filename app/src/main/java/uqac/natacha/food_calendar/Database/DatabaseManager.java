package uqac.natacha.food_calendar.Database;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uqac.natacha.food_calendar.Modele.User;


public class DatabaseManager {

    private final static String USERS = "users";

    private  User user;

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
                user = dataSnapshot.getValue(User.class);
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

}
