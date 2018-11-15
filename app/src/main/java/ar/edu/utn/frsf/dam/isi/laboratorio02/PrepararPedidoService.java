package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

import static java.lang.Thread.sleep;

public class PrepararPedidoService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public PrepararPedidoService() {
        super("PrepararPedidoService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable @Nullable Intent intent) {
        try {
            Thread.currentThread().sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PedidoRepository pedidoRepository = new PedidoRepository();
        List<Pedido> listaPedidos = PedidoRepository.getLista();

        for(Pedido p:listaPedidos){
            if(p.getEstado() == Pedido.Estado.ACEPTADO){
                p.setEstado(Pedido.Estado.EN_PREPARACION);

                Intent i = new Intent();
                i.setAction(EstadoPedidoReceiver.ESTADO_EN_PREPARACION);
                i.putExtra("idPedido",p.getId());

                sendBroadcast(i);
            }
        }
    }
}
