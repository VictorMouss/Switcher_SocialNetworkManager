package com.example.switcher_socialnetworkmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class affichage_reglage extends AppCompatActivity {

    Button btn_retour;
    TextView txt_email;
    TextView txt_whatprob;
    Button btn_signal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final int indiceBoutonClique = intent.getIntExtra("indiceBoutonClique", -1);
        //Log.i("indice bouton cliqué",indiceBoutonClique+"");

        setContentView(R.layout.activity_affichage_reglage);
        btn_retour = findViewById(R.id.btn_retour_publi);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView txtTopBar = findViewById(R.id.lbl_topBar);
        txt_email = findViewById(R.id.txt_prob);
        txt_email.setVisibility(View.INVISIBLE);
        TextView txtAffichageReglage = findViewById(R.id.textViewAffichageReglage);
        txt_whatprob = findViewById(R.id.txt_whatprob);
        txt_whatprob.setVisibility(View.INVISIBLE);
        btn_signal = findViewById(R.id.btn_signal);


        btn_signal.setVisibility(View.INVISIBLE);

        switch (indiceBoutonClique) {
            case 0:
                txtTopBar.setText(getString(R.string.str_about));
                txtAffichageReglage.setText(getString(R.string.str_about_text));
                break;
            case 1:
                txtTopBar.setText(getString(R.string.str_signal_prob_short));
                btn_signal.setText(getString(R.string.str_signal_prob_short));
                txt_email.setVisibility(View.VISIBLE);
                txt_whatprob.setVisibility(View.VISIBLE);
                btn_signal.setVisibility(View.VISIBLE);
                btn_signal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt_prob = txt_email.getText().toString();
                        Date date = Calendar.getInstance().getTime();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        txt_prob = "Problème technique signalé. " + "\n" + "\n" + "Descritpion du problème : "
                                + txt_prob + "\n" + "\n" + "Description suplémentaire du problème : ";
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"service.technique@switcher.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Problème technique");
                        intent.putExtra(Intent.EXTRA_TEXT, txt_prob);
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                txtTopBar.setText(getString(R.string.str_contact_us));
                txt_email.setVisibility(View.VISIBLE);
                btn_signal.setText(getString(R.string.str_signal_prob_short));
                txt_whatprob.setVisibility(View.VISIBLE);
                txt_whatprob.setText("Que souhaitez vous nous dire ?");
                btn_signal.setVisibility(View.VISIBLE);
                btn_signal.setText(getString(R.string.str_contact_us));
                btn_signal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt_prob = txt_email.getText().toString();
                        Date date = Calendar.getInstance().getTime();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"switcher@contact.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Demande de contact");
                        intent.putExtra(Intent.EXTRA_TEXT, txt_prob);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}
