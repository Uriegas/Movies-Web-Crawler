package com.trumam;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class CSVTest {

    @Test
    public void probar_Atributo_Tag(){
        Movie movie=new Movie("The help","Drama","When racism was a common think...");
        String real = movie.getTag();
        String esperado = "Drama";
        Assert.assertEquals(esperado, real);
    }

    @Test
    public void probar_Atributo_Title(){
        Movie movie=new Movie("The help","Drama","When racism was a common think...");
        String real = movie.getTitle();
        String esperado = "The help";
        Assert.assertEquals(esperado, real);
    }

    @Test
    public void arrojar_exception(){
        try {
            ArrayList<Movie> arrayPeliculas = new ArrayList<>();
            CreateCSV crearArchivoCSV = new CreateCSV("/poo-unidad-3-actividad-2-show-de-truman/resultado.csv");
            crearArchivoCSV.crearCSV(arrayPeliculas, crearArchivoCSV.getPath());
        }catch (Exception e){}
    }
    
    @Test
    public void probarCSV(){
        Movie m1=new Movie("The darkest minds","Cience fiction","Suddenly all the children start...");
        Movie m2=new Movie("The help","Drama","When racism was a common think...");
        Movie m3=new Movie("Luca","Comedy","Luca is a young sea moster...");
        ArrayList<Movie> arrayPeliculas = new ArrayList<>();
        arrayPeliculas.add(m1);
        arrayPeliculas.add(m2);
        arrayPeliculas.add(m3);
        //Se debe cambiar el path desde el inicio hasta antes de /resultado.csv
        //dependiendo de su propio path
        CreateCSV crearArchivoCSV = new CreateCSV("/home/marlly/Descargas/resultado.csv");
        crearArchivoCSV.crearCSV(arrayPeliculas, crearArchivoCSV.getPath());
    }

}
