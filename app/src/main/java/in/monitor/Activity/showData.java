package in.monitor.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import in.monitor.R;

public class showData extends AppCompatActivity {

    private TextView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
    data=findViewById(R.id.data);

Intent intent=getIntent();

        String id = intent.getStringExtra("Data");
        data.setText(id);




    }
}
