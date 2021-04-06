package com.example.porjetihm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    MatrixCursor matrixCursor;

    SimpleCursorAdapter adapter;
    ListView lv;
    int numCol = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Définition des colonnes
        // NB : SimpleCursorAdapter a besoin obligatoirement d'un ID nommé "_id"
        String[] columns = new String[] { "_id", "col1", "col2" };

        // Définition des données du tableau
        // les lignes ci-dessous ont pour seul but de simuler
        // un objet de type Cursor pour le passer au SimpleCursorAdapter.
        // Si vos données sont issues d'une base SQLite,
        // utilisez votre "cursor" au lieu du "matrixCursor"
        matrixCursor= new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[] { numCol+1,"nom"," * " });
        matrixCursor.addRow(new Object[] { numCol+1,"prenom"," * " });

        // on prendra les données des colonnes 1 et 2...
        String[] from = new String[] {"col1", "col2"};

        // ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[] { R.id.textViewCol1, R.id.textViewCol2};

        // création de l'objet SimpleCursorAdapter...
        adapter = new SimpleCursorAdapter(this, R.layout.row_item, matrixCursor, from, to, 0);

        // ...qui va remplir l'objet ListView
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }

    public void clicAjout (View view) {

        // on désérialise le layout qui est associé à la boîte de saisie
        final View boiteAjoutArticle =
                getLayoutInflater().inflate(R.layout.menu, null);

        /*
         * Création d'une boîte de dialogue :
         * - on positionne un titre
         * - on lui associe une vue (le layout précédemment désérialisé)
         * - on associe un libellé et un comportement au bouton positif :
         * on récupère le texte tapé par l'utilisateur dans l'EditText
         * on modifie la liste des achats, pour lui ajouter l'article saisi
         * à la position du clic, ou juste après la position du clic
         * on informe l'adaptateur qu'il y a une modification de liste qu'il
         * doit prendre en compte
         * - on associe un libellé et un comportement (null) au bouton négatif
         * - on rend la boîte visible
         */
        new AlertDialog.Builder(this)
                .setTitle("Ajouter champs")
                .setView(boiteAjoutArticle)
                .setPositiveButton("Ajouter",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int leBouton) {
                                EditText article = (EditText)
                                        boiteAjoutArticle.findViewById(R.id.saisieArticle);

                                matrixCursor.addRow(new Object[] { numCol+1,article.getText().toString()," * " });
                                lv.setAdapter(adapter);
                            }
                        })
                .setNegativeButton("Retour", null)
                .show();
    }

    /**
     * Méthode exécutée automatiquement lorsque l'on clique sur le bouton
     * "afficher une alerte"
     * @param bouton bouton sur lequel l'utilisateur a cliqué
     */
    public void clicSurAide(View bouton) {

        // on désérialise le layout qui est associé à la boîte de saisie
        final View boitehelp =
                getLayoutInflater().inflate(R.layout.help, null);

        new AlertDialog.Builder(this)
                .setTitle("Aide")
                .setView(boitehelp)
                .setPositiveButton("RETOUR", null)
                .show();
    }

    /**
     * Méthode exécutée automatiquement lorsque l'on clique sur le bouton
     * "afficher une alerte"
     * @param bouton bouton sur lequel l'utilisateur a cliqué
     */
    public void clicSurDelete(View bouton) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Etes vous sûr de vouloir supprimer cette ligne ?")
                .setPositiveButton("OUI", null)
                .setNegativeButton("NON", null)
                .show();
    }

    /**
     * Méthode exécutée automatiquement lorsque l'on clique sur le bouton
     * "afficher une alerte"
     * @param bouton bouton sur lequel l'utilisateur a cliqué
     */
    public void clicSurModification(View bouton) {

        // on désérialise le layout qui est associé à la boîte de saisie
        final View boiteModification =
                getLayoutInflater().inflate(R.layout.modification, null);

        new AlertDialog.Builder(this)
                .setTitle("Modification")
                .setView(boiteModification)
                .setPositiveButton("OUI", null)
                .setNegativeButton("NON", null)
                .show();
    }
}