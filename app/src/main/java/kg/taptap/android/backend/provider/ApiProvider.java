package kg.taptap.android.backend.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import kg.taptap.android.backend.Api;
import kg.taptap.android.service.Settings;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Singleton
public class ApiProvider implements Provider<Api> {

    private static final String TAG = ApiProvider.class.getName();

    private static final int TIMEOUT = 2;
    private static final int RW_TIMEOUT = 1;

    private static final String CONFIG_FILE = "application.properties";
    private static final String API_URL = "api.url";

    private Settings settings;

    @Inject
    public ApiProvider(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Api get() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(RW_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(RW_TIMEOUT, TimeUnit.MINUTES)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getEndpoint())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(Api.class);
    }

    private String getEndpoint() {
        String endpoint = settings.getProperty(API_URL);
        if (StringUtils.isEmpty(endpoint)) {
            throw new IllegalArgumentException(API_URL + " is empty or not defined in " + CONFIG_FILE);
        }

        return endpoint;
    }
}
