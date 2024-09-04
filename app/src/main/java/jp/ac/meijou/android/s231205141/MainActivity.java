package jp.ac.meijou.android.s231205141;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s231205141.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.changebutton.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            binding.text.setText(text);
        });

        binding.savebutton.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            prefDataStore.setString("name", text);
        });

        // 文字を消す処理
        binding.deletebutton.setOnClickListener(view -> {
            binding.text.setText(null);
        });

        prefDataStore.getString("name")
                .ifPresent(name -> binding.text.setText(name));

        /*binding.editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // テキストが更新されたら呼ばれる
                binding.text.setText(editable.toString());
            }
        });*/
        // OKボタン
        binding.buttonOk.setOnClickListener(view -> {
            var intent = new Intent();
            intent.putExtra("ret", "OK");
            setResult(RESULT_OK, intent);
            finish(); // 画面自体を終了させる処理
        });

        // Cancelボタン
        binding.buttonCancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        //prefDataStore.getString("name").ifPresent(name -> binding.text.setText(name));
        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        String text = getIntent().getStringExtra("name");
        binding.text.setText(text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        var text = binding.editTextText.getText().toString();
        prefDataStore.setString("name", text);
    }
}