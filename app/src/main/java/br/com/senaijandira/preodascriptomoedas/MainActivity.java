package br.com.senaijandira.preodascriptomoedas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView txt_btc, txt_ltc, txt_bch;
    String retornobtc = "";
    String retornoltc = "";
    String retornobch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_btc = findViewById(R.id.txt_btc);
        txt_ltc = findViewById(R.id.txt_ltc);
        txt_bch = findViewById(R.id.txt_bch);

        consultarCriptomoedas();

    }

    public void consultarCriptomoedas(){

        final String URLbtc = "https://www.mercadobitcoin.net/api/BTC/ticker/";
        final String URLltc = "https://www.mercadobitcoin.net/api/LTC/ticker/";
        final String URLbch = "https://www.mercadobitcoin.net/api/BCH/ticker/";


        //Tarefa Assincrona - executado em segundo plano
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                //É necessário usar o arquivo HttpConnection
                retornobtc = HttpConnection.get(URLbtc);
                retornoltc = HttpConnection.get(URLltc);
                retornobch = HttpConnection.get(URLbch);

                //txt_btc.setText(retorno);

                //Log.d("consultarCriptomoedas", retornobtc);

                return null;
            }

            //Função executada quando acaba o doInBackground
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                    try {

                        //Puxando os campos que o json retorna para atualizar os elementos da tela
                        JSONObject objetobtc = new JSONObject(retornobtc);
                        JSONObject tickerbtc = objetobtc.getJSONObject("ticker");
                        String btc = tickerbtc.getString("last");

                        JSONObject objetoltc = new JSONObject(retornoltc);
                        JSONObject tickerltc = objetoltc.getJSONObject("ticker");
                        String ltc = tickerltc.getString("last");

                        JSONObject objetobch = new JSONObject(retornobch);
                        JSONObject tickerbch = objetobch.getJSONObject("ticker");
                        String bch = tickerbch.getString("last");

                        txt_btc.setText(btc);
                        txt_ltc.setText(ltc);
                        txt_bch.setText(bch);


                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
            }
        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_atualizar){
            consultarCriptomoedas();
        }

        return super.onOptionsItemSelected(item);
    }



}
