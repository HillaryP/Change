package edu.washington.prathh.change;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Authorize extends ActionBarActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        if (((ChangeApp)getApplication()).getAccessToken() == null) {
            Button auth = (Button) findViewById(R.id.authPayments);
            auth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent authIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.venmo.com/v1/oauth/authorize?client_id=2422&scope=make_payments%20access_profile"));
                    startActivity(authIntent);
                }
            });
        } else {
            Intent payment = new Intent(this, Payment.class);
            startActivity(payment);
        }
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        if (((ChangeApp)getApplication()).getAccessToken() != null) {
            Intent payment = new Intent(this, Payment.class);
            startActivity(payment);
        }
    }
}
