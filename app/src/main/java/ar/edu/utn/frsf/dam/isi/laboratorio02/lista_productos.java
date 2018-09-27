package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class lista_productos extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Producto> adapterProductos;
    private List product;
    private ListView listaproductos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        ProductoRepository productos = new ProductoRepository();

        spinner = (Spinner) findViewById(R.id.cmbProductosCategoria);
        final TextView categoriaSelec = (TextView) findViewById(R.id.textView);

        adapterCategoria = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productos.getCategorias());
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoria);

        listaproductos = (ListView) findViewById(R.id.lstProductos);
        Categoria c = new Categoria();
        c.setId(1);
        c.setNombre("Entrada");
        product = ProductoRepository.buscarPorCategoria(c);
        adapterProductos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, product);
        listaproductos.setAdapter(adapterProductos);


        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        categoriaSelec.setText("Seleccionado: "+parent.getItemAtPosition(position).toString());
                        product = ProductoRepository.buscarPorCategoria((Categoria) parent.getItemAtPosition(position));
                        adapterProductos.clear();
                        for(Object p : product){
                            adapterProductos.add((Producto) p);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );


    }


}
