package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button enterBtn = findViewById(R.id.enter);
        final EditText name = findViewById(R.id.name);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = name.getText().toString();
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                intent.putExtra("Name", string);
                startActivity(intent);
            }
        });
    }
}
