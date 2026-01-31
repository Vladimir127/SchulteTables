package com.github.vladimir127.schultetables;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.github.vladimir127.schultetables.domain.repository.Extra;
import com.github.vladimir127.schultetables.domain.repository.OnErrorListener;
import com.github.vladimir127.schultetables.domain.repository.OnSuccessListener;
import com.github.vladimir127.schultetables.domain.repository.SchulteRepository;
import com.github.vladimir127.schultetables.infrastructure.MyApp;
import com.github.vladimir127.schultetables.utils.TimeUtils;

public class SchulteLevelsFragment extends Fragment {

    private static final String ARG_ASCENDING = "ascending";
    private static final String TAG = "SchulteLevelsFragment";
    private boolean ascending;

    private ConstraintLayout layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10;
    private FrameLayout layoutAsc, layoutDesc, layoutClear;
    private TextView textViewAsc, textViewDesc;

    private SchulteRepository schulteRepository;

    public SchulteLevelsFragment() {
    }

    public static SchulteLevelsFragment newInstance(boolean ascending) {
        SchulteLevelsFragment fragment = new SchulteLevelsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_levels_schulte, container, false);

        layout3 = view.findViewById(R.id.layout3);
        layout4 = view.findViewById(R.id.layout4);
        layout5 = view.findViewById(R.id.layout5);
        layout6 = view.findViewById(R.id.layout6);
        layout7 = view.findViewById(R.id.layout7);
        layout8 = view.findViewById(R.id.layout8);
        layout9 = view.findViewById(R.id.layout9);
        layout10 = view.findViewById(R.id.layout10);
        layoutAsc = view.findViewById(R.id.layout_asc);
        layoutDesc = view.findViewById(R.id.layout_desc);
        layoutClear = view.findViewById(R.id.layout_clear);

        textViewAsc = view.findViewById(R.id.text_view_asc);
        textViewDesc = view.findViewById(R.id.text_view_desc);

        toggleOrderButtons();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layout3.setOnClickListener(v -> getContract().showSplashScreen(3));
        layout4.setOnClickListener(v -> getContract().showSplashScreen(4));
        layout5.setOnClickListener(v -> getContract().showSplashScreen(5));
        layout6.setOnClickListener(v -> getContract().showSplashScreen(6));
        layout7.setOnClickListener(v -> getContract().showSplashScreen(7));
        layout8.setOnClickListener(v -> getContract().showSplashScreen(8));
        layout9.setOnClickListener(v -> getContract().showSplashScreen(9));
        layout10.setOnClickListener(v -> getContract().showSplashScreen(10));

        schulteRepository = ((MyApp) getActivity().getApplication()).getSchulteRepository();

        loadRecords(view);

        layoutAsc.setOnClickListener(v -> {
            ascending = true;
            getContract().saveOrder(ascending);
            toggleOrderButtons();
            loadRecords(view);
        });

        layoutDesc.setOnClickListener(v -> {
            ascending = false;
            getContract().saveOrder(ascending);
            toggleOrderButtons();
            loadRecords(view);
        });

        layoutClear.setOnClickListener(v -> {
            showClearConfirmDialog(view);
        });
    }

    private void loadRecords(View view) {
        for (int i = 3; i <= 10; i++) {
            TextView textView = view.findViewWithTag("textView" + i);

            Extra extra = new Extra(false, ascending);
            schulteRepository.loadRecord(i, ascending,

                    new OnSuccessListener() {
                        @Override
                        public void onSuccess(int record) {
                            String recordString = TimeUtils.millisecondsToTimeString(record);
                            textView.setText("Рекорд: " + recordString);
                        }
                    },

                    new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            //Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                            String recordString = TimeUtils.millisecondsToTimeString(0);
                            textView.setText("Рекорд: " + recordString);
                        }
                    });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException("Activity must implement contract");
        }
    }

    public void toggleOrderButtons() {
        if (ascending) {
            layoutAsc.setBackground(getResources().getDrawable(R.drawable.blue_black_round_outlined));
            textViewAsc.setTextColor(getResources().getColor(R.color.main_goal_button_font));

            layoutDesc.setBackground(getResources().getDrawable(R.drawable.blue_black_round));
            textViewDesc.setTextColor(getResources().getColor(R.color.date_light));
        } else {
            layoutDesc.setBackground(getResources().getDrawable(R.drawable.blue_black_round_outlined));
            textViewDesc.setTextColor(getResources().getColor(R.color.main_goal_button_font));

            layoutAsc.setBackground(getResources().getDrawable(R.drawable.blue_black_round));
            textViewAsc.setTextColor(getResources().getColor(R.color.date_light));
        }
    }

    /**
     * Отображает диалоговое окно с предупреждением о сбросе рекордов
     */
    private void showClearConfirmDialog(View view) {
        Log.d(TAG, "showClearConfirmDialog: Нажата кнопка Сбросить рекорды. Отображаем диалог с предупреждением");
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.alert_layout, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());

        mDialogBuilder.setView(promptsView);
        TextView TextView1 = promptsView.findViewById(R.id.TextView1);
        TextView YesTextView = promptsView.findViewById(R.id.YesTextView);
        TextView NoTextView = promptsView.findViewById(R.id.NoTextView);

        TextView1.setText(getString(R.string.confirm_clear));
        YesTextView.setText(getString(R.string.yes));
        NoTextView.setText(getString(R.string.cansel));
        final AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        YesTextView.setOnClickListener(v -> {
            Log.d(TAG, "showClearConfirmDialog: Нажата кнопка Да, сбрасываем рекорды");
            clearRecords(view);
            alertDialog.dismiss();
        });
        NoTextView.setOnClickListener(v -> {
            Log.d(TAG, "showClearConfirmDialog: нажата кнопка Нет, закрываем диалог");
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    private void clearRecords(View view) {
        Log.d(TAG, "clearRecords: для целей тестирования обрабатываем рекорды только 3 и 4 уровней");
        for (int i = 3; i <= 10; i++) {
            TextView textView = view.findViewWithTag("textView" + i);

            Log.d(TAG, "clearRecords: i == " + i + ". Сохраняем рекорд в репозитории");

            schulteRepository.saveRecord(i, ascending, 0,

                    new OnSuccessListener() {
                        @Override
                        public void onSuccess(int record) {
                            String recordString = TimeUtils.millisecondsToTimeString(record);
                            textView.setText("Рекорд: " + recordString);
                        }
                    },

                    new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            //Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                            String recordString = TimeUtils.millisecondsToTimeString(0);
                            textView.setText("Рекорд: " + recordString);
                        }
                    });
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void showSplashScreen(int level);

        void saveOrder(boolean ascending);
    }
}