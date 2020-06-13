package com.example.jacobsthierryandroidproject.View.fragment.recipePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.jacobsthierryandroidproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class addComment extends Activity {

    EditText text;
    Button button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        text = findViewById(R.id.editTextComment);
        button = findViewById(R.id.btn_subbmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", text.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
