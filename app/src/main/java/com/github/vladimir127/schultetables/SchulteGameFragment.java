package com.github.vladimir127.schultetables;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vladimir127.schultetables.utils.SquareGridLayout;
import com.github.vladimir127.schultetables.utils.TimeUtils;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchulteGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchulteGameFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    private static final String ARG_LEVEL = "level";
    private static final String ARG_ASCENDING = "ascending";

    private int level;
    private boolean ascending;

    private int previousMilliseconds = 0;
    private int counter = 0;
    private int finalTime = 0;

    private boolean isRunning = true;
    private boolean wasRunning = false;

    private int numberCount;
    private int currentNumber = 1;

    int livesCount = 3;

    private TextView timeTextView, findTextView;
    private SquareGridLayout gameFieldLayout;
    private ImageView heartImageView1, heartImageView2, heartImageView3;

    public SchulteGameFragment() {
        // Required empty public constructor
    }

    public static SchulteGameFragment newInstance(int level, boolean ascending) {
        SchulteGameFragment fragment = new SchulteGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LEVEL, level);
        args.putBoolean(ARG_ASCENDING, ascending);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            level = getArguments().getInt(ARG_LEVEL);
            ascending = getArguments().getBoolean(ARG_ASCENDING);
            numberCount = level * level;
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
        View view = inflater.inflate(R.layout.fragment_game_schulte, container, false);
        gameFieldLayout = view.findViewById(R.id.game_field_layout);
        displayGrid();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeTextView = view.findViewById(R.id.time_text_view);

        findTextView = view.findViewById(R.id.find_text_view);

        heartImageView1 = view.findViewById(R.id.heart_image_view1);
        heartImageView2 = view.findViewById(R.id.heart_image_view2);
        heartImageView3 = view.findViewById(R.id.heart_image_view3);


        int[] array = generateNumbers();
        displayNumbers(array);

        if (ascending) {
            currentNumber = 1;
        } else {
            currentNumber = numberCount;
        }
        findTextView.setText("Найдите число " + currentNumber);

        runTimer();
    }

    private void displayGrid() {
        gameFieldLayout.setColumnCount(level);
        gameFieldLayout.setRowCount(level);

        int k = 0;

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                TextView textView = new TextView(new ContextThemeWrapper(getContext(), R.style.SchulteCellStyle));
                textView.setTag(String.valueOf(k));
                if (level == 10) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                }

                SquareGridLayout.LayoutParams param = new SquareGridLayout.LayoutParams();
                param.height = 0;
                param.width = 0;
                param.rowSpec = SquareGridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1);
                param.columnSpec = SquareGridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1);
                param.setMargins(2, 2, 2, 2);

                textView.setLayoutParams(param);

                gameFieldLayout.addView(textView, k);
                k++;
            }
        }
    }

    private void displayNumbers(int[] array) {
        for (int i = 0; i < numberCount; i++) {
            TextView textView = gameFieldLayout.findViewWithTag(String.valueOf(i));
            String number = String.valueOf(array[i]);
            textView.setText(number);
            textView.setOnTouchListener(this);
            textView.setOnClickListener(this);
        }
    }

    private int[] generateNumbers() {
        int[] array = new int[numberCount];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            int number;
            do {
                number = random.nextInt(array.length);
            } while (containsNumber(array, i, number));

            array[i] = number + 1;
        }

        return array;
    }

    private boolean containsNumber(int[] array, int index, int number) {
        for (int i = 0; i < index; i++) {
            if (array[i] == number + 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onPause() {
        previousMilliseconds = counter;
        wasRunning = true;
        isRunning = false;
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (wasRunning) {
            isRunning = true;
            runTimer();
        }
    }

    private void runTimer() {
        final Handler handler = new Handler();

        long startTime = System.currentTimeMillis();

        handler.post(new Runnable() {
            @Override
            public void run() {
                String time = TimeUtils.millisecondsToTimeString(counter);

                timeTextView.setText(time);

                if (isRunning) {
                    int newMilliseconds = (int) (System.currentTimeMillis() - startTime);
                    counter = previousMilliseconds + newMilliseconds;
                    handler.postDelayed(this, 10);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        TextView textView = (TextView) v;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (textView.getText().equals(String.valueOf(currentNumber))) {
                textView.setBackground(getResources().getDrawable(R.drawable.schulte_cell_pressed));
            } else {
                textView.setBackground(getResources().getDrawable(R.drawable.schulte_cell_wrong));
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            textView.setBackground(getResources().getDrawable(R.drawable.schulte_cell_unpressed));
            v.performClick();
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;

        // Если нажали правильно, увеличиваем/уменьшаем счётчик
        if (textView.getText().equals(String.valueOf(currentNumber))) {


            // Если порядок - по возрастанию
            if (ascending) {
                currentNumber++;

                // Проверяем, не дошли ли мы до конца
                if (currentNumber <= numberCount) {
                    findTextView.setText("Найдите число " + currentNumber);
                } else {
                    finalTime = counter;
                    getContract().showResults(level, finalTime, false);
                }
            }

            // Если порядок - по убыванию
            else {
                currentNumber--;

                // Проверяем, не дошли ли мы до конца
                if (currentNumber >= 1) {
                    findTextView.setText("Найдите число " + currentNumber);
                } else {
                    finalTime = counter;
                    getContract().showResults(level, finalTime, false);
                }
            }
        }

        // Если нажали неправильно, отнимаем жизнь
        else {
            decreaseLives();
        }
    }

    private void decreaseLives() {
        livesCount--;

        if (livesCount == 2) {
            heartImageView3.setVisibility(View.INVISIBLE);
        }

        if (livesCount == 1) {
            heartImageView2.setVisibility(View.INVISIBLE);
        }

        if (livesCount == 0) {
            heartImageView1.setVisibility(View.INVISIBLE);

            finalTime = counter;
            getContract().showResults(level, finalTime, true);
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        void showResults(int level, int finalTime, boolean lose);
    }
}