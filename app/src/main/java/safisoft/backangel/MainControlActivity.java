package safisoft.backangel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.nio.charset.StandardCharsets;

public class MainControlActivity extends AppCompatActivity {


    String BLUETOOTH_NAME;
    String MODULE_MAC;

    String DEMO ;



    public Handler mHandler;
    private final int STATUS_CHECK_INTERVAL = 500;
    private final Handler handlerStatusCheck = new Handler();

    ImageView imgv_connect_led , imgv_charging , imgv_vib_led , imgv_buzz_led;


    boolean State_Zero = true;
    boolean State_One = true;
    boolean State_Tow = true;
    boolean First_lunch_Zero = true;
    boolean First_lunch_One = true;
    boolean First_lunch_Tow = true;

    TextView txtv_connecting_lable , textv_username ;
    LinearLayout btn_power ;

    ImageButton btn_reset_posture , btn_pause_tilt , btn_pause_shoulder , btn_pause_back ;

    TextView textv_battery_level , textv_temp_level , textv_tlt_r_l , textv_tlt_back , textv_tlt_shoulder;
    LottieAnimationView lott_battery_level , lott_temp_level , lott_tlt_r_l , lott_tlt_back , lott_tlt_shoulder ;
    LottieAnimationView lott_hint_back , lott_hint_shoulder , lott_hint_tilt ;

    LottieAnimationView lott_tlt_back_zzz , lott_tlt_shoulder_zzz , lott_tlt_r_l_zzz ;

    SharedPreferences getData ;
    SharedPreferences.Editor myData ;

    ImageButton btn_handel_vibration , btn_handel_buzzer , btn_delay , btn_sensitivity ;
    TextView textv_delay , textv_sensitivity ;

    LottieAnimationView arr_1 , arr_5 , arr_6 , arr_7 , arr_8 , arr_9 , arr_10 , arr_11 ;
    LinearLayout arr_2 , arr_3 , arr_4 , lay_arr ;

    RelativeLayout lay_demo_down , lay_demo_up  ;
    ImageButton btn_next_lay_demo_down , btn_next_lay_demo_up ;
    TextView textv_demo_head_down, textv_demo_body_down , textv_demo_head_up , textv_demo_body_up;

    int DemoCount = 0 ;

    String userName ;


