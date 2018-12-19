package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import org.json.JSONException;

import java.io.IOException;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRetrofit;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.RestClient;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionProductoActivity extends AppCompatActivity {
    private Button btnMenu;
    private Button btnGuardar;
    private Spinner comboCategorias;
    private EditText nombreProducto;
    private EditText descProducto;
    private EditText precioProducto;
    private ToggleButton opcionNuevoBusqueda;
    private EditText idProductoBuscar;
    private Button btnBuscar;
    private Button btnBorrar;
    private Boolean flagActualizacion;
    private ArrayAdapter<Categoria> comboAdapter;
    private CategoriaRest categoriaRest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);

        flagActualizacion = false;

        opcionNuevoBusqueda = findViewById(R.id.abmProductoAltaNuevo);
        idProductoBuscar = findViewById(R.id.abmProductoIdBuscar);
        nombreProducto = findViewById(R.id.abmProductoNombre);
        descProducto = findViewById(R.id.abmProductoDescripcion);
        precioProducto = findViewById(R.id.abmProductoPrecio);
        comboCategorias = findViewById(R.id.abmProductoCategoria);
        btnMenu = findViewById(R.id.btnAbmProductoVolver);
        btnGuardar = findViewById(R.id.btnAbmProductoCrear);
        btnBuscar = findViewById(R.id.btnAbmProductoBuscar);
        btnBorrar= findViewById(R.id.btnAbmProductoBorrar);

        opcionNuevoBusqueda.setChecked(false);
        btnBuscar.setEnabled(false);
        btnBorrar.setEnabled(false);
        idProductoBuscar.setEnabled(false);

        opcionNuevoBusqueda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flagActualizacion =isChecked;
                btnBuscar.setEnabled(isChecked);
                btnBorrar.setEnabled(isChecked);
                idProductoBuscar.setEnabled(isChecked);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(idProductoBuscar.getText().toString());

                ProductoRetrofit clienteRest = RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);
                Call<Producto> buscarCall = clienteRest.buscarProductoPorId(id);

                buscarCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {
                        Producto producto = response.body();

                        nombreProducto.setText(producto.getNombre());
                        descProducto.setText(producto.getDescripcion());
                        precioProducto.setText(producto.getPrecio().toString());

                        Integer positionCombo = comboAdapter.getPosition(producto.getCategoria());
                        comboCategorias.setSelection(positionCombo);
                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {

                    }
                });
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreProducto.getText().toString();
                String descripcion = descProducto.getText().toString();
                Double precio = Double.parseDouble(precioProducto.getText().toString());
                Categoria categoria = (Categoria) comboCategorias.getSelectedItem();

                Producto p = new Producto(nombre,descripcion,precio,categoria);

                ProductoRetrofit clienteRest = RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);

                if(flagActualizacion){
                    Integer id = Integer.parseInt(idProductoBuscar.getText().toString());
                    Call<Producto> editarCall = clienteRest.actualizarProducto(id,p);

                    editarCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> response) {

                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                            System.out.print(t.getStackTrace());
                        }
                    });

                }else{
                    Call<Producto> altaCall= clienteRest.crearProducto(p);

                    altaCall.enqueue(new Callback<Producto>() {
                        @Override
                        public void onResponse(Call<Producto> call, Response<Producto> resp) {
                            // procesar la respuesta
                        }

                        @Override
                        public void onFailure(Call<Producto> call, Throwable t) {
                        } });
                }

                limpiarProducto();
                idProductoBuscar.setText("");
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                limpiarProducto();

                ProductoRetrofit clienteRest = RestClient.getInstance()
                        .getRetrofit()
                        .create(ProductoRetrofit.class);

                Integer id = Integer.parseInt(idProductoBuscar.getText().toString());
                Call<Producto> borrarCall = clienteRest.borrar(id);
                borrarCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {

                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {

                    }
                });

                idProductoBuscar.setText("");
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionProductoActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        //categoriaRest.listarTodas();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                categoriaRest = new CategoriaRest();

                try {
                    final Categoria[] cats = categoriaRest.listarTodas().toArray(new Categoria[0]);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            comboAdapter = new ArrayAdapter<>(GestionProductoActivity.this,android.R.layout.simple_spinner_dropdown_item,cats);
                            comboCategorias.setAdapter(comboAdapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread hilo = new Thread(r);
        hilo.start();

    }

    private void limpiarProducto(){
        nombreProducto.setText("");
        descProducto.setText("");
        precioProducto.setText("");
    }

}

