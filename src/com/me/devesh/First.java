package com.me.devesh;

import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;

public class First extends Activity implements OnClickListener {

	private static final int news_PAGE_LIMIT = 20;
	TextToSpeech tts;
	Button tech, cricket, business, world, india, sports, tv, events, edu, env,
			sci, nri, beauty, breaking, local,donebutton;
	Adap adapter;
	private ListView NewsList;
    String a="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);

		new RequestTask()
				.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=top+news&h1=en&start=0");

		NewsList = (ListView) findViewById(R.id.listView2);
		/*
		 * jitAdapter adapter=new jitAdapter(this, newsTitlea);
		 * NewsList.setAdapter(adapter);
		 */
		tech = (Button) findViewById(R.id.button1);
		breaking = (Button) findViewById(R.id.button2);
		local = (Button) findViewById(R.id.button3);
		cricket = (Button) findViewById(R.id.button4);
		business = (Button) findViewById(R.id.button5);
		india = (Button) findViewById(R.id.button6);
		world = (Button) findViewById(R.id.button7);
		env = (Button) findViewById(R.id.button8);
		edu = (Button) findViewById(R.id.button9);
		sports = (Button) findViewById(R.id.button10);
		sci = (Button) findViewById(R.id.button11);
		beauty = (Button) findViewById(R.id.button12);
		nri = (Button) findViewById(R.id.button13);
		tv = (Button) findViewById(R.id.button14);
		events = (Button) findViewById(R.id.button15);
        donebutton= (Button) findViewById(R.id.button16);
		tech.setOnClickListener(this);
		env.setOnClickListener(this);
		edu.setOnClickListener(this);
		breaking.setOnClickListener(this);
		local.setOnClickListener(this);
		beauty.setOnClickListener(this);
		nri.setOnClickListener(this);
		sci.setOnClickListener(this);
		tv.setOnClickListener(this);
		sports.setOnClickListener(this);
		events.setOnClickListener(this);
		cricket.setOnClickListener(this);
		business.setOnClickListener(this);
		india.setOnClickListener(this);
		world.setOnClickListener(this);
        donebutton.setOnClickListener(this);
        donebutton.setVisibility(View.GONE);



		NewsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                Intent i=new Intent(First.this,Simplebrowser.class);
                i.putExtra("url",newsDet[position]);
                startActivity(i);
				
			}
		});
		tts = new TextToSpeech(First.this,new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {
						// TODO Auto-generated method stub
						if (status != TextToSpeech.ERROR) {
							tts.setLanguage(Locale.US);
						}
					}
				});

	}
	
	
	
	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu  
	        return true;  
	    }  


	
	
	int i;
	String translatedText, tt2, tt3;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.speak1) {
			tts.speak( read + "Thank you",TextToSpeech.QUEUE_FLUSH, null);
			//Toast.makeText(getApplicationContext(), read, Toast.LENGTH_SHORT).show();
		}
		if (id == R.id.Location1) {
			startActivity(new Intent(this, SettingActivitymain.class));
			/*
			 * SharedPreferences sharedPrefs =
			 * PreferenceManager.getDefaultSharedPreferences(this); String
			 * location = sharedPrefs.getString(
			 * getString(R.string.pref_location_key),
			 * getString(R.string.pref_location_default));
			 */
			// Toast.makeText(getApplicationContext(), location,
			// Toast.LENGTH_SHORT).show();
		}    
     

		return super.onOptionsItemSelected(item);
	}   

	
	
	

	@Override
	public void onDestroy() {
		NewsList.setAdapter(null);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (tts != null) {
			tts.stop();
			tts.shutdown();

		}
		super.onPause();
	}
	
	

	int n = 25;
	int img[];
	String read = "Todays Breaking news are.";
	String[] newsTitles;// = new String[n];
	String[] newsPhoto;// = new String[n];
	String[] newsReleasedvd = new String[n];
	String[] newsPhotoCap;// = new String[n];
	String[] newsThumb;// = new String[n];
	//String[] newsSyn;// = new String[n];
	String[] newsPos = new String[n];
	String[] newsDet = new String[n];
	String[] newsPub;
	String[] newsPubd;
	String[] newsRuna = new String[n];
	

	
    
	
	
	

	 private void refreshnewssList(String[] newsTitles, String[] imgs,String[] a,String[] b) {

			/*
			 * NewsList.setAdapter(new ArrayAdapter<String>(this,
			 * android.R.layout.simple_list_item_1, newsTitles));
			 */
			adapter = new Adap(this, imgs, newsTitles,a, b);
			NewsList.setAdapter(adapter);
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
						newsTitles = new String[newssrun.length()];
						 newsPub = new String[newssrun.length()];
						 newsPubd = new String[newssrun.length()];
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
							a=a+newsTitles[i];
							;
						}

						// update the UI
						refreshnewssList(newsTitles, newsPhotoCap,newsPub,newsPubd);

					} catch (JSONException e) {
						Log.d("Test",
								"Failed to parse the JSON response!" + e.toString());
					}
				}
			}
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button1:
				tts.stop();
				setTitle("India News");
				read="Todays National news are.";
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=india&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button2:
				tts.stop();
				setTitle("World News");
				read="Todays World news are.";
				
				
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=world&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button3:
				setTitle("Politics News");
				read="Todays Politics News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=politics&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button4:
				setTitle("Entertainment");
				read="Todays Entertainment News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=entertainment&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button5:
				setTitle("BUZZ");
				read="Todays Buzz News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=buzz&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button6:
				setTitle("Education News");
				read="Todays Education News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=education&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button7:
				setTitle("Astro News");
				read="Todays Astro news are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=astro&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button8:
				setTitle("Kolkata");
				read="Todays Kolkata news are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=kolkata&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button9:
				setTitle("Bollywood News");
				read="Todays Bollywood News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=bollywood&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button10:
				setTitle("Sports News");
				read="Todays sports news are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=sports&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button11:
				setTitle("Crime News");
				read=" todays Crime News are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=crime&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button12:
				setTitle("TV News");
				read="todays tv news are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=tvnews&start=2");
				 donebutton.setVisibility(View.GONE);
				break;
			case R.id.button13:
				setTitle("Weather");
				read="Todays Weather news are.";
				tts.stop();
				new RequestTask()
						.execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=weather&start=2");
				 donebutton.setVisibility(View.GONE);
				break;

	            case R.id.button14:
	                setTitle("Breaking News");
	                read="Todays Breaking news are.";
	                tts.stop();
	                new RequestTask()
	                        .execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=breakingnews&start=2");
	                donebutton.setVisibility(View.GONE);
	                break;
	            case R.id.button15:
	                setTitle("Cricket");
	                donebutton.setVisibility(View.VISIBLE);
	                read="Todays Cricket news are.";
	                tts.stop();
	                new RequestTask()
	                        .execute("http://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=cricket&start=2");
	                break;
	            case R.id.button16:

	                Intent i=new Intent(First.this,Simplebrowser.class);
	                i.putExtra("url","http://m.cricbuzz.com/cricket-match/live-scores");
	                startActivity(i);
	                break;
		/*	case R.id.button15:
				read="";
				tts.stop();
				SharedPreferences sharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(this);
				String location = sharedPrefs.getString(
						getString(R.string.pref_location_key),
						getString(R.string.pref_location_default));
				if (location == null || location == "" || location==" ") {
					Toast.makeText(getApplicationContext(),
							"Enter the Nearest CIty", Toast.LENGTH_SHORT).show();
				} else {
					Intent browserintent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("http://timesofindia.indiatimes.com/topic/"
									+ location));
					startActivity(browserintent);
					break;
				}*/
			}
		}

	}
