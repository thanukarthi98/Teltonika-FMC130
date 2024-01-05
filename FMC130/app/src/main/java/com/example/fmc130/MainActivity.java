package com.example.fmc130;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = ;
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

    String ttden1,ttden2,ttden3;
    Button btn_start,btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.start);
        btn_stop = findViewById(R.id.stop);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: hiiiii");
                try {
                    trustStore = KeyStore.getInstance("BKS");
                    input = getResources().openRawResource(R.raw.test);
                    trustStore.load(input, null);

                    tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init(trustStore);


                    sslCtx = SSLContext.getInstance("TLS");
                    sslCtx.init(null, tmf.getTrustManagers(), null);


                    serverURI = "ssl://210.18.156.221:8883";

                    client = new MqttClient(serverURI, "DroidClientExample", new MemoryPersistence());

                    options = new MqttConnectOptions();

                    options.setHttpsHostnameVerificationEnabled(false);

// Pass the SSL context we previously configured
                    options.setSocketFactory(sslCtx.getSocketFactory());
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.d("TAG", "start output 1: "+message.toString());
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }


                    });

                    client.connect(options);

                    client.subscribe("864394041347925/data");

                    JSONObject jsonObject = new JSONObject(input_1_on);

                    //MqttMessage msg = new MqttMessage("Android SSL Message".getBytes());
                    try {
                        client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                1, true);
                        //client.publish("864394041347925/data", msg);
                        Handler h = new Handler();
                        long delayedmillsecond = 1000;
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(off);
                                    client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
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
                    catch (MqttException e) {
                        e.printStackTrace();
                    }

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: heloooo");
                try {
                    trustStore = KeyStore.getInstance("BKS");
                    input = getResources().openRawResource(R.raw.test);
                    trustStore.load(input, null);

                    tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init(trustStore);


                    sslCtx = SSLContext.getInstance("TLS");
                    sslCtx.init(null, tmf.getTrustManagers(), null);


                    serverURI = "ssl://210.18.156.221:8883";

                    client = new MqttClient(serverURI, "DroidClientExample", new MemoryPersistence());

                    options = new MqttConnectOptions();

                    options.setHttpsHostnameVerificationEnabled(false);

// Pass the SSL context we previously configured
                    options.setSocketFactory(sslCtx.getSocketFactory());
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.d("TAG", "stop output2: "+message.toString());
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });

                    client.connect(options);

                    client.subscribe("864394041347925/data");

                    JSONObject jsonObject = new JSONObject(input_2_on);
                    // MqttMessage msg = new MqttMessage("Android SSL Message".getBytes());
                    try {
                        client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                1, true);
                        Handler h = new Handler();
                        long delayedmillsecond = 1000;
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(off);
                                    client.publish(output_commands, jsonObject.toString().getBytes(StandardCharsets.UTF_8),
                                            1, true);
                                } catch (MqttPersistenceException e) {
                                    e.printStackTrace();
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },delayedmillsecond);
                        // client.publish("864394041347925/data", msg);
                    }
                    catch (MqttException e) {
                        e.printStackTrace();
                    }

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}