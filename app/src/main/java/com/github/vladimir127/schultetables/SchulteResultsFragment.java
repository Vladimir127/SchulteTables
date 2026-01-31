package com.github.vladimir127.schultetables;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vladimir127.schultetables.domain.repository.Extra;
import com.github.vladimir127.schultetables.domain.repository.OnErrorListener;
import com.github.vladimir127.schultetables.domain.repository.OnSuccessListener;
import com.github.vladimir127.schultetables.domain.repository.SchulteRepository;
import com.github.vladimir127.schultetables.infrastructure.MyApp;
import com.github.vladimir127.schultetables.utils.TimeUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchulteResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchulteResultsFragment extends Fragment {

    private static final String ARG_LEVEL = "level";
    private static final String ARG_ASCENDING = "ascending";
    private static final String ARG_TIME = "time";
    private static final String ARG_LOSE = "lose";

    private int level;
    private boolean ascending;
    private int timeMillis;
    private boolean lose;

    private TextView resultsHeaderTextView, resultsTextView, repeatTextView, menuTextView, livesOverTextView;

    private SchulteRepository schulteRepository;

    public SchulteResultsFragment() {
    }

    public static SchulteResultsFragment newInstance(int level, boolean ascending, int timeMillis, boolean lose) {
        SchulteResultsFragment fragment = new SchulteResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL, level);
        args.putBoolean(ARG_ASCENDING, ascending);
        args.putInt(ARG_TIME, timeMillis);
        args.putBoolean(ARG_LOSE, lose);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            level = getArguments().getInt(ARG_LEVEL);
            ascending = getArguments().getBoolean(ARG_ASCENDING);
            timeMillis = getArguments().getInt(ARG_TIME);
            lose = getArguments().getBoolean(ARG_LOSE);
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
        return inflater.inflate(R.layout.fragment_results_shulte, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsHeaderTextView = view.findViewById(R.id.result_header_text_view);
        resultsTextView = view.findViewById(R.id.result_text_view);
        repeatTextView = view.findViewById(R.id.repeat_text_view);
        menuTextView = view.findViewById(R.id.menu_text_view);
        livesOverTextView = view.findViewById(R.id.lives_over_text_view);

        String time = TimeUtils.millisecondsToTimeString(timeMillis);
        resultsTextView.setText(time);

        schulteRepository = ((MyApp) getActivity().getApplication()).getSchulteRepository();

        if (lose) {
            livesOverTextView.setVisibility(View.VISIBLE);
        } else {
            checkRecordBroken();
        }

        repeatTextView.setOnClickListener(v -> getContract().repeatGame());
        menuTextView.setOnClickListener(v -> getContract().showMenu());
    }

    private void checkRecordBroken() {
        Extra extra = new Extra(false, ascending);
        schulteRepository.loadRecord(level, ascending,

                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int previousRecord) {
                        if (previousRecord == 0 || timeMillis < previousRecord) {
                            showNewRecord();
                        }
                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        // Если пришла ошибка из репозитория, значит, в локальном
                        // репозитории этого рекорда нет, а связаться с удалённым
                        // репозиторием не удалось. Это может возникнуть только
                        // в том случае, если пользователь играет в эту игру в первый
                        // раз при выключенном интернете. В этом случае мы считаем,
                        // что предыдущий рекорд равен нулю, а значит, новый рекорд
                        // надо записать в репозиторий, пока не будет выполнена
                        // первая синхронизация данных. А уже при синхронизации
                        // данных будет выполнена проверка, какой рекорд считать
                        // правильным - тот, который был на сервере, или тот, который
                        // мы только что записали в локальную базу до первой синхронизации.
                        showNewRecord();
                    }
                });
    }

    /**
     * Отображает для пользователя надпись "Новый рекорд",
     * а также записывает новый рекорд в репозиторий.
     */
    private void showNewRecord() {
        resultsHeaderTextView.setText("Новый рекорд!");

        schulteRepository.saveRecord(level, ascending, timeMillis,

                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int record) {

                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        //Toast.makeText(getContext(), "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();
                        // TODO: Нужно ли здесь вообще это сообщение?
                    }
                });
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void showMenu();

        void repeatGame();
    }
}