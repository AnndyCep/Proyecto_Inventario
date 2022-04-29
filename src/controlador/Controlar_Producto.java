package controlador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.Producto_Dado;
import vista.Crud_Interfaz;

public class Controlar_Producto implements ActionListener { //Poder controlar las acciones de la interfaz

    //Variable globales
    private int codigo;
    private String nombre;
    private double precio;
    private int inventario;

    //Intancias
    Producto producto = new Producto(); // Intancia  para acceder a los metodos
    Producto_Dado productodado = new Producto_Dado();
    Crud_Interfaz vista = new Crud_Interfaz();
    DefaultTableModel modtabla = new DefaultTableModel();  //Cargar todos los nombres a la base de datos

    public Controlar_Producto(Crud_Interfaz vista) {
        this.vista = vista;
        vista.setVisible(true);
        agregar_Eventos();
        listarTabla();

    }

    private void agregar_Eventos() { //Se agrega un evento para la lectura de los botones
        vista.getBtnAgragar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnBorrar().addActionListener(this);
        vista.getBtnBorrar1().addActionListener(this);
        vista.getTblTablaInventario().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);
            }
        });

    }

    private void listarTabla() { // Cargar la tabla
        String[] titulo = new String[]{"Codigo", "Nombre", "Precio", "Inventario"};
        modtabla = new DefaultTableModel(titulo, 0);
        List<Producto> listaProducto = productodado.Listar(); // retorna una lista y la guarmos dentro de otra lista
        for (Producto producto : listaProducto) {
            modtabla.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(),
                producto.getInventario()});// Se adiciona una nueva fila, se almacenas todos los productos que solicitamos
        }
        vista.getTblTablaInventario().setModel(modtabla);
        vista.getTblTablaInventario().setPreferredSize(new Dimension(350, modtabla.getRowCount() * 16));

    }

    private void llenarCampos(MouseEvent e) {
        JTable targer = (JTable) e.getSource();
        vista.getTxtNombre().setText(vista.getTblTablaInventario().getModel().getValueAt(targer.getSelectedRow(), 1).toString()); //Se aacede al campo de la tabla, se envia un vatos, se accede a la vista 
        vista.getTxtPrecio().setText(vista.getTblTablaInventario().getModel().getValueAt(targer.getSelectedRow(), 2).toString());
        vista.getTxtInventario().setText(vista.getTblTablaInventario().getModel().getValueAt(targer.getSelectedRow(), 3).toString());

    }

    // validar formulario, confimar si estan completos los campos de las tablas
    private boolean validarDatos() {
        if ("".equals(vista.getTxtNombre().getText()) || "".equals(vista.getTxtPrecio().getText()) || "".equals(vista.getTxtInventario().getText())) { // Se compara que si el valor de campo nombre esta vacio se envia un error
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacios");
            return false;
        }
        return true;

    }

    // validar formulario, confimar si estan completos los campos de las tablas
    private boolean cargarDatos() {
        try {
            nombre = vista.getTxtNombre().getText();
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            inventario = Integer.parseInt(vista.getTxtInventario().getText());
            return true;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos de precio como inventario deben ser numericos ");
            System.out.println("Error al cargar datos" + e);
            return false;
        }
    }

    private void limpiarDatos() {
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtInventario().setText("");
    }

    private void agregarProducto() {  //Se debe confirmar que no haya ningun cambpo vacio 
        try {
            if (validarDatos() == true) { //Se debe validar el formulario, este debe ser igual a true que todo estaa bien
                if (cargarDatos() == true) { //Se debe hacer la validacion en la validacion de fomularios
                    Producto producto = new Producto(nombre, precio, inventario);
                    productodado.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                    limpiarDatos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error agregando roducto" + e);
        } finally {
            listarTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {  //Se utiliza al final para confirmar el boton orpimido
       if (ae.getSource() == vista.getBtnAgragar()){
           agregarProducto();
       }
    }

}
