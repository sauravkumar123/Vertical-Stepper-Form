package com.example.visitorbook.verticalstepperform.form.steps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.visitorbook.R;
import com.google.android.material.textfield.TextInputEditText;

import verticalstepperform.Step;
public class StartingDes extends Step<String> {

    private static final int MIN_CHARACTERS_ALARM_NAME = 3;

    private TextInputEditText alarmNameEditText;
    private String unformattedErrorString;

    public StartingDes(String title) {
        this(title, "");
    }

    public StartingDes(String title, String subtitle) {
        super(title, subtitle);
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {

        // We create this step view programmatically

        alarmNameEditText = new TextInputEditText(getContext());
        alarmNameEditText.setHint("Starting Destination (required)");
        alarmNameEditText.setSingleLine(true);
        alarmNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                markAsCompletedOrUncompleted(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        alarmNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                getFormView().goToNextStep(true);
                return false;
            }
        });

        unformattedErrorString = getContext().getResources().getString(R.string.error_alarm_name_min_characters);

        return alarmNameEditText;
    }

    @Override
    protected void onStepOpened(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    public String getStepData() {
        Editable text = alarmNameEditText.getText();
        if (text != null) {
            return text.toString();
        }

        return "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String name = getStepData();
        return name == null || name.isEmpty()
                ? getContext().getString(R.string.form_empty_field)
                : name;
    }

    @Override
    public void restoreStepData(String data) {
        if (alarmNameEditText != null) {
            alarmNameEditText.setText(data);
        }
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        if (stepData.length() < MIN_CHARACTERS_ALARM_NAME) {
            String titleError = String.format(unformattedErrorString, MIN_CHARACTERS_ALARM_NAME);
            return new IsDataValid(false, titleError);
        } else {
            return new IsDataValid(true);
        }
    }
}
