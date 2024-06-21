package safisoft.backangel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class ConnectingBackAngelDeviceActivity extends AppCompatActivity {


    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;
    public final static int REQUEST_ENABLE_BT = 1;
    CountDownTimer countDownTimer;
    String BLUETOOTH_NAME;
    String BLUETOOTH_MAC;
    TextView txtv_username , textview_btn_connect ;
    boolean found ;

    LottieAnimationView lott_connect_state ;

    ImageButton btn_Connecting , btn_demo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting_back_angel_device);

        SharedPreferences getData = getSharedPreferences("Angel_Pro_Data", MODE_PRIVATE);
        String userName =getData.getString("User_Name","");

        txtv_username = findViewById(R.id.txtv_username);
        textview_btn_connect = findViewById(R.id.textview_btn_connect);
        lott_connect_state = findViewById(R.id.lott_connect_state);
        btn_Connecting = findViewById(R.id.btn_Connecting);
        btn_demo = findViewById(R.id.btn_demo);



        txtv_username.setText("Hi "+userName);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //if bluetooth is not enabled then create Intent for user to turn it on
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);

        } else {
            mBluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);
        }



        countDownTimer = new CountDownTimer(8000, 1000
        ) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), ConnectingBackAngelDeviceActivity.this, "Make Sure Your Device Is In Pairing Mode To Connect.");
                textview_btn_connect.setText("Try again");
                lott_connect_state.setAnimation(R.raw.ic_connection_err);
                lott_connect_state.playAnimation();

            }
        }.start();


        btn_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_move = new Intent(ConnectingBackAngelDeviceActivity.this, MainControlActivity.class);
                intent_move.putExtra("DEMO", "yes");
                startActivity(intent_move);
                finish();
            }
        });




        btn_Connecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textview_btn_connect.getText().equals("Connected")){

                    Intent intent_move = new Intent(ConnectingBackAngelDeviceActivity.this, MainControlActivity.class);
                    intent_move.putExtra("BLUETOOTH_NAME", BLUETOOTH_NAME);
                    intent_move.putExtra("BLUETOOTH_MAC", BLUETOOTH_MAC);
                    intent_move.putExtra("DEMO", "no");
                    startActivity(intent_move);
                    finish();

                }
                else {

                    try {

                        countDownTimer.cancel();
                        mBluetoothAdapter.startDiscovery();
                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(mReceiver, filter);

                        textview_btn_connect.setText("Connecting ...");
                        lott_connect_state.setAnimation(R.raw.ic_blue_dot);
                        lott_connect_state.playAnimation();
                        countDownTimer.start();
                    } catch (Exception e) {

                        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                        snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), ConnectingBackAngelDeviceActivity.this, "Make Sure Your Device Is In Pairing Mode To Connect.");
                        textview_btn_connect.setText("Try again");
                        lott_connect_state.setAnimation(R.raw.ic_connection_err);
                        lott_connect_state.playAnimation();

                    }
                }
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                mBluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mReceiver, filter);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult

    @Override
    protected void onDestroy() {
        //   unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (ActivityCompat.checkSelfPermission(ConnectingBackAngelDeviceActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                try {
                    if (device.getName().equals("Back Angel") && device.getAddress().equals("C0:49:EF:E6:27:D6")) {
                        countDownTimer.cancel();
                        BLUETOOTH_NAME = device.getName();
                        BLUETOOTH_MAC = device.getAddress();

                        SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                        snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), ConnectingBackAngelDeviceActivity.this, "Back Angel Pro Device Connected Successfully");
                        textview_btn_connect.setText("Connected");
                        lott_connect_state.setAnimation(R.raw.ic_connected_done);
                        lott_connect_state.playAnimation();


                        new CountDownTimer(2000, 1000) { //4000,1000
                            public void onTick(long millisUntilFinished) { }
                            public void onFinish() {
                                Intent intent_move = new Intent(ConnectingBackAngelDeviceActivity.this, MainControlActivity.class);
                                intent_move.putExtra("BLUETOOTH_NAME", BLUETOOTH_NAME);
                                intent_move.putExtra("BLUETOOTH_MAC", BLUETOOTH_MAC);
                                intent_move.putExtra("DEMO", "no");
                                startActivity(intent_move);
                                finish();
                            }
                        }.start();


                    }

                }catch (Exception e){

                }

            }
        }
    };

    @Override
    public void onBackPressed() {
    //    Intent intent = new Intent(FindBluetoothActivity.this, NewProjectChooseActivity.class);
    //    startActivity(intent);
     //   finish();
    }







}