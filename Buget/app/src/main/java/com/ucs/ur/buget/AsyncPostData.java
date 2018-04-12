package com.ucs.ur.buget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ur on 2016.03.02..
 */
public class AsyncPostData extends AsyncTask<String , Void , String>
{
    private Context context=null;
    private String retVal="";
    private String name;
    private String money;
    private String transaction;
    private ProgressDialog progressDialog=null;

    public AsyncPostData(Context context , String name , String money , String transaction)
    {
        this.context=context;
        this.name=name;
        this.money=money;
        this.transaction=transaction;
    }

    @Override
    protected void onPreExecute()
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Pleas wait...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {
        HttpClient hc = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(params[0]);
        InputStream is = null;

        List<NameValuePair> nameValuePair = new ArrayList<>();
        nameValuePair.add(new BasicNameValuePair("name",name));
        nameValuePair.add(new BasicNameValuePair("money",money));
        nameValuePair.add(new BasicNameValuePair("tran",transaction));

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            HttpResponse res = hc.execute(httpPost);

            if(res.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
                HttpEntity entity = res.getEntity();

                if(entity!=null)
                {
                    is=entity.getContent();

                    StringBuilder sb = new StringBuilder();

                    int chIn;

                    while ((chIn=is.read())!=-1)
                    {
                        sb.append((char)chIn);
                    }
                    retVal = sb.toString();
                }
            }

        } catch (UnsupportedEncodingException e)
        {
            retVal="Error: "+e.getMessage();
        } catch (ClientProtocolException e) {
            retVal="Error: "+e.getMessage();
        } catch (IOException e)
        {
            retVal="Error: "+e.getMessage();
        }
        finally
        {
            if(is!=null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return retVal;
    }

    @Override
    protected void onPostExecute(String s)
    {
        progressDialog.dismiss();
        Toast.makeText(context,retVal,Toast.LENGTH_LONG).show();
    }
}
