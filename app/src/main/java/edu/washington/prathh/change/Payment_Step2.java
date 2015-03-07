package edu.washington.prathh.change;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Payment_Step2 extends ActionBarActivity {
    public static final String URL_TEST = "https://sandbox-api.venmo.com/v1/payments";
    public static final String URL = "https://api.venmo.com/v1/payments";
    private String amount;
    private String recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__step2);

        TextView spentAmount = (TextView) findViewById(R.id.amount_spent);
        TextView donateAmount = (TextView) findViewById(R.id.amount_donated);
        spentAmount.setText("$" + getIntent().getExtras().getString("amount"));
        double dollarAmount = Double.parseDouble(getIntent().getExtras().getString("amount"));
        int dollarUp = (int) dollarAmount + 1;
        final double change = round(Math.abs(dollarUp - dollarAmount));
        donateAmount.setText("+" + change + " for HTC");

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(change);
            }
        });
    }

    public double round(double d) {
        return Math.round(d * 100) / 100.0;
    }

    public void submit(double change) {
        Payment_Step2.this.amount = getIntent().getExtras().getString("amount");
        Payment_Step2.this.recipient = ((EditText)findViewById(R.id.phone_number)).getText().toString();
        Log.i("Payment_Step2", "Recip: " + this.recipient + " " + this.amount + ", Donate: 7814922986 " + change);
        new PaymentRequestTask().execute(URL_TEST, this.amount, "15555555555");
        new PaymentRequestTask().execute(URL, "" + change, this.recipient);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment__step2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class PaymentRequestTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... uri) {
            if (uri[0] != null) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    Log.i("Payment", "Attempting to request: " + uri[0]);
                    HttpPost httpPost = new HttpPost(uri[0]);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("access_token", ((ChangeApp)getApplication()).getAccessToken()));
                    nameValuePairs.add(new BasicNameValuePair("phone", uri[2]));
                    nameValuePairs.add(new BasicNameValuePair("note", "Here's some Change for you!"));
                    if (uri[2].equals("15555555555")) {
                        nameValuePairs.add(new BasicNameValuePair("amount", "0.1"));
                    } else {
                        nameValuePairs.add(new BasicNameValuePair("amount", uri[1]));
                    }
                    Log.i("Payment", "nameValuePairs: " + nameValuePairs.toString());

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONObject json = new JSONObject(jsonString);
                        return json;
                    }
                } catch (Exception e) {
                    Log.w("Payment", "Something went wrong while getting url response: " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.i("Payment_Step2", result.toString());
            }
        }
    }
}
