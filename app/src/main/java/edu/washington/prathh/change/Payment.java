package edu.washington.prathh.change;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;


public class Payment extends ActionBarActivity {
    public final String ME_URL = "https://api.venmo.com/v1/me?access_token=";
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        addListenerOnButton();
        editText = (EditText) findViewById(R.id.editText);
        if (((ChangeApp)getApplication()).getAccessToken() == null) {
            Uri data = getIntent().getData();
            String token = data.getQueryParameter("access_token");
            ((ChangeApp) getApplication()).setAccessToken(token);
            new InfoRequestTask().execute(ME_URL + token);
        } else {
            String token = ((ChangeApp)getApplication()).getAccessToken();
            new InfoRequestTask().execute(ME_URL + token);
        }
    }

    public void addListenerOnButton() {
        final Context context = this;
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, Payment_Step2.class);
                intent.putExtra("amount", editText.getText().toString().substring(1));
                startActivity(intent);
            }
        });

    }

    public void addNum(View v) {
        String existing = editText.getText().toString();
        switch (v.getId()) {
            case R.id.delete:
                editText.setText(existing.subSequence(0, existing.length() - 1));
                break;
            default:
                Button current = (Button) v;
                if (!existing.matches("$(\\d)(\\.(\\d){2})?")) {
                    editText.setText(existing + current.getText());
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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

    private class InfoRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... uri) {
            if (uri[0] != null) {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    Log.i("Payment", "Attempting to request: " + uri[0]);
                    HttpResponse response = httpClient.execute(new HttpGet(uri[0]));
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        String jsonString = EntityUtils.toString(response.getEntity());
                        JSONObject json = new JSONObject(jsonString);
                        return "Welcome to Change, " + json.getJSONObject("data").getJSONObject("user").getString("first_name");
                    }
                } catch (Exception e) {
                    Log.w("Payment", "Something went wrong while getting url response: " + e);
                }
            }
            return "Something went wrong with your request";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(Payment.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
