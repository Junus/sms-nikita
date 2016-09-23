package kg.taptap.android.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import kg.taptap.android.R;
import kg.taptap.android.activity.LoginActivity;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class VerifyFragment extends RoboFragment {

    @InjectView(R.id.code)
    private EditText mCodeView;
    @InjectView(R.id.sign_in_button)
    private Button mSignInButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCodeView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.check || id == EditorInfo.IME_NULL) {
                attemptCheck();
                return true;
            }
            return false;
        });
        mSignInButton.setOnClickListener(view1 -> attemptCheck());
    }

    private void attemptCheck() {
        mCodeView.setError(null);
        View focusView;

        LoginActivity activity = (LoginActivity) getActivity();
        if (Integer.valueOf(mCodeView.getText().toString()) == activity.getCode()) {
            activity.showSuccessFragment();
        } else {
            mCodeView.setError(getString(R.string.error_code));
            focusView = mCodeView;
            focusView.requestFocus();
        }
    }
}
