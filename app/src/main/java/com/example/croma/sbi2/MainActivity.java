package com.example.croma.sbi2;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;





import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;

        import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;


import static com.example.croma.sbi2.R.id.account;
import static java.lang.System.in;



public class MainActivity extends AppCompatActivity {

    EditText acc_no;
    TextView responseView;
    ProgressBar progressBar;
    String acc;
    static final String API_KEY = "NPCITeam010";
    static final String API_URL1 = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseView = (TextView) findViewById(R.id.responseView);
        acc_no = (EditText) findViewById(account);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Button queryButton = (Button) findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveFeedTask().execute();
            }
        });
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");

            acc = acc_no.getText().toString();
        }

        protected String doInBackground(Void... args) {

            String json = null;

            try {
                HttpResponse response;
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("AccountNumber", acc);

                json = jsonObject.toString();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(API_URL1);
                httpPost.setEntity(new StringEntity(json, "UTF-8"));
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setHeader("apikey", API_KEY);


                httpPost.setHeader("Accept-Encoding", "application/json");

                response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                String sresponse = entity.toString();
                String a = EntityUtils.toString(entity);
                Log.w("QueingSystem", sresponse);
                Log.w("QueingSystem", a );
                if(entity!=null){
                    String responsebody = a ;
                    return responsebody;

                }


                 return sresponse;
              //  responseView.setText(sresponse);
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    bufferedReader.close();
//                    return stringBuilder.toString();
//                }
//                finally{
//                   // urlConnection.disconnect();
//                }


              //  Log.w("QueingSystem", sresponse);
             //   Log.w("QueingSystem", EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
               return null;
            } finally {


            }

        // return null;
        }

        protected void onPostExecute(String result) {
            String a ="hello ";
            if(result == null) {
               a += " THERE WAS AN ERROR";
                responseView.setText(a);
            }
            else {
                progressBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                Customer customer_details = gson.fromJson(result,Customer.class);
                 String b = customer_details.getReferenceNumber();
                responseView.setText(b);
            }
          //  Log.i("INFO", result);
          //  responseView.setText(result);
            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }


}