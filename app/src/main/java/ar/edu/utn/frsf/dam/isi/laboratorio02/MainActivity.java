package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnNuevoPedido;
    private Button btnHistorial;
    private Button btnListaProductos;
    private Button btnPrepararPedidos;
    private Button btnConfiguracion;
    private Button btnCategoria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNuevoPedido = (Button) findViewById(R.id.btnMainNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DarAltaPedido.class);
                startActivity(i);
            }
        });

        btnHistorial = (Button) findViewById(R.id.btnHistorialPedidos);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistorialPedido.class);
                startActivity(i);
            }
        });

        btnListaProductos = (Button) findViewById(R.id.btnListaProductos);
        btnListaProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, listaProducto.class);
                i.putExtra("Codigo",0);
                startActivity(i);
            }
        });

        btnPrepararPedidos = findViewById(R.id.btnPrepararPedidos);
        btnPrepararPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrepararPedidoService.class);
                startService(intent);
            }
        });

        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });

        btnCategoria = findViewById(R.id.btnCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
                startActivity(intent);
            }
        });

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        // Crear el canal de notificaciones pero solo para API 26 io superior
        // dado que NotificationChannel es una clase nueva que no está incluida // en las librerías de soporte qeu brindan compatibilidad hacía atrás
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.canal_estado_nombre);
            String description = getString(R.string.canal_estado_descr);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CANAL01", name, importance);
            channel.setDescription(description);
        // Registrar el canal en el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
