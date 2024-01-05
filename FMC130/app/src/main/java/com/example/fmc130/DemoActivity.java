package com.example.fmc130;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class DemoActivity extends AppCompatActivity {
    KeyStore trustStore;
    InputStream input;
    TrustManagerFactory tmf;
    SSLContext sslCtx;
    String serverURI;
    MqttClient client;
    MqttConnectOptions options;

    private static final String output_commands = "864394041347925/commands";
    private static final String input_1_on = "{ \"CMD\": \"setdigout 100\"}";
    private static final String off = "{ \"CMD\": \"setdigout 000\"}";
    private static final String input_2_on = "{ \"CMD\": \"setdigout 010\"}";

    String ttden1, ttden2, ttden3,ttden4,ttden5;
    TextView title, local_remote, on_off_status;
    ImageView pump_image_view;
    Button start_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        title = findViewById(R.id.title);
        local_remote = findViewById(R.id.auto_manual);
        on_off_status = findViewById(R.id.onoffstatues);
        pump_image_view = findViewById(R.id.iamgeview);
        start_stop = findViewById(R.id.start_stop);

        mqtt();

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ttden3.equals("0"))
                {
                    if (ttden1.equals("1") && ttden2.equals("0"))
                    {

                        if (start_stop.getText().equals("START MOTOR"))
                        {
                            try {
                                JSONObject jsonObject = new JSONObject(input_1_on);
                                client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                        1, true);
                                Handler h = new Handler();
                                long delayedmillsecond = 100;
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONObject jsonObject = new JSONObject(off);
                                            client.publish("864394041347925/data", jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                                    1, true);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (MqttPersistenceException e) {
                                            e.printStackTrace();
                                        } catch (MqttException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, delayedmillsecond);
                            } catch (JSONException err) {
                                Log.d("Error", err.toString());
                            } catch (MqttPersistenceException e) {
                                e.printStackTrace();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
                if(ttden1.equals("1")&&ttden2.equals("1"))
                {
                    if(start_stop.getText().equals("STOP MOTOR")) {
                        try {
                            JSONObject jsonObject = new JSONObject(input_2_on);
                            client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                    1, true);
                            Handler h = new Handler();
                            long delayedmillsecond = 100;
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject(off);
                                        client.publish("864394041347925/data", jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                                1, true);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (MqttPersistenceException e) {
                                        e.printStackTrace();
                                    } catch (MqttException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },delayedmillsecond);

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        } catch (MqttPersistenceException e) {
                            e.printStackTrace();
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }


                    }
                }


                if(ttden4.equals("1"))
                {
                    Handler h = new Handler();
                    long delayedmillsecond = 100;
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(off);
                                client.publish("864394041347925/data", jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                        1, true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (MqttPersistenceException e) {
                                e.printStackTrace();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }

                        }
                    },delayedmillsecond);

                }
                if(ttden5.equals("1"))
                {
                    Handler h = new Handler();
                    long delayedmillsecond = 100;
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(off);
                                client.publish("864394041347925/data", jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                        1, true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (MqttPersistenceException e) {
                                e.printStackTrace();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }

                        }
                    },delayedmillsecond);

                }
                if(ttden3.equals("1"))
                {
                    (Toast.makeText(DemoActivity.this, "Please check Motor is Fault", Toast.LENGTH_LONG)).show();
                }
                if(ttden1.equals("0"))
                {
                    Toast.makeText(DemoActivity.this,"Turn ON Auto Mode",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mqtt();
    }

    public void mqtt() {
        try {
            trustStore = KeyStore.getInstance("BKS");
            input = getResources().openRawResource(R.raw.test);
            trustStore.load(input, null);
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(null, tmf.getTrustManagers(), null);
            serverURI = "ssl://210.18.156.221:8883";
            long theRandomNum = (long) (Math.random() * Math.pow(10, 10));
            client = new MqttClient(serverURI, String.valueOf(theRandomNum), new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setHttpsHostnameVerificationEnabled(false);
            options.setSocketFactory(sslCtx.getSocketFactory());
            client.connect(options);
            client.subscribe("864394041347925/data");
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                }
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.e("TAG", "Startup: " + message.toString());

                    JSONObject root = new JSONObject(message.toString());
                    if(root.has("state")==true)
                    {
                        JSONObject state1 = root.getJSONObject("state");
                        JSONObject reported1 = state1.getJSONObject("reported");
                        ttden1 = reported1.getString("1");
                        ttden2 = reported1.getString("2");
                        ttden3 = reported1.getString("3");
                        ttden4=reported1.getString("179");
                        ttden5=reported1.getString("180");
                        Log.e("messageArrived: ", ttden1);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (ttden1.equals("1")) {
                                    //runOnUiThread(() -> local_remote.setText("REMOTE"));
                                    local_remote.setText("REMOTE");
                                }
                                if (ttden1.equals("0")) {
                                    local_remote.setText("LOCAL");
                                    //runOnUiThread(() -> local_remote.setText("LOCAL"));
                                }
                                if (ttden2.equals("1") && ttden3.equals("0")) {

                                    pump_image_view.setBackgroundColor(getResources().getColor(R.color.green));
                                    on_off_status.setText("MOTOR ON");
                                    start_stop.setText("STOP MOTOR");

                                }
                                if (ttden2.equals("0") && ttden3.equals("0")) {

                                    pump_image_view.setBackgroundColor(getResources().getColor(R.color.red));
                                    on_off_status.setText("MOTOR OFF");
                                    start_stop.setText("START MOTOR");
                                }
                                if (ttden3.equals("1") && ttden2.equals("0")) {

                                    pump_image_view.setBackgroundColor(getResources().getColor(R.color.orange));
                                    on_off_status.setText("MOTOR FAULT");
                                }

                                if (local_remote.getText().toString().equals("LOCAL")) {
                                    start_stop.setVisibility(View.INVISIBLE);
                                }
                                if (local_remote.getText().toString().equals("REMOTE")) {
                                    start_stop.setVisibility(View.VISIBLE);
                                }
                            }
                        });


                    }

                }

                

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });


        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap changeImageBackgroundColor(Bitmap originalBitmap, int targetColor) {
        // Create a new bitmap with the same dimensions as the original bitmap
        Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a canvas object and associate it with the new bitmap
        Canvas canvas = new Canvas(newBitmap);

        // Create a paint object and set the desired target color
        Paint paint = new Paint();
        paint.setColor(targetColor);

        // Draw the target color on the canvas
        canvas.drawRect(0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), paint);

        // Draw the original bitmap on top of the target color
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        // Return the new bitmap with the changed background color
        return newBitmap;
    }
}