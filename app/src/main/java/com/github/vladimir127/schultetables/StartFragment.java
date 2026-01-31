package com.github.vladimir127.schultetables;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class StartFragment extends Fragment {

    private static final String ARG_ASCENDING = "ascending";

    private boolean ascending;

    private TextView instructionTextView, countdownTextView;

    public StartFragment() {}

    public static StartFragment newInstance(boolean ascending) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_ASCENDING, ascending);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ascending = getArguments().getBoolean(ARG_ASCENDING);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException("Activity must implement contract");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        countdownTextView = view.findViewById(R.id.countdown_text_view);
        instructionTextView = view.findViewById(R.id.instruction_text_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (ascending) {
            instructionTextView.setText("Выберите все цифры\nв порядке возрастания");
        } else {
            instructionTextView.setText("Выберите все цифры\nв порядке убывания");
        }
        startCountdown();
    }

    private void startCountdown() {
        animate(countdownTextView, 4);
    }

    private void animate(TextView textView, int i) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(1000);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (i > 1) {
                    textView.setText(String.valueOf(i - 1));
                    animate(textView, i - 1);
                } else {
                    textView.setText("");
                    getContract().startGame();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        textView.startAnimation(anim);
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void startGame();
    }
}