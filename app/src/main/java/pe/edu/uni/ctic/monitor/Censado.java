package pe.edu.uni.ctic.monitor;

import android.os.Parcel;
import android.os.Parcelable;

public class Censado implements Parcelable {
    private String id_cansat;
    private String id_sensor;
    private float value;
    private String fecha;
    private String hora;

    public Censado(String id_cansat, String id_sensor, float value, String fecha, String hora) {
        this.id_cansat = id_cansat;
        this.id_sensor = id_sensor;
        this.value = value;
        this.fecha = fecha;
        this.hora = hora;
    }

    protected Censado(Parcel in) {
        id_cansat = in.readString();
        id_sensor = in.readString();
        value = in.readFloat();
        fecha = in.readString();
        hora = in.readString();
    }

    public static final Creator<Censado> CREATOR = new Creator<Censado>() {
        @Override
        public Censado createFromParcel(Parcel in) {
            return new Censado(in);
        }

        @Override
        public Censado[] newArray(int size) {
            return new Censado[size];
        }
    };

    public String getId_cansat() {
        return id_cansat;
    }

    public String getId_sensor() {
        return id_sensor;
    }


    public float getValue() {
        return value;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_cansat);
        dest.writeString(id_sensor);
        dest.writeFloat(value);
        dest.writeString(fecha);
        dest.writeString(hora);
    }
}
