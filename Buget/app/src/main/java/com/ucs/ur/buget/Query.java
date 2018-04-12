package com.ucs.ur.buget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Query extends AppCompatActivity
{

    public static ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        listView = (ListView) findViewById(R.id.List_text);

        AsyncQueryDatas asyncQueryDatas = new AsyncQueryDatas(this);
        asyncQueryDatas.execute("http://bugetapp.azurewebsites.net/API/apiRead.php");
    }

    public void Refresh(View v)
    {
        AsyncQueryDatas asyncQueryDatas = new AsyncQueryDatas(this);
        asyncQueryDatas.execute("http://bugetapp.azurewebsites.net/API/apiRead.php");
    }

    public  void setListAdapter(JSONArray jsonArray)
    {
        listView.setAdapter(new GetAllDataListViewAdapter(jsonArray,this));
    }


    public class AsyncQueryDatas extends AsyncTask<String , Void , JSONArray>
    {
        private Context context = null;
        private ProgressDialog progressDialog = null;
        private JSONArray retVal=null;
        private String Error="";

        public AsyncQueryDatas(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Pleas wait...");
            progressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params)
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);
            InputStream is = null;

            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("req","1"));

            try
            {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);

                if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
                {
                    HttpEntity entity = response.getEntity();

                    if(entity!=null)
                    {
                        is=entity.getContent();

                        int chIn;
                        StringBuilder sb = new StringBuilder();
                        while((chIn=is.read())!=-1)
                        {
                            sb.append((char) chIn);
                        }
                        retVal = new JSONArray(sb.toString());
                    }
                }


            } catch (UnsupportedEncodingException e)
            {
                Error="Error: "+e.getMessage();
            } catch (ClientProtocolException e)
            {
                Error="Error: "+e.getMessage();
            } catch (IOException e)
            {
                Error="Error: "+e.getMessage();
            } catch (JSONException e)
            {
                Error="Error: "+e.getMessage();
            }
            return retVal;
        }

        @Override
        protected void onPostExecute(JSONArray retVal)
        {
            progressDialog.dismiss();
            if(Error!="")
            {
                Toast.makeText(context, Error, Toast.LENGTH_LONG).show();
            }
            else
            {
                setListAdapter(retVal);
            }

        }
    }
}
