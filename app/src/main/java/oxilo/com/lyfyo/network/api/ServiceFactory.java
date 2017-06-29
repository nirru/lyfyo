package oxilo.com.lyfyo.network.api;


import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import oxilo.com.lyfyo.AppConstant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ericbasendra on 10/06/16.
 */
public class ServiceFactory {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
         .connectTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS);
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(AppConstant.DEVELOPMENT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = builder.build();

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) throws Exception {
          final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.DEVELOPMENT_BASE_URL)
                .client(ConfigureTimeouts())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        T service = retrofit.create(clazz);
        return service;
    }




    public static <S> S createService(Class<S> serviceClass) {
            String authToken = Credentials.basic("please enter username", "please enter password");
            return createService(serviceClass, authToken);
    }

    public static <S> S createPublicService(Class<S> serviceClass) {
        return createService(serviceClass, "Basic OWNhOTUxMGU0YmY0NTQxZTdiYWM0Yjc1Yzc5ZDdkMTg=");
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addInterceptor(httpLoggingInterceptor);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }


    public  static OkHttpClient ConfigureTimeouts() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Content-Type", "multipart/form-data")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return client;
    }


}
