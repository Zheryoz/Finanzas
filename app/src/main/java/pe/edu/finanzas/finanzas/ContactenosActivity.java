package pe.edu.finanzas.finanzas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class ContactenosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactenos);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }

        findViewById(R.id.tv_telefono).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + getString(R.string.telefono_numero);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });

        findViewById(R.id.tv_correo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + getString(R.string.email_contacto)));
                startActivity(Intent.createChooser(emailIntent, "Seleccione..."));
            }
        });

        findViewById(R.id.tv_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://" + getString(R.string.paginaweb_texto);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
