package labstuff.gcu.me.org.mpdcwork;

/**
  Created by Ross Hendry  on 21/03/2018.
 **/

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Home on 21/03/2018.
 */

import android.app.Activity;
import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RetrieveFeed extends AsyncTask {
    // Initializing instance variables
    URL url;
    ArrayList<String> headlines = new ArrayList();
    ArrayList<String> links = new ArrayList();
    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            url = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");//changed to planned!!!

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(getInputStream(url), "UTF_8");

        /* The parser looks for the various items in the feed such as title, description and georss point.
         */
            boolean insideItem = false;

            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem)
                            headlines.add(xpp.nextText()); //extract the headline
                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem)
                            headlines.add(xpp.nextText());
                    }else if (xpp.getName().equalsIgnoreCase("georss:point")) {
                        headlines.add(xpp.nextText());//extracts the georss point
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }

                eventType = xpp.next(); //move to next element
            }

/*            error handling*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        removes any xml tags from the feed*/
        Collections.replaceAll(headlines,"<br />","a ");
        System.out.println(headlines.toString());
        return headlines;

    }

/*    opens up the input stream to receive the xml data*/
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

/*    returns the arraylist Strings containing parsed xml data*/
    public ArrayList<String> heads()
    {
        Collections.replaceAll(headlines,"a","a");
        return headlines;
    }

}