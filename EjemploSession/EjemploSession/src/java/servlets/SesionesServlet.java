/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;


public class SesionesServlet extends HttpServlet {
   
     public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         
        try{
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            Connection conexion = null;
            Statement sentencia = null;
            ResultSet resultado = null;
            String url="jdbc:mysql://localhost/lab";
            String user="root";
            String password="n0m3l0";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(url, user, password);
                sentencia = conexion.createStatement();
                System.out.println("Si se conecto");
            }
            catch(Exception e){
                System.out.println("NO se conecto");
            }
            resultado = sentencia.executeQuery("select * from usuarios where nombre = '"+nombre+"' and apellido = '"+apellido+"'");
            int contador = 0;
            while(resultado.next()){
                contador++;
            }
            if(contador == 1){
                HttpSession sesion = request.getSession();
                sesion.setAttribute("claveSesion", nombre + apellido);
                response.setContentType("text/html");
                //Mostramos los  valores en el cliente
                PrintWriter out = response.getWriter();
                out.println("<h1>La sesión es correcta</h1>");
                out.println("<br>");
                out.println("<a href=\"/EjemploSession/catalogo.jsp\"> Link al catalogo del carrito  </a>");
                out.println("<br>");
                out.println("ID de la sesi&oacute;n: " + sesion.getId());
  
            }
            else{
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Login</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<script>alert('llave incorrecta inicie nuevamente sesion')</script>");
                    out.println("<script>window.location='index.jsp'</script>");
                    out.println("</body>");
                    out.println("</html>");
                    }
                HttpSession sesion = request.getSession(false);
            }
        }catch(Exception e){
            System.out.println("Error en la conexion");
        }
     }
}