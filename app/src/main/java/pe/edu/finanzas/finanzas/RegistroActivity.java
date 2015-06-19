package pe.edu.finanzas.finanzas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class RegistroActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
