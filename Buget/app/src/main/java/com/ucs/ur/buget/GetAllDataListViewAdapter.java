package com.ucs.ur.buget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ur on 2016.03.05..
 */
public class GetAllDataListViewAdapter extends BaseAdapter
{

    private JSONArray dataArray;
    private Activity activity;

    private static LayoutInflater inflater =null;

    public GetAllDataListViewAdapter(JSONArray dataArray , Activity activity)
    {
        this.dataArray=dataArray;
        this.activity=activity;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount()
    {
        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ListCell cell;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_view,null);
            cell = new ListCell();

            cell.name = (TextView) convertView.findViewById(R.id.name);
            cell.money = (TextView) convertView.findViewById(R.id.money);
            cell.curMoney = (TextView) convertView.findViewById(R.id.curMoney);
            cell.date = (TextView) convertView.findViewById(R.id.date);
            cell.imageView = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(cell);
        }
        else
        {
            cell = (ListCell) convertView.getTag();
        }

        try
        {
            JSONObject jsonObject =this.dataArray.getJSONObject(position);

            cell.name.setText("Name: "+jsonObject.getString("name"));
            cell.money.setText("Money: "+jsonObject.getString("money"));
            cell.curMoney.setText("Curent Money: "+jsonObject.getString("curMoney"));
            cell.date.setText("Date: "+jsonObject.getString("date"));

            String icon = jsonObject.getString("trans");

            if(icon.equals("1"))
            {
                cell.imageView.setImageResource(R.drawable.in);
            }
            else if(icon.equals("2"))
            {
                cell.imageView.setImageResource(R.drawable.out);
            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ListCell
    {
        private TextView name;
        private TextView money;
        private TextView curMoney;
        private TextView date;
        private ImageView imageView;
    }
}
