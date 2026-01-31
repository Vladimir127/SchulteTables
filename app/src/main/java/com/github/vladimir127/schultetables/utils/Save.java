package com.github.vladimir127.schultetables.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Save {

    private final static String TAG = "ControlReality.Save";

    public static void saveString(Context ct, String name, String text) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putString(name, text);
        ed.commit();
    }

    public static String loadString(Context ct, String name) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        String result = sPref.getString(name, "");
        return result;
    }

    public static void saveInt(Context ct, String name, int value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putInt(name, value);
        ed.apply();
    }

    public static int loadInt(Context ct, String name, int defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getInt(name, defValue);
    }

    public static void saveLong(Context ct, String name, long value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putLong(name, value);
        ed.apply();
    }

    public static long loadLong(Context ct, String name, int defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getLong(name, defValue);
    }

    public static void saveBool(Context ct, String name, boolean value) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        ed.remove(name).apply();
        ed.putBoolean(name, value);
        ed.apply();
    }

    public static boolean loadBool(Context ct, String name, boolean defValue) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences(name, Context.MODE_PRIVATE);
        return sPref.getBoolean(name, defValue);
    }

    public static Uri saveTempImage(Context context, Bitmap bitmap, String NameImage) {
        try {
            File storageDir = context.getFilesDir();
            File filePath = File.createTempFile(
                    NameImage,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return Uri.fromFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri saveImage(Context context, Bitmap bitmap, String uri) {
        try {
            File storageDir = context.getFilesDir();
            File filePath = new File(uri);
            filePath.delete(); // Delete the File, just in Case, that there was still another File
            filePath.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return Uri.fromFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadMainGoal(Context ct) {
        //Загрузить картинки
        String sizeImages = loadString(ct, "sizeImages"); // Количество картинок
        if (!sizeImages.equals("")) {
            int size = Integer.parseInt(sizeImages);
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String uri = loadString(ct, "ImageUri" + i);
                strings.add(uri);
            }
            GoalsInfo.getInstance().setImages(strings);
        }
            //Загрузить аудио
            String audio = loadString(ct, "audioUri");
            GoalsInfo.getInstance().setAudioUri(audio);
            String audioName = loadString(ct, "audioName");
            GoalsInfo.getInstance().setAudioName(audioName);

            //Загрузить текст
            String Description = loadString(ct, "Description");
            GoalsInfo.getInstance().setDescription(Description);
        /*} else {
            goals_info.getInstance().clear();
        }*/ // TODO: Временно закомментировал, чтобы главная цель пока загружалась и без картинок
    }

    public static void saveMainGoal(Context ct) {
        //Сохранить картинки // TODO: уБрать
        ArrayList<String> strings = GoalsInfo.getInstance().getImages();
        if (strings.size() > 0) {
            saveString(ct, "sizeImages", String.valueOf(strings.size()));
            for (int i = 0; i < strings.size(); i++) {
                saveString(ct, "ImageUri" + i, strings.get(i));
            }
        }

        Save.saveString(ct, "MainGoalImage" + 0, GoalsInfo.getInstance().getSaveImage1());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage1(), GoalsInfo.getInstance().getSaveImage1());

        Save.saveString(ct, "MainGoalImage" + 1, GoalsInfo.getInstance().getSaveImage2());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage2(), GoalsInfo.getInstance().getSaveImage2());

        Save.saveString(ct, "MainGoalImage" + 2, GoalsInfo.getInstance().getSaveImage3());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage3(), GoalsInfo.getInstance().getSaveImage3());

        Save.saveString(ct, "MainGoalImage" + 3, GoalsInfo.getInstance().getSaveImage4());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage4(), GoalsInfo.getInstance().getSaveImage4());

        Save.saveString(ct, "MainGoalImage" + 4, GoalsInfo.getInstance().getSaveImage5());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage5(), GoalsInfo.getInstance().getSaveImage5());

        Save.saveString(ct, "MainGoalImage" + 5, GoalsInfo.getInstance().getSaveImage6());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage6(), GoalsInfo.getInstance().getSaveImage6());

        Save.saveString(ct, "MainGoalImage" + 6, GoalsInfo.getInstance().getSaveImage7());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage7(), GoalsInfo.getInstance().getSaveImage7());

        Save.saveString(ct, "MainGoalImage" + 7, GoalsInfo.getInstance().getSaveImage8());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage8(), GoalsInfo.getInstance().getSaveImage8());

        Save.saveString(ct, "MainGoalImage" + 8, GoalsInfo.getInstance().getSaveImage9());
        Save.saveImage(ct, GoalsInfo.getInstance().getImage9(), GoalsInfo.getInstance().getSaveImage9());


        //Сохранить аудио
        String audioUrl = GoalsInfo.getInstance().getAudioUri();
        String audioName = GoalsInfo.getInstance().getAudioName();
        if (audioUrl != null) {
            saveString(ct, "audioUri", audioUrl);
        }
        if (audioName != null) {
            saveString(ct, "audioName", audioName);
        }

        //Сохранить текст

        String description = GoalsInfo.getInstance().getDescription();
        if (description != null) {
            saveString(ct, "Description", description);
        }
    }

    public static boolean containsMainGoal(Context ct) {
        SharedPreferences sPref = Objects.requireNonNull(ct).getSharedPreferences("Description", Context.MODE_PRIVATE);
        return sPref.contains("Description");
    }

    public static void saveCurrentGoals(Context ct, String tag, ArrayList<current_goals_info> arr) {
        ArrayList<current_goals_info> arrayList = arr;
        if (!arrayList.isEmpty()) {
            String size = String.valueOf(arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                String text = arrayList.get(i).getText();
                int priority = arrayList.get(i).getPriority();
                String date = arrayList.get(i).getDate();
                Boolean pin = arrayList.get(i).getPin();
                String p;
                if (pin) p = "true";
                else p = "false";

                saveString(ct, tag + "ArrayText" + i, text);
                saveString(ct, tag + "ArrayPriority" + i, String.valueOf(priority));
                saveString(ct, tag + "ArrayDate" + i, date);
                saveString(ct, tag + "ArrayPin" + i, p);
                if (tag.equals("Done")) {
                    saveString(ct, tag + "ArrayDoneDate" + i, p);
                }
            }
            saveString(ct, tag + "Array", size);
        } else {
            saveString(ct, tag + "Array", "");
        }
    }

    public static ArrayList<current_goals_info> loadCurrentGoals(Context ct, String tag) {
        String size = loadString(ct, tag + "Array");
        ArrayList<current_goals_info> arrayList = new ArrayList<>();
        if (!size.equals("")) {
            for (int i = 0; i < Integer.parseInt(size); i++) {
                String text = loadString(ct, tag + "ArrayText" + i);
                int priority = Integer.valueOf(loadString(ct, tag + "ArrayPriority" + i));
                String date = loadString(ct, tag + "ArrayDate" + i);
                Boolean pin;
                if (loadString(ct, tag + "ArrayPin" + i).equals("true")) {
                    pin = true;
                } else {
                    pin = false;
                }


                current_goals_info info = new current_goals_info();
                info.setText(text);
                info.setPriority(priority);
                info.setDate(date);
                info.setPin(pin);
                if (tag.equals("Done")) {
                    String DoneDate = loadString(ct, tag + "ArrayDoneDate" + i);
                    info.setDoneDate(DoneDate);
                }
                arrayList.add(info);
            }
        }
        return arrayList;
    }


    public static void addGoalsItemAndSave(Context ct, String tag, current_goals_info Item) {

        Log.d(TAG, "addGoalsItemAndSave: добавляем цель и сохраняем. Тэг: " + tag + ", цель: " + Item.getText());
        if (tag.equals("Current")) {
            ArrayList<current_goals_info> ar = done_goals_info.getInstance().getCurrentGoals();
            Log.d(TAG, "addGoalsItemAndSave: получаем коллекцию текущих целей: " + LogUtils.collectionToString(ar) + ". Добавляем цель и сохраняем текущие цели в SharedPreferences");

            ar.add(Item);
            saveCurrentGoals(ct, tag, ar);
        } else {
            ArrayList<current_goals_info> ar1 = done_goals_info.getInstance().getDoneGoals();
            Log.d(TAG, "addGoalsItemAndSave: получаем коллекцию выполненных целей: " + LogUtils.collectionToString(ar1));

            Log.d(TAG, "addGoalsItemAndSave: устанавливаем цели дату выполнения: " + getNowDate() + ". Добавляем цель и сохраняем текущие цели в SharedPreferences");
            Item.setDoneDate(getNowDate());
            ar1.add(Item);
            saveCurrentGoals(ct, tag, ar1);
        }
    }

    public static String getNowDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(cal.getTime());
    }

    public static void saveWishCard(Context ct) {
        WishCardInfo info = WishCardInfo.getInstance();
        Save.saveString(ct, "WishCardImage" + 0, WishCardInfo.getInstance().getSaveImage1());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage1(), WishCardInfo.getInstance().getSaveImage1());

        Save.saveString(ct, "WishCardImage" + 1, WishCardInfo.getInstance().getSaveImage2());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage2(), WishCardInfo.getInstance().getSaveImage2());

        Save.saveString(ct, "WishCardImage" + 2, WishCardInfo.getInstance().getSaveImage3());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage3(), WishCardInfo.getInstance().getSaveImage3());

        Save.saveString(ct, "WishCardImage" + 3, WishCardInfo.getInstance().getSaveImage4());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage4(), WishCardInfo.getInstance().getSaveImage4());

        Save.saveString(ct, "WishCardImage" + 4, WishCardInfo.getInstance().getSaveImage5());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage5(), WishCardInfo.getInstance().getSaveImage5());

        Save.saveString(ct, "WishCardImage" + 5, WishCardInfo.getInstance().getSaveImage6());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage6(), WishCardInfo.getInstance().getSaveImage6());

        Save.saveString(ct, "WishCardImage" + 6, WishCardInfo.getInstance().getSaveImage7());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage7(), WishCardInfo.getInstance().getSaveImage7());

        Save.saveString(ct, "WishCardImage" + 7, WishCardInfo.getInstance().getSaveImage8());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage8(), WishCardInfo.getInstance().getSaveImage8());

        Save.saveString(ct, "WishCardImage" + 8, WishCardInfo.getInstance().getSaveImage9());
        Save.saveImage(ct, WishCardInfo.getInstance().getImage9(), WishCardInfo.getInstance().getSaveImage9());

        String b = info.getSaveImage2();
    }


    public static void saveControlledSignal(Context ct) {
        String size = "";
        ArrayList<ControlledSignalInfo> arrayList = AwakenersInfo.getInstance().getAllControlledSignals();
        if (!arrayList.isEmpty()) {
            size = String.valueOf(arrayList.size());
            saveString(ct, "AllControlledSignal", size);
            for (int i = 0; i < arrayList.size(); i++) {
                String Time = arrayList.get(i).getTime();
                String Switch = String.valueOf(arrayList.get(i).isEnabled());
                String TypeSignal = arrayList.get(i).getSignalType();
                String Audio = arrayList.get(i).getAudio();
                String Image = arrayList.get(i).getImage();
                String Text = arrayList.get(i).getText();
                String TextName = arrayList.get(i).getTextName();
                String Id = String.valueOf(arrayList.get(i).getSignalId());
                String AudioName = String.valueOf(arrayList.get(i).getAudioName());
                String AudioNameService = String.valueOf(arrayList.get(i).getAudioNameService());
                String ImageName = String.valueOf(arrayList.get(i).getImageName());
                String Volume = String.valueOf(arrayList.get(i).getVolume());
                Integer[] st = arrayList.get(i).getDaysRepeat();
                String days = "";
                for (int j = 0; j < st.length; j++) {
                    days = days + st[j] + " ";
                }
                saveString(ct, "AllCotrolledSignalTime" + i, Time);
                saveString(ct, "AllCotrolledSignalSwitch" + i, Switch);
                saveString(ct, "AllCotrolledSignalTypeSignal" + i, TypeSignal);
                saveString(ct, "AllCotrolledSignalAudio" + i, Audio);
                saveString(ct, "AllCotrolledSignalImage" + i, Image);
                saveString(ct, "AllCotrolledSignalText" + i, Text);
                saveString(ct, "AllCotrolledSignalTextName" + i, TextName);
                saveString(ct, "AllCotrolledSignalDays" + i, days);
                saveString(ct, "AllCotrolledSignalId" + i, Id);
                saveString(ct, "AllCotrolledSignalAudioName" + i, AudioName);
                saveString(ct, "AllCotrolledSignalAudioNameService" + i, AudioNameService);
                saveString(ct, "AllCotrolledSignalImageName" + i, ImageName);
                saveString(ct, "AllCotrolledSignalVolume" + i, Volume);
            }
        }

        // Если список сигналов пустой (а такое тоже может быть - в частности,
        // после удаления всех сигналов), записываем в SharedPreferences размер
        // этого массива
        else {
            saveString(ct, "AllControlledSignal", "0");
        }
    }

    public static void loadControlledSignal(Context ct) {
        ArrayList<ControlledSignalInfo> controlled_signal_infos = new ArrayList<>();
        if (!loadString(ct, "AllControlledSignal").equals("")) {
            int size = Integer.parseInt(loadString(ct, "AllControlledSignal"));

            for (int i = 0; i < size; i++) {
                ControlledSignalInfo info = new ControlledSignalInfo();
                String Time = loadString(ct, "AllCotrolledSignalTime" + i);
                String Switch = loadString(ct, "AllCotrolledSignalSwitch" + i);
                String TypeSignal = loadString(ct, "AllCotrolledSignalTypeSignal" + i);
                String Audio = loadString(ct, "AllCotrolledSignalAudio" + i);
                String Image = loadString(ct, "AllCotrolledSignalImage" + i);
                String Text = loadString(ct, "AllCotrolledSignalText" + i);
                String TextName = loadString(ct, "AllCotrolledSignalTextName" + i);
                String days = loadString(ct, "AllCotrolledSignalDays" + i);
                String id = loadString(ct, "AllCotrolledSignalId" + i);
                String AudioName = loadString(ct, "AllCotrolledSignalAudioName" + i);
                String AudioNameService = loadString(ct, "AllCotrolledSignalAudioNameService" + i);
                String ImageName = loadString(ct, "AllCotrolledSignalImageName" + i);
                String Volume = loadString(ct, "AllCotrolledSignalVolume" + i);
                String[] st = days.split(" ");
                int l = 1;
                if ((st.length) > 1) {
                    l = st.length - 1;
                }
                Integer[] st1 = new Integer[l];
                for (int r = 0; r < l; r++) {
                    st1[r] = Integer.valueOf(st[r]);
                }

                info.setTime(Time);
                info.setEnabled(Boolean.valueOf(Switch));
                info.setSignalType(TypeSignal);
                info.setAudio(Audio);
                info.setAudioName(AudioName);
                info.setAudioNameService(AudioNameService);
                info.setImage(Image);
                info.setImageName(ImageName);
                info.setText(Text);
                info.setTextName(TextName);
                info.setDaysRepeat(st1);
                info.setSignalId(Integer.parseInt(id));
                info.setVolume(Integer.parseInt(Volume));

                controlled_signal_infos.add(info);
            }
        }
        AwakenersInfo.getInstance().setAllControlledSignals(controlled_signal_infos);
    }


    public static void saveRandomSignal(Context ct, boolean withControlledSignals) {
        RandomSignalInfo randomSignal = AwakenersInfo.getInstance().getSelectedRandomSignal();

        if (randomSignal != null) {
            String timeStart = randomSignal.getInterval().getStartTime();
            String timeEnd = randomSignal.getInterval().getEndTime();

            int count = randomSignal.getCount();

            String excludedInterval1TimeStart = randomSignal.getExcludedIntervals().get(0).getStartTime();
            String excludedInterval1TimeEnd = randomSignal.getExcludedIntervals().get(0).getEndTime();
            boolean excludedInterval1Enabled = randomSignal.getExcludedIntervals().get(0).isEnabled();

            String excludedInterval2TimeStart = randomSignal.getExcludedIntervals().get(1).getStartTime();
            String excludedInterval2TimeEnd = randomSignal.getExcludedIntervals().get(1).getEndTime();
            boolean excludedInterval2Enabled = randomSignal.getExcludedIntervals().get(1).isEnabled();

            String excludedInterval3TimeStart = randomSignal.getExcludedIntervals().get(2).getStartTime();
            String excludedInterval3TimeEnd = randomSignal.getExcludedIntervals().get(2).getEndTime();
            boolean excludedInterval3Enabled = randomSignal.getExcludedIntervals().get(2).isEnabled();

            String isEnabled = String.valueOf(randomSignal.isEnabled());
            String typeSignal = randomSignal.getSignalType();
            String audio = randomSignal.getAudio();
            String image = randomSignal.getImage();
            String text = randomSignal.getText();
            String textName = randomSignal.getTextName();
            String id = String.valueOf(randomSignal.getSignalId());
            String AudioName = String.valueOf(randomSignal.getAudioName());
            String AudioNameService = String.valueOf(randomSignal.getAudioNameService());
            String imageName = String.valueOf(randomSignal.getImageName());
            String volume = String.valueOf(randomSignal.getVolume());

            saveString(ct, "RandomSignalTimeStart", timeStart);
            saveString(ct, "RandomSignalTimeEnd", timeEnd);

            saveInt(ct, "RandomSignalCount", count);

            saveString(ct, "RandomSignalExcludedInterval1TimeStart", excludedInterval1TimeStart);
            saveString(ct, "RandomSignalExcludedInterval1TimeEnd", excludedInterval1TimeEnd);
            saveBool(ct, "RandomSignalExcludedInterval1Enabled", excludedInterval1Enabled);

            saveString(ct, "RandomSignalExcludedInterval2TimeStart", excludedInterval2TimeStart);
            saveString(ct, "RandomSignalExcludedInterval2TimeEnd", excludedInterval2TimeEnd);
            saveBool(ct, "RandomSignalExcludedInterval2Enabled", excludedInterval2Enabled);

            saveString(ct, "RandomSignalExcludedInterval3TimeStart", excludedInterval3TimeStart);
            saveString(ct, "RandomSignalExcludedInterval3TimeEnd", excludedInterval3TimeEnd);
            saveBool(ct, "RandomSignalExcludedInterval3Enabled", excludedInterval3Enabled);

            saveString(ct, "RandomSignalSwitch", isEnabled);
            saveString(ct, "RandomSignalTypeSignal", typeSignal);
            saveString(ct, "RandomSignalAudio", audio);
            saveString(ct, "RandomSignalImage", image);
            saveString(ct, "RandomSignalText", text);
            saveString(ct, "RandomSignalTextName", textName);
            saveString(ct, "RandomSignalId", id);
            saveString(ct, "RandomSignalAudioName", AudioName);
            saveString(ct, "RandomSignalAudioNameService", AudioNameService);
            saveString(ct, "RandomSignalImageName", imageName);
            saveString(ct, "RandomSignalVolume", volume);

            if (withControlledSignals) {
                saveControlledSignalsOfRandomSignal(ct);
            }
        }
    }

    public static void loadRandomSignal(Context ct) {
        RandomSignalInfo randomSignal = new RandomSignalInfo();

        String timeStart = loadString(ct, "RandomSignalTimeStart");
        String timeEnd = loadString(ct, "RandomSignalTimeEnd");
        if (timeStart.equals("") || timeEnd.equals("")) {
            return;
        }
        SignalInterval interval = new SignalInterval(timeStart, timeEnd, false);

        int count = loadInt(ct, "RandomSignalCount", 0);

        List<SignalInterval> excludedIntervals = new ArrayList<>();
        String excludedInterval1TimeStart = loadString(ct, "RandomSignalExcludedInterval1TimeStart");
        String excludedInterval1TimeEnd = loadString(ct, "RandomSignalExcludedInterval1TimeEnd");
        if (excludedInterval1TimeStart.equals("") || excludedInterval1TimeEnd.equals("")) {
            return;
        }
        boolean excludedInterval1Enabled = loadBool(ct, "RandomSignalExcludedInterval1Enabled", false);
        SignalInterval excludedInterval1 = new SignalInterval(excludedInterval1TimeStart, excludedInterval1TimeEnd, excludedInterval1Enabled);
        excludedIntervals.add(excludedInterval1);

        String excludedInterval2TimeStart = loadString(ct, "RandomSignalExcludedInterval2TimeStart");
        String excludedInterval2TimeEnd = loadString(ct, "RandomSignalExcludedInterval2TimeEnd");
        if (excludedInterval2TimeStart.equals("") || excludedInterval2TimeEnd.equals("")) {
            return;
        }
        boolean excludedInterval2Enabled = loadBool(ct, "RandomSignalExcludedInterval2Enabled", false);
        SignalInterval excludedInterval2 = new SignalInterval(excludedInterval2TimeStart, excludedInterval2TimeEnd, excludedInterval2Enabled);
        excludedIntervals.add(excludedInterval2);

        String excludedInterval3TimeStart = loadString(ct, "RandomSignalExcludedInterval3TimeStart");
        String excludedInterval3TimeEnd = loadString(ct, "RandomSignalExcludedInterval3TimeEnd");
        if (excludedInterval3TimeStart.equals("") || excludedInterval3TimeEnd.equals("")) {
            return;
        }
        boolean excludedInterval3Enabled = loadBool(ct, "RandomSignalExcludedInterval3Enabled", false);
        SignalInterval excludedInterval3 = new SignalInterval(excludedInterval3TimeStart, excludedInterval3TimeEnd, excludedInterval3Enabled);
        excludedIntervals.add(excludedInterval3);

        String isEnabled = loadString(ct, "RandomSignalSwitch");
        String typeSignal = loadString(ct, "RandomSignalTypeSignal");
        String audio = loadString(ct, "RandomSignalAudio");
        String image = loadString(ct, "RandomSignalImage");
        String text = loadString(ct, "RandomSignalText");
        String textName = loadString(ct, "RandomSignalTextName");
        String id = loadString(ct, "RandomSignalId");
        String AudioName = loadString(ct, "RandomSignalAudioName");
        String AudioNameService = loadString(ct, "RandomSignalAudioNameService");
        String imageName = loadString(ct, "RandomSignalImageName");
        String volume = loadString(ct, "RandomSignalVolume");

        if (isEnabled.equals("") || typeSignal.equals("") || textName.equals("") || id.equals("") || imageName.equals("") || volume.equals("")) {
            return;
        }
        randomSignal.setInterval(interval);
        randomSignal.setCount(count);
        randomSignal.addExcludedIntervals(excludedIntervals);
        randomSignal.setEnabled(Boolean.valueOf(isEnabled));
        randomSignal.setSignalType(typeSignal);
        randomSignal.setAudio(audio);
        randomSignal.setAudioName(AudioName);
        randomSignal.setAudioNameService(AudioNameService);
        randomSignal.setImage(image);
        randomSignal.setImageName(imageName);
        randomSignal.setText(text);
        randomSignal.setTextName(textName);
        randomSignal.setSignalId(Integer.parseInt(id));
        randomSignal.setVolume(Integer.parseInt(volume));

        AwakenersInfo.getInstance().setSelectedRandomSignal(randomSignal);
        loadControlledSignalsOfRandomSignal(ct);
    }

    private static void saveControlledSignalsOfRandomSignal(Context ct) {
        String size = "";
        List<ControlledSignalInfo> arrayList = AwakenersInfo.getInstance().getSelectedRandomSignal().getControlledSignals();
        if (!arrayList.isEmpty()) {
            size = String.valueOf(arrayList.size());
            saveString(ct, "RandomSignalControlledSignal", size);
            for (int i = 0; i < arrayList.size(); i++) {
                String Time = arrayList.get(i).getTime();
                String Switch = String.valueOf(arrayList.get(i).isEnabled());
                String TypeSignal = arrayList.get(i).getSignalType();
                String Audio = arrayList.get(i).getAudio();
                String Image = arrayList.get(i).getImage();
                String Text = arrayList.get(i).getText();
                String TextName = arrayList.get(i).getTextName();
                String Id = String.valueOf(arrayList.get(i).getSignalId());
                String AudioName = String.valueOf(arrayList.get(i).getAudioName());
                String AudioNameService = String.valueOf(arrayList.get(i).getAudioNameService());
                String ImageName = String.valueOf(arrayList.get(i).getImageName());
                String Volume = String.valueOf(arrayList.get(i).getVolume());
                Integer[] st = arrayList.get(i).getDaysRepeat();
                String days = "";
                for (int j = 0; j < st.length; j++) {
                    days = days + st[j] + " ";
                }
                saveString(ct, "RandomSignalControlledSignalTime" + i, Time);
                saveString(ct, "RandomSignalControlledSignalSwitch" + i, Switch);
                saveString(ct, "RandomSignalControlledSignalTypeSignal" + i, TypeSignal);
                saveString(ct, "RandomSignalControlledSignalAudio" + i, Audio);
                saveString(ct, "RandomSignalControlledSignalImage" + i, Image);
                saveString(ct, "RandomSignalControlledSignalText" + i, Text);
                saveString(ct, "RandomSignalControlledSignalTextName" + i, TextName);
                saveString(ct, "RandomSignalControlledSignalDays" + i, days);
                saveString(ct, "RandomSignalControlledSignalId" + i, Id);
                saveString(ct, "RandomSignalControlledSignalAudioName" + i, AudioName);
                saveString(ct, "RandomSignalControlledSignalAudioNameService" + i, AudioNameService);
                saveString(ct, "RandomSignalControlledSignalImageName" + i, ImageName);
                saveString(ct, "RandomSignalControlledSignalVolume" + i, Volume);
            }
        }

        // Если список сигналов пустой (а такое тоже может быть - в частности,
        // после удаления всех сигналов), записываем в SharedPreferences размер
        // этого массива
        else {
            saveString(ct, "RandomSignalControlledSignal", "0");
        }
    }

    private static void loadControlledSignalsOfRandomSignal(Context ct) {
        RandomSignalInfo randomSignal = AwakenersInfo.getInstance().getSelectedRandomSignal();

        ArrayList<ControlledSignalInfo> controlledSignals = new ArrayList<>();
        if (!loadString(ct, "RandomSignalControlledSignal").equals("")) {
            int size = Integer.parseInt(loadString(ct, "RandomSignalControlledSignal"));

            for (int i = 0; i < size; i++) {
                ControlledSignalInfo info = new ControlledSignalInfo();
                String Time = loadString(ct, "RandomSignalControlledSignalTime" + i);
                String Switch = loadString(ct, "RandomSignalControlledSignalSwitch" + i);
                String TypeSignal = loadString(ct, "RandomSignalControlledSignalTypeSignal" + i);
                String Audio = loadString(ct, "RandomSignalControlledSignalAudio" + i);
                String Image = loadString(ct, "RandomSignalControlledSignalImage" + i);
                String Text = loadString(ct, "RandomSignalControlledSignalText" + i);
                String TextName = loadString(ct, "RandomSignalControlledSignalTextName" + i);
                String days = loadString(ct, "RandomSignalControlledSignalDays" + i);
                String id = loadString(ct, "RandomSignalControlledSignalId" + i);
                String AudioName = loadString(ct, "RandomSignalControlledSignalAudioName" + i);
                String AudioNameService = loadString(ct, "RandomSignalControlledSignalAudioNameService" + i);
                String ImageName = loadString(ct, "RandomSignalControlledSignalImageName" + i);
                String Volume = loadString(ct, "RandomSignalControlledSignalVolume" + i);
                String[] st = days.split(" ");
                int l = 1;
                if ((st.length) > 1) {
                    l = st.length - 1;
                }
                Integer[] st1 = new Integer[l];
                for (int r = 0; r < l; r++) {
                    st1[r] = Integer.valueOf(st[r]);
                }

                info.setTime(Time);
                info.setEnabled(Boolean.valueOf(Switch));
                info.setSignalType(TypeSignal);
                info.setAudio(Audio);
                info.setAudioName(AudioName);
                info.setAudioNameService(AudioNameService);
                info.setImage(Image);
                info.setImageName(ImageName);
                info.setText(Text);
                info.setTextName(TextName);
                info.setDaysRepeat(st1);
                info.setSignalId(Integer.parseInt(id));
                info.setVolume(Integer.parseInt(Volume));

                randomSignal.addControlledSignal(info);
            }
        }
    }

    public static void saveEcumenicalEye(Context ct) {
        EcumenicalEyeInfo ecumenicalEye = AwakenersInfo.getInstance().getEcumenicalEye();
        if (ecumenicalEye != null) {

            String[] appsArray = ecumenicalEye.getApps();
            String appsString = "";
            for (int j = 0; j < appsArray.length; j++) {
                appsString = appsString + appsArray[j] + " ";
            }

            String Interval = String.valueOf(ecumenicalEye.getInterval());
            String TypeSignal = ecumenicalEye.getSignalType();
            String Audio = ecumenicalEye.getAudio();
            String Image = ecumenicalEye.getImage();
            String Text = ecumenicalEye.getText();
            String TextName = ecumenicalEye.getTextName();
            String AudioName = String.valueOf(ecumenicalEye.getAudioName());
            String AudioNameService = String.valueOf(ecumenicalEye.getAudioNameService());
            String ImageName = String.valueOf(ecumenicalEye.getImageName());
            String Volume = String.valueOf(ecumenicalEye.getVolume());
            boolean IsEnabled = ecumenicalEye.isEnabled();

            saveString(ct, "EcumenicalEyeApps", appsString);
            saveString(ct, "EcumenicalEyeInterval", Interval);
            saveString(ct, "EcumenicalEyeTypeSignal", TypeSignal);
            saveString(ct, "EcumenicalEyeAudio", Audio);
            saveString(ct, "EcumenicalEyeImage", Image);
            saveString(ct, "EcumenicalEyeText", Text);
            saveString(ct, "EcumenicalEyeTextName", TextName);
            saveString(ct, "EcumenicalEyeAudioName", AudioName);
            saveString(ct, "EcumenicalEyeAudioNameService", AudioNameService);
            saveString(ct, "EcumenicalEyeImageName", ImageName);
            saveString(ct, "EcumenicalEyeVolume", Volume);
            saveBool(ct, "EcumenicalEyeEnabled", IsEnabled);
        }
    }

    public static void loadEcumenicalEye(Context ct) {

        String appsString = loadString(ct, "EcumenicalEyeApps");
        String[] appsArray = appsString.split(" ");
        if (appsArray.length == 1 && appsArray[0].equals("")) {
            appsArray = new String[0];
        }

        String Interval = loadString(ct, "EcumenicalEyeInterval");
        String TypeSignal = loadString(ct, "EcumenicalEyeTypeSignal");
        String Audio = loadString(ct, "EcumenicalEyeAudio");
        String Image = loadString(ct, "EcumenicalEyeImage");
        String Text = loadString(ct, "EcumenicalEyeText");
        String TextName = loadString(ct, "EcumenicalEyeTextName");
        String AudioName = loadString(ct, "EcumenicalEyeAudioName");
        String AudioNameService = loadString(ct, "EcumenicalEyeAudioNameService");
        String ImageName = loadString(ct, "EcumenicalEyeImageName");
        String Volume = loadString(ct, "EcumenicalEyeVolume");
        boolean IsEnabled = loadBool(ct, "EcumenicalEyeEnabled", false);

        if (Interval.equals("") || TypeSignal.equals("") || Audio.equals("") || Image.equals("") || ImageName.equals("") || Volume.equals("")) {
            return;
        }

        EcumenicalEyeInfo info = new EcumenicalEyeInfo();
        info.setApps(appsArray);
        info.setInterval(Integer.parseInt(Interval));
        info.setSignalType(TypeSignal);
        info.setAudio(Audio);
        info.setAudioName(AudioName);
        info.setAudioNameService(AudioNameService);
        info.setImage(Image);
        info.setImageName(ImageName);
        info.setText(Text);
        info.setTextName(TextName);
        info.setVolume(Integer.parseInt(Volume));
        info.setEnabled(IsEnabled);

        AwakenersInfo.getInstance().setEcumenicalEye(info);
    }


    public static void saveThanks(Context ct, String tag, ArrayList<DiaryEntryInfo> arr) {
        ArrayList<DiaryEntryInfo> arrayList = arr;
        if (!arrayList.isEmpty()) {
            String size = String.valueOf(arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                String text = arrayList.get(i).getText();
                String date = arrayList.get(i).getDate();
                String time = arrayList.get(i).getTime();
                Boolean pin = arrayList.get(i).getPin();
                Boolean star = arrayList.get(i).getStar();
                String id = arrayList.get(i).getId();
                String p;
                if (pin) p = "true";
                else p = "false";
                String s;
                if (star) s = "true";
                else s = "false";

                String audioPatch = arrayList.get(i).getAudioPatch();

                String recognizedMessage = arrayList.get(i).getRecognizedMessage();
                Boolean wasRecognized = arrayList.get(i).wasRecognitionUsed();
                String r;
                if (wasRecognized) r = "true";
                else r = "false";

                saveString(ct, tag + "ArrayText" + i, text);
                saveString(ct, tag + "ArrayDate" + i, date);
                saveString(ct, tag + "ArrayTime" + i, time);
                saveString(ct, tag + "ArrayPin" + i, p);
                saveString(ct, tag + "ArrayAudioPatch" + i, audioPatch);
                saveString(ct, tag + "ArrayId" + i, id);
                saveString(ct, tag + "ArrayStar" + i, s);
                saveString(ct, tag + "ArrayRecognizedMessage" + i, recognizedMessage);
                saveString(ct, tag + "ArrayWasRecognized" + i, r);
            }
            saveString(ct, tag + "Array", size);
        } else {
            saveString(ct, tag + "Array", "");
        }
    }

    public static ArrayList<DiaryEntryInfo> loadThanks(Context ct, String tag) {
        String size = loadString(ct, tag + "Array");
        ArrayList<DiaryEntryInfo> arrayList = new ArrayList<>();
        if (!size.equals("")) {
            for (int i = 0; i < Integer.parseInt(size); i++) {
                String text = loadString(ct, tag + "ArrayText" + i);
                String date = loadString(ct, tag + "ArrayDate" + i);
                String time = loadString(ct, tag + "ArrayTime" + i);
                String id = loadString(ct, tag + "ArrayId" + i);
                Boolean pin;
                if (loadString(ct, tag + "ArrayPin" + i).equals("true")) {
                    pin = true;
                } else {
                    pin = false;
                }
                String audioPatch = loadString(ct, tag + "ArrayAudioPatch" + i);
                Boolean star;
                if (loadString(ct, tag + "ArrayStar" + i).equals("true")) {
                    star = true;
                } else {
                    star = false;
                }
                String recognizedMessage = loadString(ct, tag + "ArrayRecognizedMessage" + i);
                Boolean wasRecognized;
                if (loadString(ct, tag + "ArrayWasRecognized" + i).equals("true")) {
                    wasRecognized = true;
                } else {
                    wasRecognized = false;
                }

                DiaryEntryInfo info = new DiaryEntryInfo();
                info.setText(text);
                info.setAudioPatch(audioPatch);
                info.setDate(date);
                info.setTime(time);
                info.setPin(pin);
                info.setId(id);
                info.setStar(star);
                info.setRecognizedMessage(recognizedMessage);
                info.setWasRecognitionUsed(wasRecognized);

                arrayList.add(info);
            }
        }
        return arrayList;
    }

    /**
     * Сохраняет в SharedPreferences состояния (вкл/выкл) трёх пробудителей: Управляемый сигнал, Случайный сигнал, Вселенское Око. Эти состояния отображаются и меняются на верхней панели главного экрана.
     *
     * @param ct Контекст
     */
    public static void saveAwakenerStates(Context ct) {
        saveBool(ct, "IsControlledSignalEnabled", SettingsInfo.getInstance().isControlledSignalEnabled());
        saveBool(ct, "IsRandomSignalEnabled", SettingsInfo.getInstance().isRandomSignalEnabled());
        saveBool(ct, "IsEcumenicalEyeEnabled", SettingsInfo.getInstance().isEcumenicalEyeEnabled());
    }

    /**
     * Извлекает из SharedPreferences и записывает в SettingsInfo состояния (вкл/выкл) трёх пробудителей: Управляемый сигнал, Случайный сигнал, Вселенское Око. Эти состояния отображаются и меняются на верхней панели главного экрана.
     *
     * @param ct Контекст
     */
    public static void loadAwakenerStates(Context ct) {
        SettingsInfo.getInstance().setControlledSignalEnabled(loadBool(ct, "IsControlledSignalEnabled", true));
        SettingsInfo.getInstance().setRandomSignalEnabled(loadBool(ct, "IsRandomSignalEnabled", true));
        SettingsInfo.getInstance().setEcumenicalEyeEnabled(loadBool(ct, "IsEcumenicalEyeEnabled", true));
    }

    public static void saveRecord(Context ct, String name, int level, boolean ascending, int timeMillis) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (level > 0) {
            builder.append(level);
        }

        if (ascending) {
            builder.append("Asc");
        } else {
            builder.append("Desc");
        }

        String key = builder.toString();

        saveInt(ct, key, timeMillis);
    }

    public static int loadRecord(Context ct, String name, int level, boolean ascending) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (level > 0) {
            builder.append(level);
        }

        if (ascending) {
            builder.append("Asc");
        } else {
            builder.append("Desc");
        }

        String key = builder.toString();

        return loadInt(ct, key, -1);
    }
}
