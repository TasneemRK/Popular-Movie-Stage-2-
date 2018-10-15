package example.android.com.popularmoviesappstage.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

public final class CalNoOfColumnsAccordingToWidth {

    private Context context;

    public CalNoOfColumnsAccordingToWidth(Context context) {
        this.context = context;
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
