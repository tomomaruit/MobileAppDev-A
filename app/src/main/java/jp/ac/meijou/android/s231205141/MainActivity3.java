package jp.ac.meijou.android.s231205141;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s231205141.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK -> {
                        // OK ボタン押下
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(ret -> "Result: " + ret)
                                .ifPresent(text -> binding.textResult.setText(text));
                    }
                    case  RESULT_CANCELED -> {
                        // Cancel ボタン押下
                        binding.textResult.setText("Result: Canceled");
                    }
                    default -> {
                        // 想定外の処理
                        binding.textResult.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // setContentViewは複数回書くと最後の結果が反映される
        // この場合setContentView(R.layout.activity_main3)が最後に反映されると3の画面で固定されてしまうので
        // コメントアウトする
        //setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonA.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            startActivity(intent);
        });

        binding.buttonB.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp"));
            //intent.setData(Uri.parse("geo:35.1355277,136.9729176")); // 地図アプリを立ち上げてみる
            startActivity(intent);
        });

        binding.buttonSend.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            var text = binding.editTextText2.getText().toString();
            intent.putExtra("name",text);
            startActivity(intent);
        });

        binding.buttonLaunch.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            // MainActivityから帰ってきたらgetActivityResultで処理
            getActivityResult.launch(intent);
        });

    }
}