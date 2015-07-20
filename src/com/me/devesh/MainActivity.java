package com.me.devesh;

import android.os.AsyncTask;
import android.text.style.ClickableSpan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

	ImageButton ib1;
    Button b1;
	EditText et1;
	ListView lv;
    Adap adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ib1=(ImageButton)findViewById(R.id.imageButton1);
        b1=(Button)findViewById(R.id.bButton1);
        lv=(ListView)findViewById(R.id.listViewmain);
        et1=(EditText)findViewById(R.id.editText1);
        ib1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*Intent i=new Intent(MainActivity.this,First.class);
			startActivity(i);*/
                String search=et1.getText().toString().replaceAll(" ","+");
                new RequestTask()
                        .execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="+search);
			}
			
			
    });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i=new Intent(MainActivity.this,Simplebrowser.class);
                i.putExtra("url",newsDet[position]);
                startActivity(i);

            }
        });


        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,First.class);
			startActivity(i);
            }
        });

 }
    int n = 25;
    int img[];
    String read = "Todays Breaking news are.";
    //String[] newsTitlea;// = new String[n];
    String[] newsPhoto;// = new String[n];
    String[] newsReleasedvd = new String[n];
    String[] newsPhotoCap;// = new String[n];
    String[] newsThumb;// = new String[n];
    //String[] newsSyn;// = new String[n];
    String[] newsPos = new String[n];
    String[] newsPub = new String[n];
    String[] newsPubd = new String[n];
    String[] newsDet = new String[n];
    String[] newsRuna = new String[n];


    private void refreshnewssList(String[] newsTitles, String[] imgs) {

		/*
		 * NewsList.setAdapter(new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, newsTitles));
		 */
        adapter = new Adap(this, imgs, newsTitles, newsPub, newsPubd);
        lv.setAdapter(adapter);
    }

    private class RequestTask extends AsyncTask<String, String, String> {
        // make a request to the specified url
        @Override
        protected String doInBackground(String... uri) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                // make a HTTP request
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                    // request successful - read the response and close the
                    // connection
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else {
                    // request failed - close the connection
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (Exception e) {
                Log.d("Test", "Couldn't make a successful request!");
            }
            return responseString;
        }

        int n;

        // if the request above completed successfully, this method will
        // automatically run so you can do something with the response
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (response != null) {
                try {

                    // convert the String response to a JSON object,
                    // because JSON is the response format Rotten Tomatoes uses
                    JSONObject jsonResponse = new JSONObject(response);

                    // fetch the array of newss in the response

                    JSONObject newss = jsonResponse.getJSONObject("responseData");
                    JSONArray newssrun = newss.getJSONArray("results");
                    // add each news's title to an array

                    String[] newsCon = new String[newssrun.length()];
                    String[] newsTitles = new String[newssrun.length()];
                    String[] newsPub = new String[newssrun.length()];
                    String[] newsPubd = new String[newssrun.length()];
                    //newsTitlea = new String[newssrun.length()];
                    newsPhoto = new String[newssrun.length()];

                    newsPhotoCap = new String[newssrun.length()];
                    newsThumb = new String[newssrun.length()];
                    //newsSyn = new String[newssrun.length()];
                    n = newssrun.length();
                    for (int i = 0; i < newssrun.length(); i++) {
                        // JSONObject newsr = newssrun.getJSONObject(i);
                        JSONObject news = newssrun.getJSONObject(i);
                        if (news.has("content")) {
                            newsCon[i] = news.getString("content");
                            //newsTitlea[i] = news.getString("content");
                        } else
                            newsCon[i] = "";
                        if (news.has("title"))
                            newsTitles[i] = news.getString("title");
                        else
                            newsTitles[i] = "";
                        if (news.has("unescapedUrl")) {
                            newsDet[i] = news.getString("unescapedUrl");

                        } else
                            newsDet[i] = "";
                        if (news.has("publisher")) {
                            newsPub[i] = news.getString("publisher");

                        } else
                            newsPub[i] = "";
                        if (news.has("publishedDate")) {
                            newsPubd[i] = news.getString("publishedDate");

                        } else
                            newsPubd[i] = "";
                        // newsTitles[i] = news.getString("HeadLine");
                        // newsYear[i] = news.getString("DateLine");
                        // newsTitlea[i] = news.getString("HeadLine");
                        // searchBox.setText(newsTitles[i]);
                        // newsYeara[i] = news.getString("DateLine");
                        // newsSyn[i] = news.getString("Story");
                        // newsRun[i] = news.getString("ByLine");
                        try {
                            JSONObject ab = news.getJSONObject("image");

                            if (ab.has("url"))

                                newsPhotoCap[i] = ab.getString("url");
                            else
                                newsPhotoCap[i] = "http://ts3.mm.bing.net/th?id=JN.Wi%2fX2BbRKTeqhFAq1bK1Fg&w=139&h=139&c=7&rs=1&qlt=90&o=4&pid=1.1";
                        } catch (JSONException e) {
                            newsPhotoCap[i] = "http://ts3.mm.bing.net/th?id=JN.Wi%2fX2BbRKTeqhFAq1bK1Fg&w=139&h=139&c=7&rs=1&qlt=90&o=4&pid=1.1";
                        }
                        try {
                            JSONObject ab = news.getJSONObject("Image");

                            if (ab.has("originalContextUrl"))
                                newsPhoto[i] = ab.getString("originalContextUrl");

                            else
                                newsPhoto[i] = "http://ts3.mm.bing.net/th?id=JN.Wi%2fX2BbRKTeqhFAq1bK1Fg&w=139&h=139&c=7&rs=1&qlt=90&o=4&pid=1.1";
                        } catch (JSONException e) {
                            newsPhoto[i] = "http://ts3.mm.bing.net/th?id=JN.Wi%2fX2BbRKTeqhFAq1bK1Fg&w=139&h=139&c=7&rs=1&qlt=90&o=4&pid=1.1";
                        }
                        try {
                            JSONObject ab = news.getJSONObject("Image");

                            if (ab.has("publisher")) {
                                newsThumb[i] = ab.getString("publisher");

                            } else
                                newsThumb[i] = "";
                        } catch (JSONException e) {
                            newsThumb[i] = "";
                        }


						/*
						 * JSONObject newsR = news.getJSONObject("posters");
						 * newsPos[i]=newsR.getString("original");
						 */
                        read = read + "." +" "+ (i + 1) + "." + newsTitles[i] + ".";

                        newsPub[i] = (newsTitles[i]);
                    }

                    // update the UI
                    refreshnewssList(newsPub, newsPhotoCap);

                } catch (JSONException e) {
                    Log.d("Test",
                            "Failed to parse the JSON response!" + e.toString());
                }
            }
        }
    }
}