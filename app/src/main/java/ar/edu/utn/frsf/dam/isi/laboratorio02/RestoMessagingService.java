package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class RestoMessagingService extends FirebaseMessagingService {
    public RestoMessagingService() {
    }
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Integer id = Integer.parseInt(remoteMessage.getData().get("ID_PEDIDO"));
        String nuevoEstado = remoteMessage.getData().get("NUEVO_ESTADO");

        PedidoRepository.buscarPorId(id).setEstado(Pedido.Estado.LISTO);

        if(nuevoEstado != null){
            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "CANAL01")
                    .setContentTitle("Tu pedido "+id+ " esta en estado "+nuevoEstado.toUpperCase())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_truck);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            notificationManager.notify(remoteMessage.getTtl(), notification.build());
        }else{
            Intent i = new Intent();
            i.setAction(EstadoPedidoReceiver.ESTADO_LISTO);
            i.putExtra("idPedido",id);

            sendBroadcast(i);
        }

    }
}
