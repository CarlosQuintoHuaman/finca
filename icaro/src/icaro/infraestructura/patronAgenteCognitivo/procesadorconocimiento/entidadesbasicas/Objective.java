package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas;

import java.util.StringTokenizer;


/**
 * Defines an Objective
 */
public abstract class Objective {

    static public final int PENDING = 0;
    static public final int FOCUSED = 1;
    static public final int REFINED = 2;
    static public final int SOLVING = 3;
    static public final int SOLVED = 4;
    static public final int FAILED = 5;
    private String id;
    private int state;

    public Objective() {
        this.state = this.PENDING;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRefined() {
        this.setState(REFINED);

    }

    public void setSolved() {
        this.setState(SOLVED);

    }

    public void setPending() {
        this.setState(PENDING);

    }

    public void setSolving() {
        this.setState(SOLVING);

    }

    public void setFailed() {
        this.setState(FAILED);

    }

    public void setFocused() {
        this.setState(FOCUSED);

    }

    public int getState() {
        return this.state;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getClassName() {
        return this.getClass().getName();
    }

    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            return ((Objective) o).getID().equals(this.getID());
        }
        return false;
    }

    /**
     * Devuelve la descripci�n del estado del objetivo
     */
    public String getStateComoCadena() {
        String dev = "";

        switch (this.state) {
            case PENDING:
                dev = "PENDING";
                break;
            case FOCUSED:
                dev = "FOCUSED";
                break;
            case REFINED:
                dev = "REFINED";
                break;
            case SOLVING:
                dev = "SOLVING";
                break;
            case SOLVED:
                dev = "SOLVED";
                break;
            case FAILED:
                dev = "FAILED";
                break;
        }
        return dev;
    }

    /**
     * Devuelve una representaci�n del Objetivo como una cadena de texto
     *
     */
    public String toString() {

        String res = null;
        StringTokenizer st = new StringTokenizer(this.getClassName(), ".");

        while (st.hasMoreTokens()) {
            res = st.nextToken();
        }

        return res + " Estado: " + this.getStateComoCadena();

    }
}
