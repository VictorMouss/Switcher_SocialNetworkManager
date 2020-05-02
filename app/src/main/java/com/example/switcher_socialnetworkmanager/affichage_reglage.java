package com.example.switcher_socialnetworkmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class affichage_reglage extends AppCompatActivity {
    /* Activity adaptative des réglages - Boutons "A propos", "Nous contacter", "Signaler un problème" */

    //Attributs utilisés dans les méthodes de l'Activity
    Button btn_retour; //Bouton pour revenir sur la page des réglages
    TextView txt_email;//TextView contenant le contenu du message de l'email (Contact/Problème)
    TextView txt_labelActivity;//Texte affiché, chanegant selon le bouton cliqué
    Button btn_send;//Bouton pour envoyer le mail

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //appel du super de onCreate de l'Activty
        Intent intent = getIntent(); //on récupère l'intent pour les extra
        //on récupère l'indice du bouton cliqué depuis la page réglages
        final int indiceBoutonClique = intent.getIntExtra("indiceBoutonClique", -1);

        setContentView(R.layout.activity_affichage_reglage); //layout

        //Récupèration des élements
        btn_retour = findViewById(R.id.btn_retour_publi);
        txt_email = findViewById(R.id.txt_prob);
        txt_labelActivity = findViewById(R.id.txt_whatprob);
        btn_send = findViewById(R.id.btn_signal);
        //Les éléments non récurents sont rendus invisibles
        txt_email.setVisibility(View.INVISIBLE);
        txt_labelActivity.setVisibility(View.INVISIBLE);
        btn_send.setVisibility(View.INVISIBLE);

        TextView txtTopBar = findViewById(R.id.lbl_topBar); //textView du nom de la page (en haut)
        TextView txtAffichageReglage = findViewById(R.id.textViewAffichageReglage);//label adaptatif

        btn_retour.setOnClickListener(new View.OnClickListener() { //action lorsque le bouton retour est cliqué
            @Override
            public void onClick(View v) {
                finish();//on ferme l'activity
            }
        });


        switch (indiceBoutonClique) { //dans la méthode onCreate, en fonction du bouton cliqué dans la page réglages
            //(pour l'adaptivité de l'Activity)
            case 0: //si le bouton cliqué est le bouton "A propos"
                txtTopBar.setText(getString(R.string.str_about)); //On change le label et on met le texte <A propos>
                txtAffichageReglage.setText(getString(R.string.str_about_text));
                break;

            case 1://si le bouton cliqué est le bouton "Signaler un problème"
                txtTopBar.setText(getString(R.string.str_signal_prob_short));//On change le label
                btn_send.setText(getString(R.string.str_signal_prob_short));//On change le texte du bouton

                //On rend le bouton, le texte du message et le label de descritpions visibles
                txt_email.setVisibility(View.VISIBLE);
                txt_labelActivity.setVisibility(View.VISIBLE);
                btn_send.setVisibility(View.VISIBLE);

                btn_send.setOnClickListener(new View.OnClickListener() { //Lorsque le bonton est cliqué
                    @Override
                    public void onClick(View v) {
                        String txt_prob = txt_email.getText().toString(); //on récupère le texte de l'editText
                        Intent intent = new Intent(Intent.ACTION_SEND); //on crée une intent pour envoyer un mail
                        //on crée le corps du mail, qu'on passe en extra au mail avec son identifiant(3),
                        //et on réitère l'action pour le mail de destination(1) ainsi que le sujet  (2)
                        txt_prob = "Problème technique signalé. " + "\n" + "\n" + "Descritpion du problème : "
                                + txt_prob + "\n" + "\n" + "Description suplémentaire du problème : ";
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"service.technique@switcher.com"}); //(1)
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Problème technique");//(2)
                        intent.putExtra(Intent.EXTRA_TEXT, txt_prob);//(3)
                        startActivity(intent); //On lance l'Activity pour envoyer le mail
                    }
                });
                break;

            case 2://si le bouton cliqué est le bouton "Nous contacter"
                txtTopBar.setText(getString(R.string.str_contact_us));//On change les labels
                btn_send.setText(getString(R.string.str_signal_prob_short));//On change le texte du bouton
                txt_labelActivity.setText(R.string.str_what_to_say);
                btn_send.setText(getString(R.string.str_contact_us));
                //On rend les élements visibles
                txt_email.setVisibility(View.VISIBLE);
                txt_labelActivity.setVisibility(View.VISIBLE);
                btn_send.setVisibility(View.VISIBLE);
                btn_send.setOnClickListener(new View.OnClickListener() {//Lorsque le bonton est cliqué
                    @Override
                    public void onClick(View v) {
                        String txt_prob = txt_email.getText().toString();//on récupère le texte de l'editText
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        //on crée une intent pour envoyer un mail
                        //on crée le corps du mail, qu'on passe en extra au mail avec son identifiant(3),
                        //et on réitère l'action pour le mail de destination(1) ainsi que le sujet  (2)
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"switcher@contact.com"});//(1)
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Demande de contact");//(2)
                        intent.putExtra(Intent.EXTRA_TEXT, txt_prob);//(3)
                        startActivity(intent);//On lance l'Activity pour envoyer le mail
                    }
                });
                break;
        }
    }
}
