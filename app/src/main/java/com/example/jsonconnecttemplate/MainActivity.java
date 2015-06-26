package com.example.jsonconnecttemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    /*********************************************************************************/
    // debug
    public static final String TAG = "MainActivity";

    public static final boolean D = true;

    public float startTime;

    /*********************************************************************************/
    // global tools
    SharedPreferences mSharedPreferences;

    /*********************************************************************************/
    // parse data
    ArrayList<String> mGet1, mGet2, mGet3;
    ArrayList<HashMap<String, String>> list;
    String parm1, parm2, parm3;

    /*********************************************************************************/
    // UI
    ProgressDialog alertProgress;
    AlertDialog.Builder searchMsg;
    ListView listview;
    Button search_btn;
    EditText year_ed, month_ed;
    /*********************************************************************************/
    // Lifecycle
    /*********************************************************************************/
    // Static
    //LeaveStatAdapter mAdapter;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = this.getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        parm1 = mSharedPreferences.getString("parm1", "");
        parm2 = mSharedPreferences.getString("parm2", "");
        parm2 = mSharedPreferences.getString("parm2", "");

        //AlearProgress
        alertProgress = new ProgressDialog(MainActivity.this);
        alertProgress.setMessage("抓取資料中~請稍後......");
        alertProgress.setCancelable(false);
        listview = (ListView) MainActivity.this.findViewById(R.id.listView);
        list = new ArrayList<HashMap<String, String>>();
        mGet1 = new ArrayList<String>();
        mGet2 = new ArrayList<String>();
        mGet3 = new ArrayList<String>();

        search_btn = (Button) this.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getMyMain();
            }
        });

        adapter = new SimpleAdapter(this, list, R.layout.main_list, new String[]{"mParm1", "mParm2", "mParm3"}, new int[]{R.id.textView, R.id.textView2, R.id.textView3});
        listview.setAdapter(adapter);
        getMyMain();
        //listview.setTextFilterEnabled(true);
        //initUI();
        alertProgress.dismiss();
    }

    void initUI(){

    }


    void getMyMain(){
        Log.w(TAG, "getMain ");
        alertProgress.show();
        Appaction.get().getApi().api_getMain(parm1, parm2, parm3, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                if(D){
                    Log.w(TAG,"response = "+response);
                }

                alertProgress.dismiss();
                JSONArray jsonArray;
                try {
                    String get1, get2, get3;
                    jsonArray = response.getJSONArray(" ");
                    //Log.w("length", jsonArray.length()+"");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        parm1 = jsonObject.getString(" ");

                        JSONArray arr = jsonObject.getJSONArray(" ");
                        for(int j=0; j<arr.length(); j++){
                            JSONObject obj = (JSONObject) arr.get(j);
                            get1 = obj.getString("vtype");
                            get2 = obj.getString("duration");
                            get3 = obj.getString("agent");

                            mGet1.add(get1);
                            mGet2.add(get2);
                            mGet3.add(get3);

                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("get1", get1);
                            item.put("get2", get2);
                            item.put("get3", get3);
                            list.add(item);
                        }
                        //Log.w("ddata: ", list.get(i).get("ddata"));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//Array名稱
                adapter.notifyDataSetChanged();
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);	//顯示於主程式main
                builder.setTitle("網路連線出現問題，請連絡客服人員");	//加入標題
                builder.setMessage(error.getMessage());	//加入說明
                builder.setPositiveButton("我知道了",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//						MainActivity.this.finish();
                            }
                        }
                );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        builder.show();
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
