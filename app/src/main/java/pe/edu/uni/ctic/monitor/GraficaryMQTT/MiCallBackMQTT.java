package pe.edu.uni.ctic.monitor.GraficaryMQTT;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.uni.ctic.monitor.Censado;

public class MiCallBackMQTT implements MqttCallback {
    private final String GioDBug = "GioDBug";

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(GioDBug,"Connection was lost!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.d(GioDBug,"Message llego Arrived!: " + topic + ": " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(GioDBug,"Delivery Complete!");
    }

    public Censado converJson(String contentAsString){
        Censado censado=null;
        try {
            JSONObject jsonObject = new JSONObject(contentAsString);

            censado= new Censado(jsonObject.getString("id_cansat"),
                    jsonObject.getString("id_sensor"),
                    (float)jsonObject.getDouble("value"),
                    jsonObject.getString("fecha") ,
                    jsonObject.getString("hora") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return censado;
    }
}
