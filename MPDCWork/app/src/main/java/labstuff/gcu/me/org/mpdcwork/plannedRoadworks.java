package labstuff.gcu.me.org.mpdcwork;

/**
 * Created by Home on 17/03/2018.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import labstuff.gcu.me.org.mobileplatcw.R;

public class plannedRoadworks extends ListActivity {

    RadioButton RB0; //radio button for PR
    RadioButton RB2; //radio button for PR
    Button Home; //button for PR
    ViewFlipper VF;//view flipper for PR
    ListView listView; // list view for PR
    SearchView searchBar;//Search Bar for PR


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roadworks_main);
        Log.e(getPackageName(), "just before avw");
        RB0 = (RadioButton) findViewById(R.id.radio0);
        Home = (Button) findViewById(R.id.home);
        RB2 = (RadioButton) findViewById(R.id.radio2);
        VF = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        listView = (ListView) findViewById(R.id.listV);
        searchBar = (SearchView) findViewById(R.id.searchView);//Search View

/*        initialising the on click listener for radio buttons*/
        RB0.setOnClickListener(radio_listener);
        RB2.setOnClickListener(radio_listener);

        /*On click listener for home button to take user back to homepage*/
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(plannedRoadworks.this,MainActivity.class);
                startActivity(in);
            }
        });

        /*Setting up the array list and adding the xml into the array list to be displayed*/
        ArrayList<String> AL2 = new ArrayList<>();
        RetrieveFeed2 RF2 = new RetrieveFeed2();
        RF2.execute();
        AL2 = RF2.heads();


        /* Binding data*/
       final ArrayAdapter Planned = new ArrayAdapter(this, android.R.layout.simple_list_item_1, AL2);
        setListAdapter(Planned);
        listView.setAdapter(Planned);

        /*takes the users input to search in the rss feed*/
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                text.replace(" ","");
                Planned.getFilter().filter(text);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                text.replace(" ","");
                Planned.getFilter().filter(text);
                return false;
            }
        });
    }

    /*Functionality for the radio buttons and chooses what to display when each button selected*/
    private View.OnClickListener radio_listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio0:
                    VF.setDisplayedChild(0);

                    break;

                case R.id.radio2:
                    VF.setDisplayedChild(2);//changed

                    break;
            }
        }
    };
}

