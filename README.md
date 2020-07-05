# PRACTICA 06 Idrovo P
	PRÁCTICA DE LABORATORIO 

# CARRERA: COMPUTACION	ASIGNATURA: PROGRAMACION ORIENTADA A OBJETOS
# NRO. PRÁCTICA:	6	
# TÍTULO PRÁCTICA: Desarrollo de una aplicación informática para la gestión de directorios en Java utilizando manejo de excepciones
# OBJETIVO ALCANZADO:
  •	Desarrollo de una aplicación de persistir datos en archivos.
  •	Implementación de control de excepciones en el desarrollo de aplicaciones.
  •	Uso de la plataforma github para el avance de la aplicación.
# ACTIVIDADES DESARROLLADAS
  1.	Creación de un proyecto de Java en NetBeans en base al diagrama UML e interfaz propuesta por el docente, esta solo fue propuesta como sugerencia para la creación del   proyecto.
 
 
  2.	La aplicación cumple con los requisitos que son:
    •	La ruta es dinámica y el usuario puede ingresar cualquier ruta.
    •	Desde el menú Gestionar Directorio, se puede crear, eliminar y renombrar un directorio. 

  3.	Repositorio en github: https://github.com/psidrovo/PRACTICA6-P-IDROVO.git 


# RESULTADO(S) OBTENIDO(S):
  a)	Resultados       
  Como resultados tenemos el código del programa, el cual funciona según lo propuesto. A continuación, se colocará el código de todas las clases utilizadas, esto se puede corroborar en el link de github expuesto con anterioridad.
  Los Paquetes creados son:
  ec.edu.ups.controlador -> Contiene la clase que controla el directorio
  ec.edu.ups.multimedia -> Contiene los iconos usados en la vista
  ec.edu.ups.vista -> Contiene la interfaz grafica de la aplicacion

  # Clases del Paquete Controlador
    •	ControladorDirectorio

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

 # Clases paquete Vista
``•	VistaDirectorio


package ec.edu.ups.vista;

import ec.edu.ups.controlador.ControladorDirectorio;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.AEADBadTagException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Paul Idrovo
 */
public class VistaDirectorio extends javax.swing.JFrame {

    private ControladorDirectorio controladorDirectorio;
    private String rutaAntigua;

    public VistaDirectorio() {
        initComponents();
        controladorDirectorio = new ControladorDirectorio();
        rutaAntigua = "";
        this.setLocationRelativeTo(this);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/ec/edu/ups/multimedia/carpeta.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstDirectorio = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaInformacion = new javax.swing.JTextArea();
        btArchivosOcultos = new javax.swing.JButton();
        btDirectorioOculto = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btArchivos = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnCrear = new javax.swing.JMenuItem();
        mnEliminar = new javax.swing.JMenuItem();
        mnRenombrar = new javax.swing.JMenuItem();
        mnSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DIRECTORIO");

        lstDirectorio.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lstDirectorio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstDirectorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstDirectorioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstDirectorio);

        txtaInformacion.setEditable(false);
        txtaInformacion.setColumns(20);
        txtaInformacion.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtaInformacion.setRows(5);
        jScrollPane2.setViewportView(txtaInformacion);

