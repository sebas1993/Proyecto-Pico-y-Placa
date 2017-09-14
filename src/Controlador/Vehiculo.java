/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Hashtable;
import javax.swing.JOptionPane;

/**
 *
 * @author rosemberth
 */
public class Vehiculo {

    protected Hashtable<Integer, String> diasPicoPlaca;

    private String valPlaca;
    private String fecha;
    private String hora;

    public Vehiculo(String valPlaca, String fecha, String hora) {
        this.valPlaca = valPlaca;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getValPlaca() {
        return valPlaca;
    }

    public void setValPlaca(String valPlaca) {
        this.valPlaca = valPlaca;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Verificamos si existe la palabra filtro en el arreglo
     *
     * @param filtro
     * @param arreglo
     * @return
     */
    public Boolean existeEnCadena(String filtro, String[] arreglo) {
        if (arreglo.length > 0) {
            for (String arreglo1 : arreglo) {
                if (arreglo1.equals(filtro)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Validamos la placa con el ultimo dígito y comparando el número de la
     * placa con el de la fecha ingresada
     *
     * @param diaObtenido
     * @return
     */
    public Boolean validarPlaca(String diaObtenido) {
        inicializarVariables();
        try {
            Integer ultimoDigito = Integer.parseInt(valPlaca.substring(valPlaca.length() - 1));
            String diaComparar = diasPicoPlaca.get(ultimoDigito);
            return diaComparar.equals(diaObtenido);
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Se busca en la fecha, primero que el día ingresado no sea fin de semana
     * para despues verificar en la hora ingresada
     *
     * @return
     */
    public Boolean buscarEnFecha() {
        Boolean ret = false;
        String[] diasNoPicoPlaca = {"SÁBADO", "DOMINGO"};
        Calendar calendario = Calendar.getInstance();
        try {
            Date fechaDate = convertirStringFecha(fecha, "dd/MM/yyyy");
            calendario.setTime(fechaDate);
            String nombreDia = obtenerNombreDia(calendario);
            if (!existeEnCadena(nombreDia, diasNoPicoPlaca)) {
                if (validarPlaca(nombreDia)) {
                    return buscarEnHora();
                } else {
                    ret = true;
                }
                // return 
            } else {
                ret = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 01: Sintaxis incorrecta en el ingreso de datos");

        }
        return ret;
    }

    /**
     * Se valida la hora ingresada para encontrar si esta dentro del rango
     * permitido
     *
     * @return
     */
    public Boolean buscarEnHora() {
        Boolean ret = true;
        String[] horarioPicoPlacaMañana = {"07:30", "09:30"};
        String[] horarioPicoPlacaTarde = {"16:00", "19:30"};
        if ((hora.compareTo(horarioPicoPlacaMañana[0]) >= 0 && hora.compareTo(horarioPicoPlacaMañana[1]) <= 0)
                || (hora.compareTo(horarioPicoPlacaTarde[0]) >= 0 && hora.compareTo(horarioPicoPlacaTarde[1]) <= 0)) {
            ret = false;
        }
        return ret;
    }

    /**
     * Se inicializa la HashTable
     */
    public void inicializarVariables() {
        diasPicoPlaca = new Hashtable<Integer, String>();
        diasPicoPlaca.put(0, "LUNES");
        diasPicoPlaca.put(1, "LUNES");
        diasPicoPlaca.put(2, "MARTES");
        diasPicoPlaca.put(3, "MARTES");
        diasPicoPlaca.put(4, "MIÉRCOLES");
        diasPicoPlaca.put(5, "MIÉRCOLES");
        diasPicoPlaca.put(6, "JUEVES");
        diasPicoPlaca.put(7, "JUEVES");
        diasPicoPlaca.put(8, "VIERNES");
        diasPicoPlaca.put(9, "VIERNES");

    }

    public void validarPicoPlaca() {
        if (buscarEnFecha()) {
            JOptionPane.showMessageDialog(null, "El vehículo PUEDE transitar sin problemas en la fecha y hora ingresadas");
        } else {
            JOptionPane.showMessageDialog(null, "El vehículo NO PUEDE transitar en la fecha y hora ingresadas");
        }
    }

    /**
     * Obtiene el nombre del dia ingresado
     *
     * @param calendar
     * @return
     */
    public String obtenerNombreDia(Calendar calendar) {

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("es", "US")).toUpperCase();
    }

    /**
     * Convierte la fecha String a Date segun el patron ingresado
     *
     * @param fecha
     * @param patron
     * @return Date
     */
    public static Date convertirStringFecha(String fecha, String patron) {
        try {
            SimpleDateFormat sdfConvertir = new SimpleDateFormat(patron);
            return sdfConvertir.parse(fecha);
        } catch (ParseException ex) {
            return null;
        }
    }
}
