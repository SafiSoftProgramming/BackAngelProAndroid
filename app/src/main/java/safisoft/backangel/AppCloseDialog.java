package safisoft.backangel;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;


public class AppCloseDialog extends Dialog implements
        View.OnClickListener {
    public ImageButton btn_no , btn_yes;
    public Activity c;

    public AppCloseDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_close_dialog);

        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);

        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
