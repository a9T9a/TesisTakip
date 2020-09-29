package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.View;
import android.widget.ImageButton;

public class Vardiya extends AppCompatActivity {
    private ImageButton gece,gunduz,aksam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vardiya);
        getSupportActionBar().setTitle("Vardiyalar");

        gece=findViewById(R.id.imgb_vard_gece);
        gunduz=findViewById(R.id.imgb_vard_gunduz);
        aksam=findViewById(R.id.imgb_vard_aksam);

        gece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vardiya.this,VardiyaBilgi.class);
                intent.putExtra("Vardiya","Gece");
                startActivity(intent);
            }
        });

        aksam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vardiya.this,VardiyaBilgi.class);
                intent.putExtra("Vardiya","Akşam");
                startActivity(intent);
            }
        });

        gunduz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vardiya.this,VardiyaBilgi.class);
                intent.putExtra("Vardiya","Gündüz");
                startActivity(intent);
            }
        });
    }
}
