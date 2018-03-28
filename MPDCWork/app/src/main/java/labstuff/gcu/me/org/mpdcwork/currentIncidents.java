package labstuff.gcu.me.org.mpdcwork;

/**
 * Created by Ross Hendry S1227760 on 17/03/2018.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import labstuff.gcu.me.org.mobileplatcw.R;

public class currentIncidents extends ListActivity {

    /*initialises the variables*/
    RadioButton RB1;
    RadioButton RB2;
    Button Home;
    ViewFlipper VF;
    ListView listView;
    SearchView searchBar;//Search Bar for Current Incidents


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incidents_main);
        Log.e(getPackageName(), "just before avw");

        /*link the variable to the component in the xml file*/
        Home = (Button) findViewById(R.id.home);
        RB1 = (RadioButton) findViewById(R.id.radio1);
        RB2 = (RadioButton) findViewById(R.id.radio2);
        VF = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        listView = (ListView) findViewById(R.id.listV);
        searchBar = (SearchView) findViewById(R.id.searchView);//Search View

        /*initialise the variable to the onclicklistener*/
        RB1.setOnClickListener(radio_listener);
        RB2.setOnClickListener(radio_listener);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(currentIncidents.this,MainActivity.class);
                startActivity(in);
            }
        });

        /*populate the array with parsed xml data*/
        ArrayList<String> headlines = new ArrayList<>();
        RetrieveFeed getXML = new RetrieveFeed();
        getXML.execute();
        headlines = getXML.heads();

        // Binding data
        final ArrayAdapter Current = new ArrayAdapter(this, android.R.layout.simple_list_item_1, headlines);

        /*applies the parsed xml data to the listview*/
        setListAdapter(Current);
        listView.setAdapter(Current);

/*        enables the user's input to be searched within the rss feed*/
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                text.replace(" ","");
                Current.getFilter().filter(text);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                text.replace(" ","");
                Current.getFilter().filter(text);
                return false;
            }
        });
    }

    /*states what will be displayed depending on which radio button the user chooses*/
    private View.OnClickListener radio_listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
               /* case R.id.radio0:
                    VF.setDisplayedChild(0);

                    break;*/
                case R.id.radio1:
                    VF.setDisplayedChild(1);


                    break;
                case R.id.radio2:
                    VF.setDisplayedChild(2);//changed

                    break;
            }
        }
    };
}


