package kg.taptap.android.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.Random;

import kg.taptap.android.R;
import kg.taptap.android.activity.LoginActivity;
import kg.taptap.android.backend.Api;
import kg.taptap.android.service.Settings;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends RoboFragment {

    private static final String TAG = LoginFragment.class.getName();

    // UI references.
    @InjectView(R.id.phone)
    private EditText mPhoneView;
    @InjectView(R.id.login_progress)
    private View mProgressView;
    @InjectView(R.id.sign_in_button)
    private Button mSignInButton;

    @Inject
    private Api api;
    @Inject
    private Settings settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhoneView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mSignInButton.setOnClickListener(view1 -> attemptLogin());
    }


    private void attemptLogin() {
        // Reset errors.
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid phone address.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            int randCode = getRandomCode();
            String xml = getXMLBody(phone, randCode);
            RequestBody body = RequestBody.create(MediaType.parse("text/xml"), xml);
            api.login(body)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nikitaDTO -> {
                        Log.i(TAG, "success auth with phone = " + phone);
                        showProgress(false);
                        String message;
                        switch (nikitaDTO.getStatus()) {
                            case 0 : {
                                LoginActivity activity = (LoginActivity) getActivity();
                                activity.setCode(randCode);
                                activity.showVerifyFragment();
                                return;
                            }
                            case 1 : message = "Ошибка в формате запроса"; break;
                            case 2 : message = "Неверная авторизация"; break;
                            case 3 : message = "Недопустимый IP-адрес отправителя"; break;
                            case 4 : message = "Недостаточно средств на счету клиентаНедостаточно средств на счету клиента."; break;
                            case 5 : message = "Недопустимое имя отправителяНедопустимое имя отправителя"; break;
                            default : message = "Проблема при получении кода";
                        }
                        Toast.makeText(getActivity(),
                                message,
                                Toast.LENGTH_SHORT)
                                .show();

                    }, throwable -> {
                        showProgress(false);
                        Toast.makeText(getActivity(),
                                "Проблема при получении кода",
                                Toast.LENGTH_SHORT)
                                .show();
                        Log.e(TAG, "error auth");
                    });
        }
    }

    @NonNull
    private String getXMLBody(String phone, int randCode) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<message>\n" +
                        "<login>" + settings.getProperty("login") + "</login>\n" +
                        "<pwd>" + settings.getProperty("password") + "</pwd>\n" +
                        "<id>" + getRandomCode() + "</id>\n" +
                        "<sender>"  + settings.getProperty("company") +  "</sender>\n" +
                        "<text>code: " + randCode + "</text>\n" +
                        "<phones>\n" +
                        "<phone>" + phone + "</phone>\n" +
                        "</phones>\n" +
                        "</message>";
    }

    private int getRandomCode() {
        Random rand = new Random();
        return rand.nextInt(9999 - 1001 + 1) + 1001;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
