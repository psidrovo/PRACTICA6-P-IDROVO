/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.controlador;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul Idrovo
 */
public class ControladorDirectorio {

    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<String> listarArchivos() {
        List<String> directorio = new ArrayList<>();
        File raiz = new File(ruta);
        if (raiz.exists()) {
            File[] elementos = raiz.listFiles();
            for (File elemento : elementos) {
                if (!elemento.isDirectory()) {
                    if (!elemento.isHidden()) {
                        directorio.add(elemento.getName());
                    }
                }
            }
            return directorio;
        }
        return null;
    }

    public List<String> listarDirectorio() {
        List<String> directorio = new ArrayList<>();
        File raiz = new File(ruta);
        if (raiz.exists()) {
            File[] elementos = raiz.listFiles();
            if(elementos==null){
                return null;
            }
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    if (!elemento.isHidden()) {
                        directorio.add(elemento.getName());
                    }
                }
            }
            return directorio;
        }
        return null;
    }

    public List<String> listarArchivosOcultos() {
        List<String> directorio = new ArrayList<>();
        File raiz = new File(ruta);
        if (raiz.exists()) {
            File[] elementos = raiz.listFiles();
            for (File elemento : elementos) {
                if (!elemento.isDirectory()) {
                    if (elemento.isHidden()) {
                        directorio.add(elemento.getName());
                    }
                }
            }
            return directorio;
        }
        return null;
    }

    public List<String> listarDirectoriosOcultos() {
        List<String> directorio = new ArrayList<>();
        File raiz = new File(ruta);
        if (raiz.exists()) {
            File[] elementos = raiz.listFiles();
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    if (elemento.isHidden()) {
                        directorio.add(elemento.getName());
                    }
                }
            }
            return directorio;
        }
        return null;
    }

    public void crearDirectorio(String dir) {
        File rutaCreacion = new File(dir);
        if (!rutaCreacion.exists()) {
            rutaCreacion.mkdir();
        }
    }
    public void crearArchivo(String dir) {
        File rutaCreacion = new File(dir);
        if (!rutaCreacion.exists()) {
            try {
                rutaCreacion.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ControladorDirectorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eliminarDirectorio(File rutaEliminar) {
        if (rutaEliminar.exists()) {
            File archivosTam[] = rutaEliminar.listFiles();
            if (archivosTam != null) {
                int DirLength = archivosTam.length;
                for (int x = 0; x < DirLength; x++) {
                    if (archivosTam[x].isFile()) {
                        archivosTam[x].delete();
                    } else {
                        eliminarDirectorio(archivosTam[x]);
                    }
                }
                rutaEliminar.delete();
            }
        }
    }

    public void renombrarDirectorio(String rutaAntigua, String rutaNueva) {
        File rutaRenombrar = new File(rutaAntigua);
        if (rutaRenombrar.exists()) {
            rutaRenombrar.renameTo(new File(rutaNueva));
        }
    }

    public String mostrarInformacion() {
        String datos = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        File rutaInformacion = new File(ruta);
        //Path\
        datos = rutaInformacion.getAbsolutePath() + "\n";
        //Tamaño 
        double tamanio = tamanioDir(rutaInformacion) / 1024;
        String total = "";
        DecimalFormat df = new DecimalFormat("#0.00");
        if (tamanio > 1024) {
            tamanio = tamanio / 1024;
            total = df.format(tamanio) + " GB\n";
        } else {
            total = df.format(tamanio) + " Mb\n";
        }
        datos = datos + (total);
        //Permisos de lectura        
        if (rutaInformacion.canRead()) {
            datos = datos + ("Tiene permisos de lectura\n");
        } else {
            datos = datos + ("No tiene permisos de lectura\n");
        }
        //Permisos de escritura
        if (rutaInformacion.canWrite()) {
            datos = datos + ("Tiene permisos de escritura\n");
        } else {
            datos = datos + ("No tiene permisos de escritura\n");
        }
        //Ultima Fecha Modificacion
        datos = datos + (sdf.format(rutaInformacion.lastModified()));

        return datos;

    }

    public double tamanioDir(File rutaInformacion) {
        double tamanio = 0;
        File archivosTam[] = rutaInformacion.listFiles();
        if (archivosTam != null) {
            int DirLength = archivosTam.length;
            for (int x = 0; x < DirLength; x++) {
                if (archivosTam[x].isFile()) {
                    tamanio += ((archivosTam[x].length() / 1024.0));
                } else {
                    tamanio += tamanioDir(archivosTam[x]);
                }
            }
        }else if(rutaInformacion.isFile()){
            tamanio += ((rutaInformacion.length() / 1024.0));
        }
        return tamanio;
    }
}
