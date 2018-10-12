package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.ProductoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.PedidoDetalle;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class DarAltaPedido extends AppCompatActivity {

    private Pedido unPedido;
    private PedidoRepository repositorioPedido;
    private ProductoRepository repositorioProducto;

    private ArrayAdapter adapterProductos;
    private EditText edtPedido;
    private EditText emailPedido;
    private EditText edtHora;
    private EditText edtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_alta_pedido);

        edtPedido = findViewById(R.id.edtPedidoDireccion);
        emailPedido = findViewById(R.id.edtPedidoCorreo);
        edtHora = findViewById(R.id.edtPedidoHoraEntrega);
        edtDireccion = findViewById(R.id.edtPedidoDireccion);

        edtPedido.setEnabled(false);

        findViewById(R.id.btnPedidoAddProducto).setOnClickListener(btnPedidoAddProducto);
        findViewById(R.id.btnPedidoHacerPedido).setOnClickListener(pedidoHacerPedidoListener);

        unPedido = new Pedido();
        repositorioPedido = new PedidoRepository();
        repositorioProducto = new ProductoRepository();

        TextView vacio = (TextView) findViewById(R.id.textView);
        ListView listaPedido = findViewById(R.id.lstPedidoItems);
        adapterProductos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaPedido.setAdapter(adapterProductos);


        ((RadioGroup) findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);

                RadioButton radioEnviar = (RadioButton) group.findViewById(R.id.radioEnviar);

                if (checkedRadioButton.equals(radioEnviar)) edtPedido.setEnabled(true);
                else edtPedido.setEnabled(false);
            }
        });




    }
    private View.OnClickListener btnPedidoAddProducto = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(DarAltaPedido.this, listaProducto.class);
            i.putExtra("Codigo",1);
            startActivityForResult(i, 1);

        }
    };

    private  View.OnClickListener pedidoHacerPedidoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String direccion = edtPedido.getText().toString();
            unPedido.setDireccionEnvio(direccion);
            unPedido.setMailContacto(emailPedido.getText().toString());
            unPedido.setEstado(Pedido.Estado.REALIZADO);

            if(true){
                //Para validar hora
                String[] horaingresada = edtHora.getText().toString().split(":");
                GregorianCalendar hora = new GregorianCalendar();
                int valorHora = Integer.valueOf(horaingresada[0]);
                int valorMinuto = Integer.valueOf(horaingresada[1]);
                if(valorHora<0 || valorHora>23){
                    Toast.makeText(DarAltaPedido.this, "La hora ingresada ("+valorHora+") es incorrecta", Toast.LENGTH_LONG).show();
                    return;
                }
                if(valorMinuto<0 || valorMinuto>59){
                    Toast.makeText(DarAltaPedido.this, "Los minutos ("+valorMinuto+") son incorrectos", Toast.LENGTH_LONG).show();
                    return;
                }
                hora.set(Calendar.HOUR_OF_DAY,valorHora);
                hora.set(Calendar.MINUTE,valorMinuto);
                hora.set(Calendar.SECOND,Integer.valueOf(0));
                unPedido.setFecha(hora.getTime());

                repositorioPedido.guardarPedido(unPedido);

                unPedido = new Pedido();
                Log.d("APP_LAB02","Pedido "+unPedido.toString());
                finish();
            }else Toast.makeText(getApplicationContext(),"Uno o mas campos estan vacios",Toast.LENGTH_SHORT).show();
        }
    };

    private Boolean validar() {
        Boolean valido = true;

        if(edtHora.getText().toString().matches("") ||
                emailPedido.getText().toString().matches("") ||
                edtPedido.getText().toString().matches("")) valido = false;

        RadioButton radioEnviar = findViewById(R.id.radioEnviar);
        if(radioEnviar.isChecked() && edtDireccion.getText().toString().matches("")) valido=false;

        return valido;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==1){
                Producto p = ProductoRepository.buscarPorId(data.getExtras().getInt("idProducto"));

                adapterProductos.add(p);

                Integer cantidad = data.getExtras().getInt("cantidad");


                PedidoDetalle detalleProducto = new PedidoDetalle(cantidad,p);
                detalleProducto.setPedido(unPedido);

                ((TextView)findViewById(R.id.lblTotalPedido)).setText("Total del pedido: $"+unPedido.total());
            }
        }
    }

}
