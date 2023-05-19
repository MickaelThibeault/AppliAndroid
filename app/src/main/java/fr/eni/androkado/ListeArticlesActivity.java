package fr.eni.androkado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eni.androkado.adapters.ArticleAdapter;
import fr.eni.androkado.bo.Article;
import fr.eni.androkado.dao.ArticlesDAO;

public class ListeArticlesActivity extends AppCompatActivity {

    private static final String TAG = "ENI";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Article> articles = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar_liste, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_ajout) {
            Intent intentAjout = new Intent(this,AjoutEditActivity.class);
            startActivity(intentAjout);
        } else  if (itemId == R.id.item_configuration) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener monClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Log.i(TAG, "Position : "+v.getTag());
            Intent intent = new Intent(ListeArticlesActivity.this, MainActivity.class);
            intent.putExtra("article", articles.get(position));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_articles);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        chargementDonnees();
        chargementRecycler();
    }

    private void chargementRecycler() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new ArticleAdapter(articles, monClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    private void chargementDonnees() {
        ArticlesDAO dao = new ArticlesDAO(this);
        SharedPreferences spIntra = getSharedPreferences("configuration",MODE_PRIVATE);
        Boolean valeurTri = spIntra.getBoolean(ConfigurationActivity.CLE_TRI,false);
        articles = dao.get(valeurTri);
    }
}