package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static String ESTADO_ACEPTADO="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("APP04","REcibido "+intent.getAction());

        // an Intent broadcast.
        Integer idPedido = intent.getExtras().getInt("idPedido");
        String action = intent.getAction();
        if(action.equalsIgnoreCase(ESTADO_ACEPTADO)){
            Pedido p = PedidoRepository.buscarPorId(idPedido);
            Toast.makeText(context,"Pedido para "+ p.getMailContacto()+ " ha cambiado de estado a ACEPTADO", Toast.LENGTH_SHORT).show();


        Intent notifyIntent = new Intent(context, DetallePedido.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notifyIntent.putExtra("idPedido",idPedido);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "CANAL01")
                    .setContentTitle("Tu Pedido fue ACEPTADO")
                    .setContentText("El costo será de $"+ p.total()+ "\n Previsto el envio para "+p.getFecha().getTime())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_truck)
                    .setContentIntent(notifyPendingIntent);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            // notificationId is a unique int for each notification that you must define

            notificationManager.notify(idPedido, notification.build());

       }


    }

}