        btArchivosOcultos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btArchivosOcultos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/ups/multimedia/estadistica.png"))); // NOI18N
        btArchivosOcultos.setText("Listar Archivos Ocultos");
        btArchivosOcultos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btArchivosOcultosActionPerformed(evt);
            }
        });

        btDirectorioOculto.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btDirectorioOculto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/ups/multimedia/carpeta.png"))); // NOI18N
        btDirectorioOculto.setText("Listar Directorios Ocultos");
        btDirectorioOculto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDirectorioOcultoActionPerformed(evt);
            }
        });

        txtRuta.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtRuta.setText("D:\\PROYECTO JAVA");
        txtRuta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRutaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutaFocusLost(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setText("RUTA:");

        btArchivos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btArchivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/edu/ups/multimedia/estadistica.png"))); // NOI18N
        btArchivos.setText("Listar Archivos ");
        btArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btArchivosActionPerformed(evt);
            }
        });

        jMenuBar1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jMenu1.setText("GESTIONAR DIRECTORIO");

        mnCrear.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mnCrear.setText("Crear");
        mnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCrearActionPerformed(evt);
            }
        });
        jMenu1.add(mnCrear);

        mnEliminar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mnEliminar.setText("Eliminar");
        mnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEliminarActionPerformed(evt);
            }
        });
        jMenu1.add(mnEliminar);

        mnRenombrar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mnRenombrar.setText("Renombrar");
        mnRenombrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRenombrarActionPerformed(evt);
            }
        });
        jMenu1.add(mnRenombrar);

        mnSalir.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        mnSalir.setText("Salir");
        mnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mnSalir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtRuta))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btArchivosOcultos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(80, 80, 80)
                        .addComponent(btDirectorioOculto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btArchivos)
                            .addComponent(btDirectorioOculto)
                            .addComponent(btArchivosOcultos)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutaFocusLost
        Listar();
    }//GEN-LAST:event_txtRutaFocusLost
    public void Listar() {
        controladorDirectorio.setRuta(rutaAntigua);
        List<String> directorio = controladorDirectorio.listarDirectorio();
        if (directorio != null) {
            String imp[] = new String[directorio.size()];
            for (int i = 0; i < imp.length; i++) {
                imp[i] = directorio.get(i);
            }
            lstDirectorio.setListData(imp);
            rutaAntigua = txtRuta.getText();
        } else {
            JOptionPane.showMessageDialog(null, "<html>El DIRECTORIO <strong>" + txtRuta.getText() + "</strong> NO EXISTE", "ERROR", JOptionPane.ERROR_MESSAGE);
            txtRuta.setText(rutaAntigua);
        }
    }
    private void btDirectorioOcultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDirectorioOcultoActionPerformed
        controladorDirectorio.setRuta(txtRuta.getText());
        List<String> directorio = controladorDirectorio.listarDirectoriosOcultos();
        if (!directorio.isEmpty()) {
            String imp[] = new String[directorio.size()];
            DefaultListModel<String> model = new DefaultListModel<>();
            for (int i = 0; i < imp.length; i++) {
                model.addElement(directorio.get(i));
                //imp[i] = directorio.get(i);
            }
            //lstDirectorio.setListData(imp);
            lstDirectorio.setModel(model);
        }
    }//GEN-LAST:event_btDirectorioOcultoActionPerformed

    private void btArchivosOcultosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btArchivosOcultosActionPerformed
        controladorDirectorio.setRuta(txtRuta.getText());
        List<String> directorio = controladorDirectorio.listarArchivosOcultos();
        if (!directorio.isEmpty()) {
            String imp[] = new String[directorio.size()];
            for (int i = 0; i < imp.length; i++) {
                imp[i] = directorio.get(i);
            }
            lstDirectorio.setListData(imp);
        }
    }//GEN-LAST:event_btArchivosOcultosActionPerformed

    private void btArchivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btArchivosActionPerformed
        controladorDirectorio.setRuta(txtRuta.getText());
        List<String> directorio = controladorDirectorio.listarArchivos();
        if (!directorio.isEmpty()) {
            String imp[] = new String[directorio.size()];
            for (int i = 0; i < imp.length; i++) {
                imp[i] = directorio.get(i);
            }
            lstDirectorio.setListData(imp);
        }
    }//GEN-LAST:event_btArchivosActionPerformed

    private void lstDirectorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstDirectorioMouseClicked
        if (!lstDirectorio.isSelectionEmpty()) {
            txtRuta.setText(rutaAntigua + "\\" + lstDirectorio.getSelectedValue().toString());
            controladorDirectorio.setRuta(txtRuta.getText());
            String datos = controladorDirectorio.mostrarInformacion();
            txtaInformacion.setText(datos);
        }
    }//GEN-LAST:event_lstDirectorioMouseClicked

    private void mnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnSalirActionPerformed

    private void mnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCrearActionPerformed
        String nombreDir = JOptionPane.showInputDialog("INGRESE EL NOMBRE DEL NUEVO DIRECTORIO", "");
        if(nombreDir!=null ){
            if(!nombreDir.equals("")){
                if (nombreDir.contains(".")) {
                    controladorDirectorio.crearArchivo(rutaAntigua + "\\" + nombreDir);
                } else {
                    controladorDirectorio.crearDirectorio(rutaAntigua + "\\" + nombreDir);
                }
            }else{
                JOptionPane.showMessageDialog(null, "NOMBRE NO VALIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        Listar();
    }//GEN-LAST:event_mnCrearActionPerformed

    private void mnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEliminarActionPerformed
        if (!lstDirectorio.isSelectionEmpty()) {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "<html>DESEA ELIMINAR EL REGISTRO <strong>" + lstDirectorio.getSelectedValue() + "</strong> ?</html>");

            if (JOptionPane.OK_OPTION == confirmar) {
                controladorDirectorio.eliminarDirectorio(new File(rutaAntigua + "\\" + lstDirectorio.getSelectedValue().toString()));
                JOptionPane.showMessageDialog(null, "EL DIRECTORIO " + lstDirectorio.getSelectedValue() + " HA SIDO ELIMINADO", "ELIMINAR", JOptionPane.INFORMATION_MESSAGE);
                txtRuta.setText(rutaAntigua);
                Listar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA SELECCIONADO NINGUN DIRECTORIO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnEliminarActionPerformed

    private void mnRenombrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRenombrarActionPerformed
        if (!lstDirectorio.isSelectionEmpty()) {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "<html>DESEA EDITAR EL REGISTRO <strong>" + lstDirectorio.getSelectedValue() + "</strong> ?</html>");

            if (JOptionPane.OK_OPTION == confirmar) {
                String nombreDir = JOptionPane.showInputDialog("INGRESE EL NUEVO NOMBRE DEL NUEVO DIRECTORIO", "");
                controladorDirectorio.renombrarDirectorio(rutaAntigua + "\\" + lstDirectorio.getSelectedValue().toString(), rutaAntigua + "\\" + nombreDir);
                JOptionPane.showMessageDialog(null, "EL DIRECTORIO HA SIDO RENOMBRADO ", "RENOMBRADO", JOptionPane.INFORMATION_MESSAGE);
                Listar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA SELECCIONADO NINGUN DIRECTORIO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_mnRenombrarActionPerformed

    private void txtRutaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutaFocusGained
        rutaAntigua = txtRuta.getText();
    }//GEN-LAST:event_txtRutaFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaDirectorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaDirectorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaDirectorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaDirectorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    System.out.println("Error setting Java LAF: " + e);
                }
                new VistaDirectorio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btArchivos;
    private javax.swing.JButton btArchivosOcultos;
    private javax.swing.JButton btDirectorioOculto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstDirectorio;
    private javax.swing.JMenuItem mnCrear;
    private javax.swing.JMenuItem mnEliminar;
    private javax.swing.JMenuItem mnRenombrar;
    private javax.swing.JMenuItem mnSalir;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextArea txtaInformacion;
    // End of variables declaration//GEN-END:variables
}

# Utilizacion.

El usuario deberá colocar una ruta, al salir del textbox, se eejecutará en método para traer esa ruta a la lista, esto se realiza con el evento FocusLost, cuando se de click en algún directorio o archivo se colocará su nombre en la ruta para facilitar el avance entre rutas y además cargará toda la información del mismo en el textArea que se encuentra conjunto a la lista. 

# CONCLUSIONES:
Esta práctica nos ayuda a aplicar nuestros conocimientos sobre directorios, además de desarrollar nuevos métodos y fomentar la investigación de cómo realizar ciertas operaciones propuestas como el tamaño de un archivo que necesita recursividad, ayudándonos a mejorar nuestro aprendizaje fuera del entrono educativo de la Universidad.

# RECOMENDACIONES:
En esta práctica no he visto una recomendación fuerte, talvez continuar con este tipo de prácticas que ayuden a los estudiantes a mejorar su habilidad de investigación como se explica en la conclusión.
