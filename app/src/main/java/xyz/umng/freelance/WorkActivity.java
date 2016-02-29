package xyz.umng.freelance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WorkActivity extends AppCompatActivity {
    private static final String TAG = "WorkActivity";

    ProgressDialog pd;

    @Bind(R.id.input_firstName) EditText _firstNameText;
    @Bind(R.id.input_lastName) EditText _lastNameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_phone) EditText _phoneText;
    @Bind(R.id.input_stream) EditText _streamText;
    @Bind(R.id.input_work_do) EditText _workDoText;
    @Bind(R.id.input_more_about_you) EditText _moreAboutYouText;
    @Bind(R.id.btn_submit_work) Button _submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        ButterKnife.bind(this);

        _submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(WorkActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                pd.setMessage("Submitting...");
                pd.show();
                submit();
            }
        });
    }

    public void submit() {
        Log.d(TAG, "WorkActivity");

        if (!validate()) {
            onSubmitFailed();
            return;
        }

        _submitButton.setEnabled(false);

        String firstName = _firstNameText.getText().toString().trim();
        String lastName = _lastNameText.getText().toString().trim();
        String email = _emailText.getText().toString().trim();
        String phone = _phoneText.getText().toString().trim();
        String stream = _streamText.getText().toString().trim();
        String workDo = _workDoText.getText().toString().trim();
        String moreAboutYou = _moreAboutYouText.getText().toString().trim();

        ParseObject work = new ParseObject("Work");
        work.put("firstName", firstName);
        work.put("lastName", lastName);
        work.put("email", email);
        work.put("phone", phone);
        work.put("stream", stream);
        work.put("workDo", workDo);
        work.put("moreAboutYou", moreAboutYou);
        work.saveInBackground(new SaveCallback() {
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    onSubmitSuccess();
                } else {
                    onSubmitFailed();
                    Toast.makeText(WorkActivity.this, "Error\nPlease try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void onSubmitSuccess() {
        _submitButton.setEnabled(true);
        pd.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkActivity.this);
        builder.setMessage("Your response has been successfully recorded. We will contact you soon.");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void onSubmitFailed() {
        _submitButton.setEnabled(true);
        pd.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = _firstNameText.getText().toString().trim();
        String lastName = _lastNameText.getText().toString().trim();
        String email = _emailText.getText().toString().trim();
        String phone = _phoneText.getText().toString().trim();
        String stream = _streamText.getText().toString().trim();
        String workDo = _workDoText.getText().toString().trim();
        String moreAboutYou = _moreAboutYouText.getText().toString().trim();

        if (firstName.isEmpty() || firstName.length() < 3) {
            _firstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _firstNameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            _lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lastNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 4 || phone.length() > 10) {
            _phoneText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (stream.isEmpty() || stream.length() < 3) {
            _streamText.setError("at least 3 characters");
            valid = false;
        } else {
            _streamText.setError(null);
        }

        if (workDo.isEmpty() || workDo.length() < 3) {
            _workDoText.setError("at least 3 characters");
            valid = false;
        } else {
            _workDoText.setError(null);
        }

        if (moreAboutYou.isEmpty() || moreAboutYou.length() < 10) {
            _moreAboutYouText.setError("at least 10 characters");
            valid = false;
        } else {
            _moreAboutYouText.setError(null);
        }

        return valid;
    }
}
