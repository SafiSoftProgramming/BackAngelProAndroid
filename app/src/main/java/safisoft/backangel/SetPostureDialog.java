package safisoft.backangel;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class SetPostureDialog extends Dialog implements
        View.OnClickListener {
    public ImageButton btn_ok;
    public Activity c;
    public TextView textv_username_dialog ;

    public SetPostureDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_posture_dialog);

        btn_ok = findViewById(R.id.btn_set_pos_dialog);
        btn_ok.setOnClickListener(this);

        textv_username_dialog = findViewById(R.id.textv_username_dialog);
        textv_username_dialog.setText("");

    }

    @Override
    public void onClick(View v) {

    }
}
