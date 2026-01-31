package com.github.vladimir127.schultetables;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.vladimir127.schultetables.utils.Save;

public class SchulteActivity extends AppCompatActivity implements SchulteLevelsFragment.Contract, StartFragment.Contract, SchulteGameFragment.Contract, SchulteResultsFragment.Contract {

    ImageView menuImageView;

    private int level = 3;

    private boolean ascending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schulte);

        menuImageView = findViewById(R.id.menu_image_view);
        menuImageView.setOnClickListener(v -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        });

        loadOrder();

        SchulteLevelsFragment levelsFragment = SchulteLevelsFragment.newInstance(ascending);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, levelsFragment)
                .commit();
    }

    /**
     * Загружает из SharedPreferences порядок цифр в игре - по возрастанию или по убыванию
     */
    private void loadOrder() {
        ascending = Save.loadBool(this, "SchulteOrder", true);
    }

    @Override
    public void showSplashScreen(int level) {
        this.level = level;

        StartFragment startFragment = StartFragment.newInstance(ascending);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, startFragment)
                .commit();
    }

    @Override
    public void saveOrder(boolean ascending) {
        this.ascending = ascending;
        Save.saveBool(this, "SchulteOrder", ascending);
    }

    @Override
    public void startGame() {
        getSupportFragmentManager().popBackStack();

        SchulteGameFragment gameFragment = SchulteGameFragment.newInstance(level, ascending);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, gameFragment)
                .commit();
    }

    @Override
    public void showResults(int level, int finalTime, boolean lose) {
        getSupportFragmentManager().popBackStack();

        SchulteResultsFragment resultsFragment = SchulteResultsFragment.newInstance(level, ascending, finalTime, lose);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, resultsFragment)
                .commit();
    }

    @Override
    public void showMenu() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void repeatGame() {
        getSupportFragmentManager().popBackStack();

        StartFragment startFragment = StartFragment.newInstance(ascending);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, startFragment)
                .commit();
    }
}