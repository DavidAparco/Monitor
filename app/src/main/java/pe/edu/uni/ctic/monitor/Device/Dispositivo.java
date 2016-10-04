package pe.edu.uni.ctic.monitor.Device;

import android.os.Parcel;
import android.os.Parcelable;

public class Dispositivo implements Parcelable{
    private int imagen;
    private String id;
    private String modelo;
    private String fecha;
    private String unidad;



    protected Dispositivo(Parcel in) {
        imagen = in.readInt();
        id = in.readString();
        modelo = in.readString();
        fecha = in.readString();
        unidad = in.readString();
        tipo_sensor = in.readString();
    }


    public static final Creator<Dispositivo> CREATOR = new Creator<Dispositivo>() {
        @Override
        public Dispositivo createFromParcel(Parcel in) {
            return new Dispositivo(in);
        }

        @Override
        public Dispositivo[] newArray(int size) {
            return new Dispositivo[size];
        }
    };

    public String getTipo_sensor() {
        return tipo_sensor;
    }

    private String tipo_sensor;

    public Dispositivo(int imagen, String id, String tipo_sensor, String unidad, String modelo, String fecha) {
        this.imagen = imagen;
        this.id = id;
        this.modelo = modelo;
        this.fecha = fecha;
        this.unidad = unidad;
        this.tipo_sensor = tipo_sensor;
    }

    public int getImagen() {
        return imagen;
    }

    public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUnidad() {
        return unidad;
    }



    @Override
    public String toString() {
        return "Dispositivo{" +
                "tipo_sensor='" + tipo_sensor + '\'' +
                ", unidad='" + unidad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", modelo='" + modelo + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imagen);
        dest.writeString(id);
        dest.writeString(modelo);
        dest.writeString(fecha);
        dest.writeString(unidad);
        dest.writeString(tipo_sensor);
    }
}
