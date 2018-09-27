package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class lista_productos extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<Producto> adapterProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        ProductoRepository productos = new ProductoRepository();

        spinner = (Spinner) findViewById(R.id.cmbProductosCategoria);
        adapterProducto = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productos.getLista());
        adapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterProducto);
    }


}
