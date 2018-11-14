package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class DetallePedido extends AppCompatActivity {

    private ArrayAdapter adapterProductos;
    private EditText edtPedido;
    private EditText emailPedido;
    private EditText edtHora;
    private EditText edtDireccion;
    private RadioGroup rGroup;
    private RadioButton rRetira;
    private RadioButton rEnviar;
    private TextView lblTotalPedido;
    private Button btnPedidoVolver;
    private TextView lbl_direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        edtPedido = findViewById(R.id.edtPedidoDireccion);
        emailPedido = findViewById(R.id.edtPedidoCorreo);
        edtHora = findViewById(R.id.edtPedidoHoraEntrega);
        edtDireccion = findViewById(R.id.edtPedidoDireccion);
        rGroup = findViewById(R.id.radioGroup);
        rRetira = findViewById(R.id.radioRetira);
        rEnviar = findViewById(R.id.radioEnviar);
        lblTotalPedido = findViewById(R.id.lblTotalPedido);
        btnPedidoVolver = findViewById(R.id.btnPedidoVolver);
        lbl_direccion = findViewById(R.id.lbl_direccion);

        btnPedidoVolver.setOnClickListener(volverListener);

        BroadcastReceiver br = new EstadoPedidoReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(EstadoPedidoReceiver.ESTADO_ACEPTADO);
        getApplication().getApplicationContext().registerReceiver(br, filtro);

        displayPedido();
    }

    private View.OnClickListener volverListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainMenu = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(mainMenu);
        }
    };

    private void displayPedido(){


        // Set information about pedido
        Bundle bundle = getIntent().getExtras();
        try{
            Integer idPedido = bundle.getInt("idPedido");

            Pedido p = PedidoRepository.buscarPorId(idPedido);

            emailPedido.setText(p.getMailContacto());
            lblTotalPedido.setText("Total del pedido: $"+p.total());
            edtHora.setText(p.getFecha().getHours()+":"+p.getFecha().getMinutes());
            rEnviar.setEnabled(false);
            rRetira.setEnabled(false);

            if(p.getRetirar()){
                edtDireccion.setVisibility(View.GONE);
                lbl_direccion.setVisibility(View.GONE);
                rRetira.setChecked(true);
            }else{
                edtDireccion.setText(p.getDireccionEnvio());
                rEnviar.setChecked(true);
            }

            emailPedido.setEnabled(false);
            edtDireccion.setEnabled(false);
            edtHora.setEnabled(false);

        }catch(Exception e){

        }

    }
}
