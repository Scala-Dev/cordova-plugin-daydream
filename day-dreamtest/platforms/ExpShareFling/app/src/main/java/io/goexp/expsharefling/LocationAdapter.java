package io.goexp.expsharefling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scala.exp.android.sdk.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar Oyarzun on 12/21/16.
 */
public class LocationAdapter extends ArrayAdapter<Location> {

    public LocationAdapter(Context context, List<Location> locations) {
        super(context, 0, locations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Location location = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textview, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        tvName.setText(location.getString("name"));


        return convertView;
    }
}
