package example.android.com.popularmoviesappstage.AND_Arch_Comp_Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import example.android.com.popularmoviesappstage.Models.Movie;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
public abstract class MovieRoom extends RoomDatabase {

    public abstract MovieDao movieDao();
    public static final Object LOCK = new Object();
    public static final String DB_NAME = "Movies_DB";
    public static  MovieRoom instance;

    public static MovieRoom getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                instance = Room.databaseBuilder(context.getApplicationContext(),MovieRoom.class,DB_NAME).build();
            }
        }
        return instance;
    }

}
