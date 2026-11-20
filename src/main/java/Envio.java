/**
 * Un envío agrupa los paquetes que un cliente manda a un mismo destino.
 * Un envío admite como máximo tres paquetes.
 *
 * Ninguno de los métodos de esta clase escribe en pantalla.
 *
 * @author/ Sergio H.
 */
public class Envio {

    private static final int MAX_PAQUETES = 3;

    private String codigo;
    private String codigoPostal;
    private Paquete paquete1, paquete2, paquete3;

    /**
     * Crea un envío sin paquetes.
     *
     * @param codigo       código de seguimiento, p.ej. "PX00000001R"
     * @param codigoPostal código postal del destino, p.ej. "36202"
     */
    public Envio(String codigo, String codigoPostal) {
        this.codigo = codigo;
        this.codigoPostal = codigoPostal;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * @return cuántos paquetes tiene el envío (0 a 3)
     * Para mejorar el algoritmo: Los paquetes se añaden siempre en orden, así que nunca hay huecos entre ellos.
     */
    public int getNumeroPaquetes() {
        int numPqt = 0;
        if (paquete1 != null){
            numPqt++;
        }
        if (paquete2 != null){
            numPqt++;
        }
        if (paquete3 != null){
            numPqt++;
        }
        return numPqt;
    }

    /**
     * @return true si ya no se pueden añadir más paquetes
     */
    public boolean estaCompleto() {
        if (paquete3 != null){
            return true;
        }
        return false;
    }

    /**
     * Añade un paquete al envío si no está completo.
     *
     * @return true si se ha añadido, false si el envío estaba completo
     */
    public boolean addPaquete(Paquete paquete) {
        if (paquete1 == null){
            paquete1 = paquete;
            return true;
        }
        else if (paquete2 == null){
            paquete2 = paquete;
            return true;
        }
        else if (paquete3 == null){
            paquete3 = paquete;
            return true;
        }
        return false;
    }

    /**
     * Crea un paquete con las medidas y peso indicados y lo añade al envío.
     *
     * @return true si se ha añadido, false si el envío estaba completo
     */
    public boolean addPaquete(double alto, double ancho, double largo, double peso) {
        if (paquete1 == null){
            paquete1 = new Paquete(alto, ancho, largo, peso);
            return true;
        }
        else if (paquete2 == null){
            paquete2 = new Paquete(alto, ancho, largo, peso);
            return true;
        }
        else if (paquete3 == null) {
            paquete3 = new Paquete(alto, ancho, largo, peso);
            return true;
        }
        return false;
    }

    /**
     * @return la suma de los pesos reales de los paquetes (kg)
     */
    public double calcularPesoReal() {
        if (paquete3 != null){
            return paquete1.getPeso() + paquete2.getPeso() + paquete3.getPeso();
        }
        else if (paquete2 != null){
            return paquete1.getPeso() + paquete2.getPeso();
        }
        else if (paquete1 != null) {;
            return paquete1.getPeso();
        }
        return 0;
    }

    /**
     * Suma los pesos facturables de los paquetes y redondea hacia arriba
     * al kilo entero.
     *
     * @return el peso facturable del envío (kg enteros)
     */
    public double calcularPesoFacturable() {
        if (paquete3 != null){
            return Math.ceil(paquete1.calcularPesoFacturable() + paquete2.calcularPesoFacturable() + paquete3.calcularPesoFacturable());
        }
        else if (paquete2 != null){
            return Math.ceil(paquete1.calcularPesoFacturable() + paquete2.calcularPesoFacturable());
        }
        else if (paquete1 != null) {;
            return Math.ceil(paquete1.calcularPesoFacturable());
        }
        return 0;
    }

    /**
     * @return el paquete de mayor peso real; si hay empate, el que se añadió
     * antes; null si el envío no tiene paquetes
     */
    public Paquete getPaqueteMasPesado() {
        double peso = 0;
        if (paquete3 != null) {
            peso = Math.max(paquete3.getPeso(), Math.max(paquete1.getPeso(), paquete2.getPeso()));
            if (peso == paquete3.getPeso()) {
                return paquete3;
            } else if (peso == paquete2.getPeso()) {
                return paquete2;
            } else if (peso == paquete1.getPeso()) {
                return paquete1;
            } else if (paquete2 != null) {
                peso = Math.max(paquete2.getPeso(), paquete1.getPeso());
                if (peso == paquete2.getPeso()) {
                    return paquete2;
                } else if (peso == paquete1.getPeso()) {
                    return paquete1;
                } else if (paquete1 != null) {
                    return paquete1;
                }
                return null;
            }
        }
        return null;
    }

    /**
     * @return representación textual del envío (ver enunciado)
     */
    public String toString() {
      String pqtTxt = "";
      switch (getNumeroPaquetes()){
          case 1:
              pqtTxt = "1. " + paquete1.getDimension().toString();
              break;
          case 2:
              pqtTxt = "1. " + paquete1.getDimension().toString();
              pqtTxt += "\n 2. " + paquete2.getDimension().toString();
              break;
          case 3:
              pqtTxt = "1. " + paquete1.getDimension().toString();
              pqtTxt += "\n 2. " + paquete2.getDimension().toString();
              pqtTxt += "\n 3. " + paquete3.getDimension().toString();
              break;
      }

      return "Envío " + codigo + " -> CP " + codigoPostal + " (" + getNumeroPaquetes() + " paquete/s)" +
        "\n" + pqtTxt + "\n" + "Peso Real: " + calcularPesoReal() + " kg | Peso facturable: " + calcularPesoFacturable() + " kg";
    }
}
