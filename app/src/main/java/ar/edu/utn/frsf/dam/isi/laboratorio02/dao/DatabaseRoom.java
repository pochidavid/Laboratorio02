package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;


@Database(entities = {Categoria.class,Producto.class}, version = 1)
public abstract class DatabaseRoom extends RoomDatabase {
    private static DatabaseRoom db = null;

    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();

    private static void create(Context context){
        db = Room.databaseBuilder(context,DatabaseRoom.class,"Database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DatabaseRoom getInstance(Context context){
        if(db == null) create(context);
        return db;
    }
}
