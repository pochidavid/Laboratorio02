package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import javax.xml.datatype.Duration;

import ar.edu.utn.frsf.dam.isi.laboratorio02.dao.PedidoRepository;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static String ESTADO_ACEPTADO="ar.edu.utn.frsf.dam.isi.laboratorio02.ESTADO_ACEPTADO";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Integer idPedido = intent.getExtras().getInt("idPedido");
        String action = intent.getAction();
        //if(action.equals(ESTADO_ACEPTADO)){
            Toast.makeText(context,"Pedido para "+ PedidoRepository.buscarPorId(idPedido).getMailContacto()+ " ha cambiado de estado a ACEPTADO", Toast.LENGTH_SHORT).show();
        //}
    }
}
