package jp.ac.meijou.android.s231205141;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.Optional;

import io.reactivex.rxjava3.core.Single;

public class PrefDataStore {

    private static PrefDataStore instance;
    private final RxDataStore<Preferences> dataStore;

    /**
     * コンストラクタ
     *
     * @param dataStore DataStore
     */
    private PrefDataStore(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Lesson5DataStore のインスタンスを返す
     *
     * @param context Context
     * @return Lesson5DataStoreのインスタンス(シングルトン)
     */
    public static PrefDataStore getInstance(Context context) {
        if (instance == null) {
            var dataStore = new RxPreferenceDataStoreBuilder(context.getApplicationContext(), "settings").build();
            instance = new PrefDataStore(dataStore);
        }
        return instance;
    }

    /**
     * Stringの値をを保存する
     *
     * @param key   保存するキー
     * @param value 保存する値
     */
    public void setString(String key, String value) {
        dataStore.updateDataAsync(prefsIn -> {
                    var mutablePreferences = prefsIn.toMutablePreferences();
                    var prefKey = PreferencesKeys.stringKey(key);
                    mutablePreferences.set(prefKey, value);
                    return Single.just(mutablePreferences);
                })
                .subscribe();
    }

    public Optional<String> getString(String key) {
        return dataStore.data()
                .map(prefs -> {
                    var prefkey = PreferencesKeys.stringKey(key);
                    return Optional.ofNullable(prefs.get(prefkey));
                })
                .blockingFirst();
    }
}

