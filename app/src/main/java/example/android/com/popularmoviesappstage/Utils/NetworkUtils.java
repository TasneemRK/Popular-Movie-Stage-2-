package example.android.com.popularmoviesappstage.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public final class  NetworkUtils  {

    private Context context;

    private NetworkUtils() {
    }

    public NetworkUtils(Context context) {
        this.context = context;
    }

    public static URL buildUrl(String urlString) {
        Uri builtUri = Uri.parse(urlString);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildTrailerUrl(String id) {

        Uri builtUri = Uri.parse(Constant.BASE_URL).buildUpon()
                .appendPath(Constant.MOVIE)
                .appendPath(id)
                .appendPath(Constant.VIDEOS)
                .appendQueryParameter(Constant.API_KEY1,Constant.apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildReviewUrl(String id) {

        Uri builtUri = Uri.parse(Constant.BASE_URL).buildUpon()
                .appendPath(Constant.MOVIE)
                .appendPath(id)
                .appendPath(Constant.REVIEWS)
                .appendQueryParameter(Constant.API_KEY1,Constant.apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}
