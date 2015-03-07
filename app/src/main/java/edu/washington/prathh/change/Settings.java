package edu.washington.prathh.change;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void showCheckmark (View view1) {
        uncheckAll();
        switch (((TextView) view1).getText().toString()) {
            case "Human Rights Campaign \nwww.hrc.org":
                ImageView image = (ImageView) findViewById(R.id.hrc_check);
                image.setVisibility(View.VISIBLE);
                break;
            case "The Trevor Project \nwww.hrc.org":
                ImageView image2 = (ImageView) findViewById(R.id.trevor_check);
                image2.setVisibility(View.VISIBLE);
                break;
            case "GLAAD \nwww.glaad.org":
                ImageView image3 = (ImageView) findViewById(R.id.glaad_check);
                image3.setVisibility(View.VISIBLE);
                break;
            case "Campus Pride \nwww.campuspride.org":
                ImageView image4 = (ImageView) findViewById(R.id.campus_check);
                image4.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void uncheckAll() {
        ImageView image = (ImageView) findViewById(R.id.hrc_check);
        image.setVisibility(View.INVISIBLE);
        ImageView image2 = (ImageView) findViewById(R.id.trevor_check);
        image2.setVisibility(View.INVISIBLE);
        ImageView image3 = (ImageView) findViewById(R.id.glaad_check);
        image3.setVisibility(View.INVISIBLE);
        ImageView image4 = (ImageView) findViewById(R.id.campus_check);
        image4.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
}
