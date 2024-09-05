package jp.ac.meijou.android.s231205141;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s231205141.databinding.ActivityMain4Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity4 extends AppCompatActivity {

    // Slide26
    private final OkHttpClient okHttpClient = new OkHttpClient(); // http通信するクライアント
    private  final Moshi moshi = new Moshi.Builder().build(); // JSONをJavaのオブジェクトに変換
    private  final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class); // JSONをGistクラスに変換するAdapter
    private ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var request = new Request.Builder()
                .url("https://mura.github.io/meijou-android-sample/gist.json")
                // https://api.github.com/gists/c2a7c39532239ff261be
                // 代替リンク https://mura.github.io/meijou-android-sample/gist.json
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通信に失敗したら呼ばれる
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 通信に成功したら呼ばれる
                var gist = gistJsonAdapter.fromJson(response.body().source());
                Optional.ofNullable(gist)
                        .map(g ->g.files.get("OkHttp.txt"))
                        .ifPresent(gistFile -> {
                            runOnUiThread(() -> binding.text.setText(gistFile.content));
                        });
            }
        });

        //setContentView(R.layout.activity_main4); // 不要なのでコメントアウト
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}