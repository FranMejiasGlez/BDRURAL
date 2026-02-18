/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import primero.Tipos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Mejias Gonzalez Francisco
 */
public class CrudTipos {

    public boolean alta() {
        BufferedReader teclado = null;
        String descripcion;
        int numero;
        Session sesion = SessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            System.out.println("Introduce un numero de tipo: ");
            teclado = new BufferedReader(new InputStreamReader(System.in));
            numero = Integer.valueOf(teclado.readLine());
            if (numero <= 0) {
                System.out.println("Error: El codigo debe ser mayor que 0");
                return false;
            }
            Tipos tipoExistente = (Tipos) sesion.get(Tipos.class, numero);
            if (tipoExistente != null) {
                System.out.println("Error: Ya existe un tipo con ese codigo");
                return false;
            }
            System.out.println("Introduce una descripcion: ");
            descripcion = teclado.readLine();
            if (descripcion == null || descripcion.trim().isEmpty()) {
                System.out.println("Error: La descripcion no puede estar vacía");
                return false;
            }
            Query query = sesion.createQuery(
                    "SELECT COUNT(t) FROM primero.Tipos t WHERE t.descripcion = :desc");
            query.setParameter("desc", descripcion);
            Long count = (Long) query.uniqueResult();

            if (count > 0) {
                System.out.println("ERROR: Ya existe un tipo con esa descripcion.");
                return false;
            }
            tx = sesion.beginTransaction();
            Tipos tipo = new Tipos();
            tipo.setCodigo(numero);
            tipo.setDescripcion(descripcion);
            sesion.save(tipo);
            tx.commit();

            System.out.println("Alta correcta");
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
        	try {
				teclado.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            sesion.close();
        }

    }

    public boolean baja() {


        try {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String descripcion;
            int numero;
            Session sesion = SessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx = null;
            // 1. Pedir codigo al usuario (usando BufferedReader como en alta)
            System.out.print("Ingrese el codigo a eliminar: ");
            int codigo = Integer.parseInt(teclado.readLine());


            try {
                // 2. Verificar que existe el registro
                Tipos tipo = (Tipos) sesion.get(Tipos.class, codigo);

                if (tipo == null) {
                    System.out.println("ERROR: No existe un tipo con ese codigo.");
                    return false;
                }

                // 3. Eliminar dentro de transaccion
                tx = sesion.beginTransaction();
                sesion.delete(tipo);
                tx.commit();

                System.out.println("Baja exitosa: Tipo eliminado.");
                return true;

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                System.out.println("ERROR: " + e.getMessage());
                return false;
            } finally {
            	teclado.close();
                sesion.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(CrudTipos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modificar() {
        Session sesion = SessionFactoryUtil.getSessionFactory().openSession();
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        Transaction tx = null;
        Tipos tipo = null;

        try {
            while (tipo == null) {
                System.out.print("Ingrese el codigo a modificar: ");
                try {
                    int codigo = Integer.parseInt(teclado.readLine());
                    tipo = (Tipos) sesion.get(Tipos.class, codigo);

                    if (tipo == null) {
                        System.out.println("ERROR: No existe un tipo con ese codigo. Inténtelo de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Debe ingresar un número entero válido.");
                }
            }

            System.out.println("Descripción actual: " + tipo.getDescripcion());
            System.out.print("Ingrese la nueva descripción: ");
            String nuevaDescripcion = teclado.readLine();

            tx = sesion.beginTransaction();
            tipo.setDescripcion(nuevaDescripcion);
            tx.commit();

            System.out.println("Modificación exitosa.");
            return true;

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
            return false;
        } finally {
        	try {
				teclado.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            sesion.close();
        }
    }
    
    public void resumenTipos(){
    	Session sesion = SessionFactoryUtil.getSessionFactory().openSession();
    	
    	try{
    	List<Tipos> tipos = sesion.createQuery("FROM  primero.Tipos").list();
    	
    	for(Tipos tipo : tipos){

    		String consulta = "SELECT COUNT(a) FROM primero.Alojamientos a WHERE a.tipo = :codigo";
    		Long conteo = (Long) sesion.createQuery(consulta).setParameter("codigo", tipo.getCodigo()).uniqueResult();
    		
    		System.out.println(tipo.getDescripcion() + ": " + conteo);
    	}
    	} finally{
    		sesion.close();
    	}
    }
    
    
    
    
}
