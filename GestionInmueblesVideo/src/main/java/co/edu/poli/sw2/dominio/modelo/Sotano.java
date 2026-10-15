package co.edu.poli.sw2.dominio.modelo;

/**
 * Representa el sotano opcional de una casa.
 */
public class Sotano {

    private double areaM2;
    private String tipoUso;

    /**
     * Constructor de la clase Sotano.
     *
     * @param areaM2 area del sotano en metros cuadrados
     * @param tipoUso uso que se le da al sotano
     */
    public Sotano(double areaM2, String tipoUso) {

        if (areaM2 <= 0) {
            throw new IllegalArgumentException(
                "El area del sotano debe ser mayor que cero.");
        }

        if (tipoUso == null || tipoUso.isBlank()) {
            throw new IllegalArgumentException(
                "El tipo de uso es obligatorio.");
        }

        this.areaM2 = areaM2;
        this.tipoUso = tipoUso.trim();
    }

    public double getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(double areaM2) {
        if (areaM2 <= 0) {
            throw new IllegalArgumentException(
                "El area debe ser mayor que cero.");
        }
        this.areaM2 = areaM2;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        if (tipoUso == null || tipoUso.isBlank()) {
            throw new IllegalArgumentException(
                "El tipo de uso es obligatorio.");
        }
        this.tipoUso = tipoUso.trim();
    }

    @Override
    public String toString() {
        return "Sotano de " + areaM2
                + " m2 - Uso: " + tipoUso;
    }
}