package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class HistorialPedido extends AppCompatActivity implements View.OnClickListener {

    private Button btnHistorialNuevo;
    private Button btnHistorialMenu;
    private ListView listaPedido;
    PedidoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedido);

        btnHistorialMenu = findViewById(R.id.btnHistorialMenu);
        btnHistorialNuevo = findViewById(R.id.btnHistorialNuevo);
        listaPedido = findViewById(R.id.lstHistorialPedidos);
        adapter = new PedidoAdapter(this,PedidoRepository.getLista());
        listaPedido.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHistorialMenu: finish();
            case R.id.btnHistorialNuevo: startActivity(new Intent(this,DarAltaPedido.class)); break;
        }
    }
}
