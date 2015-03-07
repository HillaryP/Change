package edu.washington.prathh.change;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Authorize extends Activity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);

    }

    public void loadImage (View view) {
        if (((ChangeApp)getApplication()).getAccessToken() == null) {
            ImageView auth = (ImageView) findViewById(R.id.authPayments);
                Intent authIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.venmo.com/v1/oauth/authorize?client_id=2422&scope=make_payments%20access_profile"));
                startActivity(authIntent);
        } else {
            Intent payment = new Intent(this, Payment.class);
            startActivity(payment);
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

    @Override
    public void onResume() {
        super.onResume();
        if (((ChangeApp)getApplication()).getAccessToken() != null) {
            Intent payment = new Intent(this, Payment.class);
            startActivity(payment);
        }
    }
}
