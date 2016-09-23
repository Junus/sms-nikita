package kg.taptap.android.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kg.taptap.android.R;
import kg.taptap.android.fragment.LoginFragment;
import kg.taptap.android.fragment.SuccessFragment;
import kg.taptap.android.fragment.VerifyFragment;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;

import static android.Manifest.permission.READ_CONTACTS;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboFragmentActivity {

    @InjectFragment(R.id.login_fragment)
    private LoginFragment loginFragment;
    @InjectFragment(R.id.verify_fragment)
    private VerifyFragment verifyFragment;
    @InjectFragment(R.id.success_fragment)
    private SuccessFragment successFragment;

    private int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoginFragment();
    }

    public void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
                .hide(verifyFragment)
                .hide(successFragment)
                .show(loginFragment)
                .commitAllowingStateLoss();
    }

    public void showVerifyFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
                .hide(loginFragment)
                .hide(successFragment)
                .show(verifyFragment)
                .commitAllowingStateLoss();
    }

    public void showSuccessFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
                .show(successFragment)
                .hide(loginFragment)
                .hide(verifyFragment)
                .commitAllowingStateLoss();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}

