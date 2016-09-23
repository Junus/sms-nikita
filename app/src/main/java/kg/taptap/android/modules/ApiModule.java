package kg.taptap.android.modules;

import com.google.inject.AbstractModule;

import kg.taptap.android.backend.Api;
import kg.taptap.android.backend.provider.ApiProvider;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Api.class).toProvider(ApiProvider.class);
    }
}
