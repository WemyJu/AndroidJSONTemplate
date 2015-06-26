package com.example.jsonconnecttemplate;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

/**
 * Created by mac on 15/6/27.
 */
public class Web_Service {
    ///////////////////////////////////////////
    // debug
    ///////////////////////////////////////////

    public static final String TAG = Web_Service.class.getSimpleName();

    public static final boolean D = true;
    public static final boolean D_API_CONTENT_STATE       = D && true;
    public static final boolean D_API_CONTENT_TIME       = D && false;
    public static final boolean D_API_CONTENT            = D && false;
    public static final boolean D_API_ACTIVITY_FUTURE    = D && true;
    public static final boolean D_API_SIGNACTIVITY_QUERY = D && false;
    public static final boolean D_API_SIGNACTIVITY       = D && false;
    public static final boolean D_API_EUSER_NEW          = D && false;
    public static final boolean D_API_EUSER_LOGIN        = D && false;
    public static final boolean D_API_EUSER_DATA         = D && false;
    public static final boolean D_API_EUSER_UPDATE       = D && false;
    public static final boolean D_API_ACTIVITYGAME_PRIZE = D && false;
    public static final boolean D_API_ACTIVITYGAME_GAMESTATE  = D && false;
    public static final boolean D_API_ACTIVITY_GAME_SET_ANS   = D && false;
    public static final boolean D_API_ACTIVITYGAME_AWARDPRIZE = D && false;

    private final RequestQueue mQueue;

    public Web_Service(RequestQueue queue) {
        mQueue = queue;
    }
    public Request<?> api_getMain(String parm1, String parm2, String parm3, Response.Listener<JSONObject> listener, ErrorListener errorListener) {

        String uri = String.format(Constants.API_SOMETHING);

        if(D_API_ACTIVITY_FUTURE) {
            Log.w(TAG, "api_some_thing uri = " + uri);
        }

        ArrayList<BasicNameValuePair> params = new ArrayList();

        PostParameterJsonObjectRequest jsonObjectRequest = new PostParameterJsonObjectRequest(Method.POST, uri , params, listener, errorListener) ;
        params.add(new BasicNameValuePair("parm1", parm1));
        params.add(new BasicNameValuePair("parm2", parm2));
        params.add(new BasicNameValuePair("parm3", parm3));
        return mQueue.add(jsonObjectRequest);
    }
}
