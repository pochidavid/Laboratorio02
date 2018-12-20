package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.CategoriaRest;
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

    private Producto productoSeleccionado;

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


        final ProductoRepository productos = new ProductoRepository();
        int idProductoSel=0;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                CategoriaRest catRest = new CategoriaRest();
                try {
                    final Categoria[] cats = catRest.listarTodas().toArray(new Categoria[0]);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterCategoria = new
                                    ArrayAdapter<Categoria>(listaProducto.this, android.R.layout.simple_spinner_dropdown_item, cats);
                            spinner = (Spinner) findViewById(R.id.cmbProductosCategoria);
                            spinner.setAdapter(adapterCategoria);
                            spinner.setSelection(0);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    adapterProductos.clear();
                                    adapterProductos.addAll(productos.buscarPorCategoria((Categoria) parent.getItemAtPosition(position)));
                                    adapterProductos.notifyDataSetChanged();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });

                            product = ProductoRepository.buscarPorCategoria((Categoria) spinner.getSelectedItem());
                            adapterProductos = new ArrayAdapter<Producto>(listaProducto.this, android.R.layout.simple_list_item_single_choice,product);
                            listaproductos = (ListView) findViewById(R.id.lstProductos);
                            listaproductos.setAdapter(adapterProductos);

                        }
                    });
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }};


        Thread unHilo = new Thread(r);
        unHilo.start();

        final TextView categoriaSelec = (TextView) findViewById(R.id.textView);

        findViewById(R.id.btnProdAddPedido).setOnClickListener(btnProdAddPedido);



        /*
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

        );*/

        listaproductos = (ListView) findViewById(R.id.lstProductos);

        listaproductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSeleccionado = (Producto) adapterProductos.getItem(position);
            }
        });

        if(parametro==1){
            t.setVisibility(View.VISIBLE);
            e.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);
        }

    }

    private final View.OnClickListener btnProdAddPedido = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String c = e.getText().toString();
            Producto p = productoSeleccionado;

            Integer idProducto = p.getId();
            Integer cantidad = Integer.parseInt(c);

            Intent intentResultado = new Intent();
            intentResultado.putExtra("cantidad", cantidad);
            intentResultado.putExtra("idProducto", idProducto);
            setResult(Activity.RESULT_OK, intentResultado);
            finish();

        }
    };

}
