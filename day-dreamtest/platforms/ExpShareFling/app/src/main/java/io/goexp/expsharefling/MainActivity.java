package io.goexp.expsharefling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.scala.exp.android.sdk.Exp;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.channels.IChannel;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.SearchResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();


    private String uuid = "fb447921-484a-4949-8896-932666362db2";
    private String secret = "88686f693af312ed1dacba9e51ca6a33f3b6506612f3e46b6c212f964113db3a06e19bc3728fbb64cabab0690cc33829";
    private String user = "cesar.oyarzun@scala.com";
    //    private String password = "Com5715031@";
//     private String password = "5715031Com";
    private String password = "5715031Com@";
    private String org = "";
    //    public static final String host = "https://api.goexp.io";
    public static final String host =  "https://api-staging.goexp.io";
    private String url;
    private ListView listView ;
    private List<Location> listLocation=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        final LocationAdapter locationAdapter = new LocationAdapter(this,listLocation);
        listView.setAdapter(locationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;
                Location  itemValue    = (Location) listView.getItemAtPosition(position);
                IChannel channel = itemValue.getChannel();
                Map<String, Object> payload = new HashMap<String, Object>();
                payload.put("content", url);
                channel.fling(payload);
            }

        });

        final Map<String,Object> startOptions = new HashMap<>();
        startOptions.put(Utils.HOST,host);
        startOptions.put(Utils.SECRET,secret);
        startOptions.put(Utils.UUID,uuid);
        startOptions.put(Utils.ENABLE_EVENTS, true);
        Exp.start(startOptions).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "...START EXP SDK COMPLETED...");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(LOG_TAG, "...SDK ERROR START...", e);
            }

            @Override
            public void onNext(final Boolean aBoolean) {
                Log.i(LOG_TAG,"...SDK ONNEXT...");
                Map<String,Object> options = new HashMap<>();
                options.put("limit","1000");
                options.put("skip", "0");
                options.put("sort", "asc");
                Exp.findLocations(options)
                        .then(new Subscriber<SearchResults<Location>>() {
                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", e.toString());
                            }
                            @Override
                            public void onNext(SearchResults<Location> resultLocation) {
                                Log.i("Response", resultLocation.toString());
                                listLocation = resultLocation.getResults();
                                locationAdapter.addAll(resultLocation.getResults());
                                locationAdapter.notifyDataSetChanged();
                            }
                        });

            }
        });
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }


    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
           this.url = sharedText;

        }
    }
}
