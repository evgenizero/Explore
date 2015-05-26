package bg.uni_sofia.fmi.explore.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import bg.uni_sofia.fmi.explore.filters.PlaceAlphaCriteria;
import bg.uni_sofia.fmi.explore.filters.PlaceDistanceCriteria;
import de.greenrobot.event.EventBus;

/**
 * Created by eyanev on 5/25/15.
 */
public class FilterDialog extends DialogFragment {
    public static final String TAG = "FilterDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] filters = new String[] {"Distance", "Alphabetically"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose filter")
                .setItems(filters, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                EventBus.getDefault().post(new PlaceDistanceCriteria());
                                break;
                            }
                            case 1: {
                                EventBus.getDefault().post(new PlaceAlphaCriteria());
                                break;
                            }
                        }
                    }
                });
        return builder.create();
    }
}
