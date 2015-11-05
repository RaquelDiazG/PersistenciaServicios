package es.upm.miw.persistenciaservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflador del menú: añade elementos a la action bar
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Menu", getString(item.getItemId()));
        switch (item.getItemId()) {
            case R.id.menuFavorites:
                showFavorites();
                break;
        }
        return true;
    }

    public void showPosts(View view) { //click on posts button (layout)
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
    }

    private void showFavorites() { //click on favorites button (menu)
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}
