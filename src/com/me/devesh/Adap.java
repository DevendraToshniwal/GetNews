package com.me.devesh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.devesh.ImageLoader;

public class Adap extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private String[] img;
    private String[] pub;
    private String[] pubd;
    private static LayoutInflater inflater=null;
    
    public ImageLoader imageLoader; 
    
    public Adap(Activity a, String[] d,String[] i,String[] b,String[] c) {
        activity = a;
        img=d;
        pub=b;
        pubd=c;
        data=i;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.second, null);
        TextView text1=(TextView)vi.findViewById(R.id.text1);
        TextView text2=(TextView)vi.findViewById(R.id.text2);
        TextView text=(TextView)vi.findViewById(R.id.text);
        text1.setText("PUBLISHER-"+pub[position]);
        text2.setText("PUBLISHEDDATE-"+pubd[position]);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        text.setText(data[position].replaceAll("\\<[^>]*>",
                "").toString());
        imageLoader.DisplayImage(img[position], image);
        return vi;
    }
}
