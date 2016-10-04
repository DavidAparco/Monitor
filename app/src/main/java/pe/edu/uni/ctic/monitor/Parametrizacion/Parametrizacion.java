package pe.edu.uni.ctic.monitor.Parametrizacion;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.uni.ctic.monitor.Censado;
import pe.edu.uni.ctic.monitor.ConexionServer;
import pe.edu.uni.ctic.monitor.Device.Dispositivo;
import pe.edu.uni.ctic.monitor.Device.DispositivoAdapter;
import pe.edu.uni.ctic.monitor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Parametrizacion extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner1;
    List<String> list ;
    ArrayAdapter<String> dataAdapter;
    RadioButton rb1,rb2;

    private static String LOG_TAG = "CardViewActivity2";
    private RecyclerView mRecyclerView;
    private DispositivoAdapter mDisAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList <Dispositivo>results;
    ArrayList<Censado> resultsCensado;
    int posicion;

    public Parametrizacion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_parametrizacion, container, false);
        spinner1 = (Spinner) rootView.findViewById(R.id.spinner);
        list = new ArrayList<>();
        actualizaSpinner();
        rb1= (RadioButton)rootView.findViewById(R.id.radioBCansat);
        rb2= (RadioButton)rootView.findViewById(R.id.radioBSensor);
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        results= new ArrayList<>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDisAdapter = new DispositivoAdapter(results);
        mRecyclerView.setAdapter(mDisAdapter);
        return rootView;
    }

    public void actualizaSpinner(){
        dataAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("aca","intem selected");
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();

        if(list.get(position).equals("Zona 2")){
            //new cargarDispositivos("consInf/").execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBCansat:
                if (checked){
                    rb2.setChecked(false);
                    list.clear();
                    new cargarTiposSensor("consFiltro/cansat").execute();
                    break;
                }
            case R.id.radioBSensor:
                if (checked){
                    rb1.setChecked(false);
                    list.clear();
                    new cargarTiposSensor("consFiltro/sensor").execute();
                    break;
                }
        }
    }

    private class cargarTiposSensor extends AsyncTask<String, Void, String> {
        String url;
        public cargarTiposSensor(String url){
            this.url=url;
        }

        @Override
        protected String doInBackground(String... urls) {
            String respues="...";
            try {
                ConexionServer cs= new ConexionServer();
                list = cs.receiveFiltroDispositivo(url);
            }catch (IOException e) {
                e.printStackTrace();
            }
            return respues;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("respuesta", result);
            //cargaCardViews(results);
            actualizaSpinner();
        }
    }

}
