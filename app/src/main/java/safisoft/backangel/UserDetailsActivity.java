package safisoft.backangel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UserDetailsActivity extends AppCompatActivity {

    ImageButton btn_skip , btn_save_username ;
    EditText edit_text_username ;

    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        sharedPreferences = getSharedPreferences("Angel_Pro_Data", MODE_PRIVATE);
        SharedPreferences.Editor myData = sharedPreferences.edit();

        myData.putString("User_Name","User");
        myData.putString("First_Run","Run1");
        myData.apply();


        btn_skip = findViewById(R.id.btn_skip);
        btn_save_username = findViewById(R.id.btn_save_username);
        edit_text_username = findViewById(R.id.edit_text_username);



       // SharedPreferences getData = getSharedPreferences("Angel_Pro_Data", MODE_PRIVATE);
      //  String s =getData.getString("User_Name","");



        btn_save_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_text_username.getText().toString().isEmpty()){
                    SnackBarInfoControl snackBarInfoControl = new SnackBarInfoControl();
                    snackBarInfoControl.SnackBarInfoControlView(getApplicationContext(), findViewById(android.R.id.content).getRootView(), UserDetailsActivity.this, "The Back Angel Pro app wants to know your name.");
                }
                else{
                    myData.putString("User_Name",edit_text_username.getText().toString());
                    myData.apply();

                    Intent intent = new Intent(UserDetailsActivity.this, ConnectingBackAngelDeviceActivity.class);
                    startActivity(intent);
                    finish();
                }





            }
        });



        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailsActivity.this, ConnectingBackAngelDeviceActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}