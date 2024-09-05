package jp.ac.meijou.android.s231205141;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

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

import jp.ac.meijou.android.s231205141.databinding.ActivityMain5Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity {


    private final OkHttpClient okHttpClient = new OkHttpClient(); // http通信するクライアント
    private  final Moshi moshi = new Moshi.Builder().build(); // JSONをJavaのオブジェクトに変換
    private  final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class); // JSONをGistクラスに変換するAdapter
    private ActivityMain5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGet.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            var text = binding.editTextText3.getText().toString();
            var url  = Uri.parse("https://placehold.jp/350×350.png")
                    .buildUpon()
                    .appendQueryParameter("text", text)
                    .build()
                    .toString();
            getImage(url);
        });

        /*var request = new Request.Builder()
                .url("https://placehold.jp/350×350.png")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 通信に成功したら呼ばれる
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                // UIスレッド以外で更新するとクラッシュするので、UIスレッド上で実行
                runOnUiThread(() -> binding.imageView.setImageBitmap(bitmap));
            }
        });*/


        //setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void getImage(String url) {
        // リクエスト先に画像URLを指定
        var request = new Request.Builder()
                .url(url)
                .build();

        // 非同期通信でリクエスト
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通信に失敗した時に呼ばれる
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 通信に成功したら呼ばれる
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                // UIスレッド以外で更新するとクラッシュするので、UIスレッド上で実行
                runOnUiThread(() -> binding.imageView.setImageBitmap(bitmap));
            }
        });
    }
}