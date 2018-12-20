package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

@Dao
public interface CategoriaDao {

    @Insert
    void crearCategoria(Categoria categoria);

    @Query("SELECT * FROM categoria")
    List<Categoria> listarCategorias();

    @Delete
    void borrarCategoria(Categoria categoria);

    @Update
    void actualizar(Categoria categoria);

}
