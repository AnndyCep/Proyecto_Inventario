package modelo;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Producto_Dado {

    ConexionBD conexion = new ConexionBD(); //Intancia de conexion
    Connection con;
    PreparedStatement ps;  // Ejecutar las consultas de la tabla
    ResultSet rs; // Para guardar las consultas
    
    public List Listar (){
        String sql = "select * from productos";
        List<Producto> lista = new ArrayList ();
        
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setInventario(rs.getInt(4));
                lista.add(producto);
                
            }
        } catch (Exception e) {
            System.out.println("Error en listar");
        }
        return lista;
    } // fin del metodo listar
    
    public void agregar ( Producto producto){
        String sql = "Insert into productos (nombre, precio.inventario) values (?,?,?)"; //Se realiza la consulta
        try {
            con = conexion.conectarBaseDatos(); // se realiza la conexion a la base de datos
            ps = con.prepareStatement(sql); // Se prepara la consulta
            ps.setString(1, producto.getNombre()); 
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario()); //Al tener la conulta completa 
            ps.executeUpdate(); // Se ejecuta la consulta o la insercion. update, para agregar
            
        } catch (SQLException e) {
            System.out.println("Error agregar" + e);
        }
    } //Fin del metodo agregar
} // fin de la clase 
