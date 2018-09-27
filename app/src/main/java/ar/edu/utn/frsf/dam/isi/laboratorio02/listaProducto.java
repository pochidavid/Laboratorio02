package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class listaProducto extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<Categoria> adapterCategoria;
    private ArrayAdapter<Producto> adapterProductos;
    private List product;
    private ListView listaproductos;

    private TextView t;
    private EditText e;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        int parametro = this.getIntent().getExtras().getInt("Codigo");

        t = (TextView) findViewById(R.id.textView5);
        t.setVisibility(View.INVISIBLE);
        e = (EditText) findViewById(R.id.edProdCantidad);
        e.setVisibility(View.INVISIBLE);
        b = (Button) findViewById(R.id.btnProdAddPedido);
        b.setVisibility(View.INVISIBLE);


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

        if(parametro==1){
            t.setVisibility(View.VISIBLE);
            e.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);
        }

    }
    private View.OnClickListener btnProdAddPedido = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            String cantidad = e.getText().toString();
            Producto p = (Producto) spinner.getItemAtPosition(spinner.getSelectedItemPosition());

            int idProducto = p.getId();

            Intent intentResultado = new Intent();
            intentResultado.putExtra("cantidad", cantidad);
            intentResultado.putExtra("idProducto", idProducto);
            setResult(Activity.RESULT_OK,intentResultado);
            finish();

        }
    }

}
