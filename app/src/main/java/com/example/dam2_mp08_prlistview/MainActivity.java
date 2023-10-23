package com.example.dam2_mp08_prlistview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;
        public String img;

        public Record(int _intents, String _nom, String _img ) {
            intents = _intents;
            nom = _nom;
            img = _img;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // Numeros aleatorios
    Random rnd = new Random();
    int apellido = 0;
    int nombre = 0;
    int id = 0;
    int foto = 0;

    // Lista de apellidos
    String[] apellidos = new String[7];

    // Lista de fotos
    String[] fotos = new String[4];

    //Lista de nombre
    String[] nombres = new String[7];

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los arrays de nombres, de apellidos y fotos
        apellidos[0] = "Fernandez";
        apellidos[1] = "Alvarez";
        apellidos[2] = "Jimenez";
        apellidos[3] = "Sanchez";
        apellidos[4] = "Gonzalez";
        apellidos[5] = "Perez";
        apellidos[6] = "Rodriguez";

        nombres[0] = "Gean";
        nombres[1] = "Geanfranco";
        nombres[2] = "Franco";
        nombres[3] = "Anfra";
        nombres[4] = "Fran";
        nombres[5] = "Anco";
        nombres[6] = "Ean";

        fotos[0] = "barbas";
        fotos[1] = "nini";
        fotos[2] = "ganador";
        fotos[3] = "acosador";

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo", "ganador") );
        records.add( new Record(12,"Pepe", "ganador") );
        records.add( new Record(42,"Laura", "ganador") );

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                if (getItem(pos).img.equals("acosador")) {
                    ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.acosador);
                } else if (getItem(pos).img.equals("barbas")) {
                    ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.barbas);
                } else if (getItem(pos).img.equals("nini")) {
                    ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.nini);
                } else if (getItem(pos).img.equals("ganador")) {
                    ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.ganador);
                }

                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<3;i++) {
                    nombre = rnd.nextInt(7);
                    apellido = rnd.nextInt(7);
                    id = rnd.nextInt(100);
                    foto = rnd.nextInt(4);
                    records.add(new Record(id, nombres[nombre] + " " + apellidos[apellido], fotos[foto]));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
        Button ordenar = findViewById(R.id.button2);
        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sort the records ArrayList based on the 'intents' field in ascending order
                Collections.sort(records, new Comparator<Record>() {
                    @Override
                    public int compare(Record record1, Record record2) {
                        return Integer.compare(record1.intents, record2.intents);
                    }
                });

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();
            }
        });
    }
}