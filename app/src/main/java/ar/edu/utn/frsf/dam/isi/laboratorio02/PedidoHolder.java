package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;

public class PedidoHolder extends RecyclerView.ViewHolder{

    public TextView tvMailPedido;
    public TextView tvHoraEntrega;
    public TextView tvCantidadItems;
    public TextView tvPrecio;
    public TextView estado;
    public ImageView tipoEntrega;
    public Button btnCancelar;

    PedidoHolder(View base){
        super(base);
        this.tvMailPedido = base.findViewById(R.id.tvMailPedido);
        this.tvHoraEntrega = base.findViewById(R.id.tvHoraEntrega);
        this.tvCantidadItems = base.findViewById(R.id.tvCantidadItems);
        this.tvPrecio = base.findViewById(R.id.tvPrecio);
        this.estado = base.findViewById(R.id.estado);
        this.tipoEntrega = base.findViewById(R.id.tipoEntrega);
        this.btnCancelar = base.findViewById(R.id.btnCancelar);
    }


}
