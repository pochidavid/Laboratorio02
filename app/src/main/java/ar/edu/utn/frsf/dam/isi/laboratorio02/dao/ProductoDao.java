package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Dao
public interface ProductoDao {
    @Insert
    void crear(Producto producto);

    @Query("SELECT * FROM producto")
    List<Producto> listar();

    @Delete
    void borrar(Producto producto);

    @Update
    void actualizar(Producto producto);

    @Query("SELECT * FROM producto WHERE catid = :idCategoria")
    List<Producto> listarPorCategoria(Integer idCategoria);
}
