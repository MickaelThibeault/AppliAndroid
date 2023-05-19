package fr.eni.androkado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import fr.eni.androkado.bo.Article;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "ENI";
    Article article = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar_details, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.item_modifier)
        {
                editerArticle();
                return true;
        } else if (item.getItemId() == R.id.item_envoyer) {
            envoyerMessage();
            return true;
        }
        return true;
    }

    private void editerArticle() {
        // Envoi à l'activité d'affichage du détail
        Intent intention = new Intent(this, AjoutEditActivity.class);
        intention.putExtra("fr.eni.android.androcado.ArticleSelectionne", article);
        startActivity(intention);
    }

    private void envoyerMessage() {
        // Envoi à l'activité d'affichage du détail
        Intent intention = new Intent(this, ListContactActivity.class);
        startActivity(intention);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        article = getIntent().getParcelableExtra("article");

        TextView tvNom =  findViewById(R.id.tv_article);
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvPrix = findViewById(R.id.tv_prix);
        RatingBar rating = findViewById(R.id.rating_article);
        ToggleButton toggle = findViewById(R.id.btn_achete);

        if (article!=null) {
            tvNom.setText(article.getNom());
            tvDescription.setText(article.getDescription());
            tvPrix.setText(String.valueOf(article.getPrix()));
            rating.setRating(article.getNote());
            toggle.setChecked(article.isAchete());
        }
    }

    public void onClickUrl(View view) {
        Intent intention = new Intent(this, InfoUrlActivity.class);
        intention.putExtra("article", article);

        startActivity(intention);
    }

    public void onClickAchat(View view) {
        article.setAchete(!article.isAchete());
        Log.i(TAG,"Valeur achat " + article.isAchete());
    }
}