    String Run_State ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);


        BLUETOOTH_NAME = getIntent().getStringExtra("BLUETOOTH_NAME");
        MODULE_MAC = getIntent().getStringExtra("BLUETOOTH_MAC");
        DEMO = getIntent().getStringExtra("DEMO");

        initiateBluetoothProcess();


        getData = getSharedPreferences("Angel_Pro_Data", MODE_PRIVATE);
        userName =getData.getString("User_Name","");




        SharedPreferences sharedPreferences = getSharedPreferences("Angel_Pro_Data", MODE_PRIVATE);
        myData = sharedPreferences.edit();

        if(getData.getString("pause_play_back_state","").equals("z") || getData.getString("pause_play_shoulder_state","").equals("x") ||
                getData.getString("pause_play_tilt_state","").equals("c") || getData.getString("pause_play_buzzer","").equals("u") ||
                getData.getString("pause_play_vibration","").equals("v")){
            // Toast.makeText(this, "first time apply", Toast.LENGTH_SHORT).show();
           
        }
        else {
            //  Toast.makeText(this, "change", Toast.LENGTH_SHORT).show();
            myData.putString("pause_play_back_state", "Z");
            myData.putString("pause_play_shoulder_state", "X");
            myData.putString("pause_play_tilt_state", "C");
            myData.putString("pause_play_buzzer", "U");
            myData.putString("pause_play_vibration", "V");
            myData.apply();
        }


        imgv_connect_led =findViewById(R.id.imgv_connect_led);
        txtv_connecting_lable = findViewById(R.id.txtv_connecting_lable);
        textv_username = findViewById(R.id.textv_username);
        btn_power = findViewById(R.id.btn_power);
        btn_reset_posture = findViewById(R.id.btn_reset_posture);
        textv_battery_level = findViewById(R.id.textv_battery_level);
        lott_battery_level = findViewById(R.id.lott_battery_level);
        textv_temp_level =findViewById(R.id.textv_temp_level);
        lott_temp_level = findViewById(R.id.lott_temp_level);
        lott_tlt_r_l = findViewById(R.id.lott_tlt_r_l);
        textv_tlt_r_l = findViewById(R.id.textv_tlt_r_l);
        lott_tlt_back =findViewById(R.id.lott_tlt_back);
        textv_tlt_back =findViewById(R.id.textv_tlt_back);
        imgv_charging = findViewById(R.id.imgv_charging);
        textv_tlt_shoulder = findViewById(R.id.textv_tlt_shoulder);
        lott_tlt_shoulder = findViewById(R.id.lott_tlt_shoulder);
        lott_hint_back = findViewById(R.id.lott_hint_back);
        lott_hint_shoulder = findViewById(R.id.lott_hint_shoulder);
        lott_hint_tilt = findViewById(R.id.lott_hint_tilt);
        btn_pause_tilt = findViewById(R.id.btn_pause_tilt);
        btn_pause_shoulder = findViewById(R.id.btn_pause_shoulder);
        btn_pause_back = findViewById(R.id.btn_pause_back);
        lott_tlt_back_zzz = findViewById(R.id.lott_tlt_back_zzz);
        lott_tlt_shoulder_zzz = findViewById(R.id.lott_tlt_shoulder_zzz);
        lott_tlt_r_l_zzz = findViewById(R.id.lott_tlt_r_l_zzz);
        btn_handel_buzzer = findViewById(R.id.btn_handel_buzzer);
        btn_handel_vibration = findViewById(R.id.btn_handel_vibration);
        imgv_buzz_led = findViewById(R.id.imgv_buzz_led);
        imgv_vib_led = findViewById(R.id.imgv_vib_led);
        textv_delay = findViewById(R.id.textv_delay);
        textv_sensitivity = findViewById(R.id.textv_sensitivity);
        btn_delay = findViewById(R.id.btn_delay);
        btn_sensitivity = findViewById(R.id.btn_sensitivity);
        arr_1 = findViewById(R.id.arr_1);
        arr_2 = findViewById(R.id.arr_2);
        arr_3 = findViewById(R.id.arr_3);
        arr_4 = findViewById(R.id.arr_4);
        arr_5 = findViewById(R.id.arr_5);
        arr_6 = findViewById(R.id.arr_6);
        arr_7 = findViewById(R.id.arr_7);
        arr_8 = findViewById(R.id.arr_8);
        arr_9 = findViewById(R.id.arr_9);
        arr_10 = findViewById(R.id.arr_10);
        arr_11 = findViewById(R.id.arr_11);
        lay_demo_down = findViewById(R.id.lay_demo_down);
        btn_next_lay_demo_down = findViewById(R.id.btn_next_lay_demo_down);
        textv_demo_head_down = findViewById(R.id.textv_demo_head_down);
        textv_demo_body_down = findViewById(R.id.textv_demo_body_down);

        lay_demo_up = findViewById(R.id.lay_demo_up);
        btn_next_lay_demo_up = findViewById(R.id.btn_next_lay_demo_up);
        textv_demo_head_up = findViewById(R.id.textv_demo_head_up);
        textv_demo_body_up = findViewById(R.id.textv_demo_body_up);

        lay_arr =findViewById(R.id.lay_arr);



        textv_username.setText("Hi "+userName);





        if(DEMO.equals("yes")) {

            lay_demo_down.setVisibility(View.VISIBLE);
            lay_arr.setVisibility(View.VISIBLE);
            Demo(DemoCount);
            lott_battery_level.setFrame(220);
            lott_temp_level.setFrame(50);
        }

        Run_State = getData.getString("First_Run","");

        if(DEMO.equals("no") && Run_State.equals("Run1") ) {

            lay_demo_down.setVisibility(View.VISIBLE);
            lay_arr.setVisibility(View.VISIBLE);
            Demo(DemoCount);
            lott_battery_level.setFrame(220);
            lott_temp_level.setFrame(50);

            myData.putString("First_Run","Run2");
            myData.apply();

            DEMO = "im_demo" ;

        }




        btn_next_lay_demo_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoCount ++ ;
                Demo(DemoCount);

            }
        });

        btn_next_lay_demo_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoCount ++ ;
                Demo(DemoCount);

            }
        });



        btn_pause_tilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pause_play_state =getData.getString("pause_play_tilt_state","");
                if(pause_play_state.equals("C")) {
                    SEND_COMMAND("c");
                }
                else if(pause_play_state.equals("c")) {
                    SEND_COMMAND("C");
                }
            }
        });

        btn_pause_shoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pause_play_state =getData.getString("pause_play_shoulder_state","");
                if(pause_play_state.equals("X")) {
                    SEND_COMMAND("x");
                }
                else if(pause_play_state.equals("x")) {
                    SEND_COMMAND("X");
                }
            }
        });

        btn_pause_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pause_play_state =getData.getString("pause_play_back_state","");
                if(pause_play_state.equals("Z")) {
                    SEND_COMMAND("z");
                }
                else if(pause_play_state.equals("z")) {
                    SEND_COMMAND("Z");
                }
            }
        });

        btn_handel_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pause_play_vibration =getData.getString("pause_play_vibration","");
                if(pause_play_vibration.equals("V")) {
                    SEND_COMMAND("v");
                }
                else if(pause_play_vibration.equals("v")) {
                    SEND_COMMAND("V");
                }
            }
        });

        btn_handel_buzzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pause_play_buzzer =getData.getString("pause_play_buzzer","");
                if(pause_play_buzzer.equals("U")) {
                    SEND_COMMAND("u");
                }
                else if(pause_play_buzzer.equals("u")) {
                    SEND_COMMAND("U");
                }
            }
        });


        btn_reset_posture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SEND_COMMAND("S");
                SEND_COMMAND(getData.getString("pause_play_back_state",""));
                SEND_COMMAND(getData.getString("pause_play_shoulder_state",""));
                SEND_COMMAND(getData.getString("pause_play_tilt_state",""));
                SEND_COMMAND(getData.getString("pause_play_buzzer",""));
                SEND_COMMAND(getData.getString("pause_play_vibration",""));
                SEND_COMMAND(getData.getString("delay_amount",""));
                SEND_COMMAND(getData.getString("sensitivity_amount",""));

                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this,"Your Posture Has Ben ReSet");
            }
        });


        btn_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SEND_COMMAND("P");

                AppCloseDialog appCloseDialog  = new AppCloseDialog(MainControlActivity.this);
                appCloseDialog.show();
                appCloseDialog.setCanceledOnTouchOutside(false);
                appCloseDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                appCloseDialog.btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                    }
                });

                appCloseDialog.btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        appCloseDialog.dismiss();
                    }
                });




            }
        });


        btn_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delay_amount =getData.getString("delay_amount","");

                if(delay_amount.equals("0")) {
                    SEND_COMMAND("4");
                }
                else if(delay_amount.equals("4")) {
                    SEND_COMMAND("6");
                }
                else if(delay_amount.equals("6")) {
                    SEND_COMMAND("8");
                }
                else if(delay_amount.equals("8")) {
                    SEND_COMMAND("0");
                }

            }
        });


        btn_sensitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sensitivity_amount =getData.getString("sensitivity_amount","");

                if(sensitivity_amount.equals("1")) {
                    SEND_COMMAND("2");
                }
                else if(sensitivity_amount.equals("2")) {
                    SEND_COMMAND("3");
                }
                else if(sensitivity_amount.equals("3")) {
                    SEND_COMMAND("1");
                }

            }
        });







    }





    public void initiateBluetoothProcess(){
        ((ApplicationEx)getApplication()).mBtEngine.SET_MAC(MODULE_MAC); //back angel
        ((ApplicationEx)getApplication()).Start_Stop_Manual_control(0);

        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String txt = (String)msg.obj;

                StringBuilder sb = new StringBuilder();
                sb.append(txt);                                      // append string
                String sbprint = sb.substring(0, sb.length());            // extract string
                sb.delete(0, sb.length());
                final String finalSbprint = sb.append(sbprint).toString();

                if(!DEMO.equals("im_demo")) {
                    GET_VAL_FROM_DTRING(finalSbprint);
                }




            }
        };
        ((ApplicationEx)getApplication()).mBtEngine.SET_HANDLER(mHandler);


        handlerStatusCheck.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(((ApplicationEx)getApplication()).mBtEngine.getState() == 0 ){
                    if(State_Zero) {
                        txtv_connecting_lable.setText("Connection Lost");
                        imgv_connect_led.setBackgroundResource(R.drawable.ic_red_dot_not_connected);
                        if(!First_lunch_Zero) {
                            //    SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                            //   snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this, "Connection Lost");
                        }
                        State_Zero = false ;
                        State_One = true ;
                        State_Tow = true ;
                        First_lunch_Zero = false ;
                    }
                }
                if(((ApplicationEx)getApplication()).mBtEngine.getState() == 1 ){
                    if(State_One) {
                        txtv_connecting_lable.setText("Trying to Connect");
                        imgv_connect_led.setBackgroundResource(R.drawable.ic_red_dot_not_connected);
                        if(!First_lunch_One) {
                            //      SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                            //      snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this, "Trying to Connect");
                        }
                        State_Zero = true ;
                        State_One = false ;
                        State_Tow = true ;
                        First_lunch_One = false ;
                    }
                }
                if(((ApplicationEx)getApplication()).mBtEngine.getState() == 2 ){
                    if(State_Tow) {
                        txtv_connecting_lable.setText("Connected");
                        imgv_connect_led.setBackgroundResource(R.drawable.ic_green_dot_connected);
                        if(!First_lunch_Tow) {
                            //   SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                            //   snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this, "Connected");
                        }



                        State_Zero = true ;
                        State_One = true ;
                        State_Tow = false ;
                        First_lunch_Tow = false ;
                    }
                }
                handlerStatusCheck.postDelayed(this, STATUS_CHECK_INTERVAL);
            }
        }, STATUS_CHECK_INTERVAL);

    }


    private void EndConnection() {
        ((ApplicationEx)getApplication()).Start_Stop_Manual_control(1);
        handlerStatusCheck.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
    }

    private void SEND_COMMAND(String Commend){

        ((ApplicationEx)getApplication()).Start_Stop_Manual_control(0);
        if(((ApplicationEx)getApplication()).writeBt(Commend.getBytes(StandardCharsets.UTF_8))){
            //      txtv_command_history.append( "> "+Commend+"\n");
        }
        else {
            SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
            snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this,"Something Went Wrong");
        }

    }

    String prev = null;
    boolean isHintShoulder = true ;
    boolean isHintBack = true ;
    boolean isHintTilt = true ;

    boolean ShowSetPostureDialog = true ;




    private void GET_VAL_FROM_DTRING(String String) {
        String[] Final_Val;
        try {
            String[] msgs = String.split("\n");
            int msgsCount = msgs.length;
            if (msgsCount > 0) {
                if (prev != null)
                    msgs[0] = prev + msgs[0]; //add it to first message
                if (String.endsWith("\n"))
                    prev = null;
                else {
                    prev = msgs[msgsCount - 1];
                    msgsCount--; }
                for (int i = 0; i < msgsCount; i++) {


                    Final_Val = msgs[i].split(",");

                    SHOW_DATA(Final_Val[0], lott_battery_level, textv_battery_level);
                    SHOW_DATA(Final_Val[1], lott_temp_level, textv_temp_level);
                    SHOW_DATA(Final_Val[2], lott_tlt_r_l, textv_tlt_r_l);
                    SHOW_DATA(Final_Val[3], lott_tlt_back, textv_tlt_back);
                    SHOW_DATA(Final_Val[4], lott_tlt_shoulder, textv_tlt_shoulder);


                    //////////////// Shoulder
                    if(!Final_Val[5].isEmpty() && Final_Val[5].charAt(0) == 'E' && isHintShoulder){
                        lott_hint_shoulder.setAnimation(R.raw.ic_red_dot);
                        lott_hint_shoulder.playAnimation();
                        lott_hint_shoulder.setSpeed(3);
                        isHintShoulder = false ;
                    }else if(!Final_Val[5].isEmpty() && Final_Val[5].charAt(0) == 'e' && !isHintShoulder) {
                        lott_hint_shoulder.setAnimation(R.raw.ic_blue_dot);
                        lott_hint_shoulder.playAnimation();
                        lott_hint_shoulder.setSpeed(1);
                        isHintShoulder = true ;
                    }

                    ////////////////// Back
                    if(!Final_Val[6].isEmpty() && Final_Val[6].charAt(0) == 'B' && isHintBack){
                        lott_hint_back.setAnimation(R.raw.ic_red_dot);
                        lott_hint_back.playAnimation();
                        lott_hint_back.setSpeed(3);
                        isHintBack = false ;
                    }else if(!Final_Val[6].isEmpty() && Final_Val[6].charAt(0) == 'b' && !isHintBack) {
                        lott_hint_back.setAnimation(R.raw.ic_blue_dot);
                        lott_hint_back.playAnimation();
                        lott_hint_back.setSpeed(1);
                        isHintBack = true ;
                    }

                    ////////////////// Tilt
                    if(!Final_Val[7].isEmpty() && Final_Val[7].charAt(0) == 'T' && isHintTilt){
                        lott_hint_tilt.setAnimation(R.raw.ic_red_dot);
                        lott_hint_tilt.playAnimation();
                        lott_hint_tilt.setSpeed(3);
                        isHintTilt = false ;
                    }else if(!Final_Val[7].isEmpty() && Final_Val[7].charAt(0) == 't' && !isHintTilt) {
                        lott_hint_tilt.setAnimation(R.raw.ic_blue_dot);
                        lott_hint_tilt.playAnimation();
                        lott_hint_tilt.setSpeed(1);
                        isHintTilt = true ;
                    }

                    /////////////////// pause_play_back_state
                    if(Final_Val[8].equals("Z")){
                        myData.putString("pause_play_back_state","Z");
                        myData.apply();
                        lott_hint_back.setVisibility(View.VISIBLE);
                        lott_tlt_back_zzz.setVisibility(View.INVISIBLE);
                        btn_pause_back.setBackgroundResource(R.drawable.ic_pause);

                    }else if(Final_Val[8].equals("z") ){
                        myData.putString("pause_play_back_state","z");
                        myData.apply();
                        textv_tlt_back.setText("Sleep");
                        lott_hint_back.setVisibility(View.INVISIBLE);
                        lott_tlt_back_zzz.setVisibility(View.VISIBLE);
                        btn_pause_back.setBackgroundResource(R.drawable.ic_play);
                        lott_tlt_back.setFrame(32);
                    }

                    /////////////////// pause_play_shoulder_state
                    if(Final_Val[9].equals("X") ){
                        myData.putString("pause_play_shoulder_state","X");
                        myData.apply();
                        lott_hint_shoulder.setVisibility(View.VISIBLE);
                        lott_tlt_shoulder_zzz.setVisibility(View.INVISIBLE);
                        btn_pause_shoulder.setBackgroundResource(R.drawable.ic_pause);

                    }else if(Final_Val[9].equals("x") ){
                        myData.putString("pause_play_shoulder_state","x");
                        myData.apply();
                        textv_tlt_shoulder.setText("Sleep");
                        lott_hint_shoulder.setVisibility(View.INVISIBLE);
                        lott_tlt_shoulder_zzz.setVisibility(View.VISIBLE);
                        btn_pause_shoulder.setBackgroundResource(R.drawable.ic_play);
                        lott_tlt_shoulder.setFrame(53);
                    }

                    /////////////////// pause_play_tilt_state
                    if(Final_Val[10].equals("C") ){
                        myData.putString("pause_play_tilt_state","C");
                        myData.apply();
                        lott_hint_tilt.setVisibility(View.VISIBLE);
                        lott_tlt_r_l_zzz.setVisibility(View.INVISIBLE);
                        btn_pause_tilt.setBackgroundResource(R.drawable.ic_pause);
                        ShowSetPostureDialog = true ;

                    }else if(Final_Val[10].equals("c") ){
                        myData.putString("pause_play_tilt_state","c");
                        myData.apply();
                        textv_tlt_r_l.setText("Sleep");
                        lott_hint_tilt.setVisibility(View.INVISIBLE);
                        lott_tlt_r_l_zzz.setVisibility(View.VISIBLE);
                        btn_pause_tilt.setBackgroundResource(R.drawable.ic_play);
                        lott_tlt_r_l.setFrame(22);
                        ShowSetPostureDialog = true ;
                    }

                    ///////////////////////////pause_play_vibration
                    if(Final_Val[11].equals("V")){
                        myData.putString("pause_play_vibration","V");
                        myData.apply();
                        imgv_vib_led.setBackgroundResource(R.drawable.ic_green_dot_connected);
                    }else if(Final_Val[11].equals("v")){
                        myData.putString("pause_play_vibration","v");
                        myData.apply();
                        imgv_vib_led.setBackgroundResource(R.drawable.ic_red_dot_not_connected);
                    }

                    //////////////////////////pause_play_buzzer
                    if(Final_Val[12].equals("U")){
                        myData.putString("pause_play_buzzer","U");
                        myData.apply();
                        imgv_buzz_led.setBackgroundResource(R.drawable.ic_green_dot_connected);
                    }else if(Final_Val[12].equals("u")){
                        myData.putString("pause_play_buzzer","u");
                        myData.apply();
                        imgv_buzz_led.setBackgroundResource(R.drawable.ic_red_dot_not_connected);
                    }


                    //////////////////////////delay_amount
                    if(Final_Val[13].equals("0")){
                        myData.putString("delay_amount","0");
                        myData.apply();
                        textv_delay.setText("0s");
                    }else if(Final_Val[13].equals("4")){
                        myData.putString("delay_amount","4");
                        myData.apply();
                        textv_delay.setText("2s");
                    }else if(Final_Val[13].equals("6")){
                        myData.putString("delay_amount","6");
                        myData.apply();
                        textv_delay.setText("3s");
                    }else if(Final_Val[13].equals("8")){
                        myData.putString("delay_amount","8");
                        myData.apply();
                        textv_delay.setText("4s");
                    }


                    //////////////////////////sensitivity_amount
                    if(Final_Val[14].equals("1")){
                        myData.putString("sensitivity_amount","1");
                        myData.apply();
                        textv_sensitivity.setText("1x");
                    }else if(Final_Val[14].equals("2")){
                        myData.putString("sensitivity_amount","2");
                        myData.apply();
                        textv_sensitivity.setText("2x");
                    }else if(Final_Val[14].equals("3")){
                        myData.putString("sensitivity_amount","3");
                        myData.apply();
                        textv_sensitivity.setText("3x");
                    }



                    ///////////////////////////
                    if(Final_Val[10].charAt(0) == 'M' && ShowSetPostureDialog){

                        SetPostureDialog setPostureDialog = new SetPostureDialog(MainControlActivity.this);
                        setPostureDialog.show();
                        setPostureDialog.setCanceledOnTouchOutside(false);
                        setPostureDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        setPostureDialog.btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SEND_COMMAND("S");
                                SEND_COMMAND(getData.getString("pause_play_back_state",""));
                                SEND_COMMAND(getData.getString("pause_play_shoulder_state",""));
                                SEND_COMMAND(getData.getString("pause_play_tilt_state",""));
                                SEND_COMMAND(getData.getString("pause_play_buzzer",""));
                                SEND_COMMAND(getData.getString("pause_play_vibration",""));
                                SEND_COMMAND(getData.getString("delay_amount",""));
                                SEND_COMMAND(getData.getString("sensitivity_amount",""));



                                setPostureDialog.dismiss();
                                SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                                snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), MainControlActivity.this,"Your Posture Has Ben ReSet");
                            }
                        });
                        setPostureDialog.textv_username_dialog.setText("Hi "+userName);
                        ShowSetPostureDialog = false ;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean isCharging = true ;
    private void SHOW_DATA(String State, LottieAnimationView lottieAnimationView, TextView textView) {

        //  textView.setText("");
        float State_float = Float.parseFloat(State.trim());
        int State_Int = (int) State_float;

        /////////////////////////////////////////////////////////// Battery Level
        if (textView.getId() == textv_battery_level.getId()) {
            if(State_Int == 100 && isCharging){
                lottieAnimationView.playAnimation();
                lottieAnimationView.loop(true);
                imgv_charging.setVisibility(View.VISIBLE);
                textv_battery_level.setText("");
                isCharging = false ;
            }
            else if (State_Int < 100){
                textView.setText(Math.abs(State_Int) + "%");
                State_Int = (int) (State_Int * 4.2);
                lottieAnimationView.loop(false);
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setFrame(Math.abs(State_Int));
                imgv_charging.setVisibility(View.INVISIBLE);
                isCharging = true ;
            }
        }
        ///////////////////////////////////////////////////////////////    temp
        if(textView.getId() == textv_temp_level.getId()){
            textView.setText(Math.abs(State_Int) + "℃");
            State_Int = (int) (State_Int * 0.6 );
            lottieAnimationView.setFrame(Math.abs(State_Int)+38);
        }
        /////////////////////////////////////////////////////////////// tilt r_l

        if (textView.getId() == textv_tlt_r_l.getId()) {
            if (State_Int <= 5 && State_Int >= -5) {
                textView.setText(Math.abs(State_float) + "°");
                State_Int = (int) (State_Int * 4.4);
                if (State_Int <= 0) {
                    lottieAnimationView.setFrame(Math.abs(State_Int));
                }
                if (State_Int >= 0) {
                    lottieAnimationView.setFrame(Math.abs(State_Int) + 23);
                }
            }

        }
        /////////////////////////////////////////////////////////////// tilt back
        if (textView.getId() == textv_tlt_back.getId()) {
            textView.setText(Math.abs(State_float) + "°");
            State_Int = (int) (State_float * 20);
            lottieAnimationView.setFrame(Math.abs(State_Int));
        }

        /////////////////////////////////////////////////////////////// tilt shoulder

        if (textView.getId() == textv_tlt_shoulder.getId()) {
            String partOfNumber = State.substring(1, 4);
            int State_Int_half = Integer.parseInt(partOfNumber);
            State_Int_half = State_Int_half / 4;
            State_Int = (int) (State_Int_half * 3.7037037037);
            State_Int = 600 - State_Int;
            textView.setText(Math.abs(State_Int) + "°");
            lottieAnimationView.setFrame(State_Int + 48);
        }



    }

    private void Demo(int Step){

        switch(Step) {
            case 0:

                textv_demo_head_down.setText("Power Button and Connection Led");
                textv_demo_body_down.setText("The power button turns off the device and terminates the connection, and the LED indicates the connection status: green for connected and red for disconnected.");
                arr_1.setVisibility(View.VISIBLE);

                break;

            case 1:
                Hide_arr();
                textv_demo_head_down.setText("Set Your Posture");
                textv_demo_body_down.setText("The next step is to wear the device on your back and perform the collaboration steps.");
                break;

            case 2:
                lay_demo_down.setVisibility(View.GONE);
                SetPostureDialog setPostureDialog = new SetPostureDialog(MainControlActivity.this);
                setPostureDialog.show();
                setPostureDialog.setCanceledOnTouchOutside(false);
                setPostureDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                setPostureDialog.btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                 //       SEND_COMMAND("S");
                 //       SEND_COMMAND(getData.getString("pause_play_back_state",""));
                 //       SEND_COMMAND(getData.getString("pause_play_shoulder_state",""));
                 //       SEND_COMMAND(getData.getString("pause_play_tilt_state",""));
                 //       SEND_COMMAND(getData.getString("pause_play_buzzer",""));
                 //       SEND_COMMAND(getData.getString("pause_play_vibration",""));
                 //       SEND_COMMAND(getData.getString("delay_amount",""));
                 //       SEND_COMMAND(getData.getString("sensitivity_amount",""));



                        setPostureDialog.dismiss();
                        lay_demo_down.setVisibility(View.VISIBLE);
                        arr_2.setVisibility(View.VISIBLE);
                        lott_tlt_r_l.playAnimation();
                        lott_tlt_r_l.loop(true);
                        lott_tlt_r_l.setSpeed(0.5F);
                        lott_tlt_shoulder.playAnimation();
                        lott_tlt_shoulder.loop(true);
                        lott_tlt_shoulder.setSpeed(1);
                        lott_tlt_back.playAnimation();
                        lott_tlt_back.loop(true);
                        lott_tlt_back.setSpeed(0.5F);
                        textv_demo_head_down.setText("The Little Human Figures");
                        textv_demo_body_down.setText("They let you to track your body's actual movements in real time, such as tilting to the side and bending the back and shoulders.");

                    }
                });
                setPostureDialog.textv_username_dialog.setText("Hi "+userName);
                break;


            case 3:
                Hide_arr();
                arr_3.setVisibility(View.VISIBLE);
                lott_tlt_r_l.pauseAnimation();
                lott_tlt_shoulder.pauseAnimation();
                lott_tlt_back.pauseAnimation();
                lott_tlt_r_l.setFrame(22);
                lott_tlt_shoulder.setFrame(50);
                lott_tlt_back.setFrame(1);

                lott_hint_back.setAnimation(R.raw.ic_blue_dot);
                lott_hint_back.playAnimation();
                lott_hint_back.setSpeed(1);

                lott_hint_tilt.setAnimation(R.raw.ic_red_dot);
                lott_hint_tilt.playAnimation();
                lott_hint_tilt.setSpeed(3);

                lott_hint_shoulder.setAnimation(R.raw.ic_red_dot);
                lott_hint_shoulder.playAnimation();
                lott_hint_shoulder.setSpeed(3);

                textv_demo_head_down.setText("Movement angle and alert flashes");
                textv_demo_body_down.setText("When the body goes beyond the specified angle of movement, a red alert shows behind the bend angle data.");
                break;


            case 4:
                Hide_arr();
                arr_4.setVisibility(View.VISIBLE);
                lott_tlt_r_l.pauseAnimation();
                lott_tlt_shoulder.pauseAnimation();
                lott_tlt_back.pauseAnimation();

                lott_tlt_r_l.setFrame(23);
                lott_tlt_shoulder.setFrame(50);
                lott_tlt_back.setFrame(32);

                lott_tlt_shoulder_zzz.setVisibility(View.VISIBLE);
                lott_tlt_back_zzz.setVisibility(View.VISIBLE);

                btn_pause_shoulder.setBackgroundResource(R.drawable.ic_play);
                btn_pause_back.setBackgroundResource(R.drawable.ic_play);



                lott_hint_back.setAnimation(R.raw.ic_blue_dot);
                lott_hint_back.playAnimation();
                lott_hint_back.setSpeed(1);

                lott_hint_tilt.setAnimation(R.raw.ic_blue_dot);
                lott_hint_tilt.playAnimation();
                lott_hint_tilt.setSpeed(1);

                lott_hint_shoulder.setAnimation(R.raw.ic_blue_dot);
                lott_hint_shoulder.playAnimation();
                lott_hint_shoulder.setSpeed(1);

                textv_demo_head_down.setText("Pause and Play");
                textv_demo_body_down.setText("These buttons allow you to turn off and on data reading and notifications for all angles.");
                break;

            case 5:
                Hide_arr();
                arr_5.setVisibility(View.VISIBLE);
                textv_demo_head_down.setText("Sound Alert");
                textv_demo_body_down.setText("When the sound Alert is turned on, the device makes a sound if the body movement exceeds the defined angle.");
                break;

            case 6:
                Hide_arr();
                arr_6.setVisibility(View.VISIBLE);
                textv_demo_head_down.setText("Vibration Alerts");
                textv_demo_body_down.setText("When the Vibration Alert is turned on, the device makes a Vibration if the body movement exceeds the defined angle.");
                break;

            case 7:
                Hide_arr();
                arr_7.setVisibility(View.VISIBLE);
                textv_demo_head_up.setText("Temperature");
                textv_demo_body_up.setText("It provides you real-time information about your body temperature.");
                lott_temp_level.playAnimation();
                lott_temp_level.loop(true);
                lay_demo_down.setVisibility(View.INVISIBLE);
                lay_demo_up.setVisibility(View.VISIBLE);
                break;

            case 8:
                lott_temp_level.pauseAnimation();
                lott_temp_level.setFrame(15);
                Hide_arr();
                arr_8.setVisibility(View.VISIBLE);
                textv_demo_head_up.setText("Battery Level");
                textv_demo_body_up.setText("The remaining charge value in the device's battery is displayed here, and when the charger is connected, the status changes to indicate that the device is charging.");
                lott_battery_level.playAnimation();
                lott_battery_level.loop(true);
                imgv_charging.setVisibility(View.VISIBLE);
                textv_battery_level.setText("");
                break;

            case 9:
                Hide_arr();
                arr_9.setVisibility(View.VISIBLE);
                textv_demo_head_up.setText("Delay");
                textv_demo_body_up.setText("You can set the delay of the sound and vibration alert to 2, 4, or 6 seconds.");
                break;

            case 10:
                Hide_arr();
                arr_10.setVisibility(View.VISIBLE);
                textv_demo_head_up.setText("Sensitivity");
                textv_demo_body_up.setText("You can customize the sensitivity of the device in measuring the range of motion, as it allows you to have a smaller or larger range of motion");
                break;

            case 11:
                Hide_arr();
                arr_11.setVisibility(View.VISIBLE);
                textv_demo_head_up.setText("Reset Your Posture");
                textv_demo_body_up.setText("Posture can be reset at any time by pushing a single button. Simply place your body in a proper and comfortable position and push this button; your body will be watched from this new point.");
                break;

            case 12:
                Hide_arr();
                lay_arr.setVisibility(View.GONE);
                lay_demo_down.setVisibility(View.GONE);
                lay_demo_up.setVisibility(View.GONE);
                DEMO = "Run_Normal_With_Real_Data" ;
                break;


            default:

        }


    }


    private void Hide_arr(){
        arr_1.setVisibility(View.INVISIBLE);
        arr_2.setVisibility(View.INVISIBLE);
        arr_3.setVisibility(View.INVISIBLE);
        arr_4.setVisibility(View.INVISIBLE);
        arr_5.setVisibility(View.INVISIBLE);
        arr_6.setVisibility(View.INVISIBLE);
        arr_7.setVisibility(View.INVISIBLE);
        arr_8.setVisibility(View.INVISIBLE);
        arr_9.setVisibility(View.INVISIBLE);
        arr_10.setVisibility(View.INVISIBLE);
        arr_11.setVisibility(View.INVISIBLE);

    }


    public void onBackPressed() {
        super.onBackPressed();
        EndConnection();
        // Intent intent = new Intent(VoiceControlActivity.this, NewProjectChooseActivity.class);
        //   intent.putExtra("AD_STATE","true" );
        //    startActivity(intent);
        //  finish();
    }




}