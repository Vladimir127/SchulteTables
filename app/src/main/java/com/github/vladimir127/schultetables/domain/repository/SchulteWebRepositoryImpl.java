package com.github.vladimir127.schultetables.domain.repository;

import android.util.Log;

import androidx.annotation.Nullable;

public class SchulteWebRepositoryImpl implements SchulteRepository {

//    private final String TAG = "SchulteWebRepo";
//
//    private final GameApiService gameService;
//
//    public SchulteWebRepositoryImpl() {
//        ApiClient apiClient = new ApiClient();
//        gameService = apiClient.getGameService();
//    }
//
    @Override
    public void loadRecord(int level,
                          boolean ascending,
                           OnSuccessListener onSuccessListener,
                           OnErrorListener onErrorListener) {
//
//        // С сервера мы сначала загружаем все игры пользователя (поскольку
//        // API не предоставляет нам запроса по параметрам), а затем уже сами
//        // фильтруем полученный список прямо в репозитории
//
//        Log.d(TAG, "loadRecord: level = " + level + ", ascending = " + ascending);
//        Log.d(TAG, "loadRecord: сначала загружаем все игры из личного кабинета пользователя");
//
//        gameService
//                .getGames()
//                .enqueue(new Callback<GameResponse>() {
//                    @Override
//                    public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
//
//                        Log.d(TAG, "loadRecord.onResponse: поступил ответ");
//
//                        if (response.isSuccessful()) {
//
//                            Log.d(TAG, "loadRecord.onResponse: ответ успешный");
//
//                            GameResponse result = response.body();
//
//                            if (result != null) {
//                                Log.d(TAG, "loadRecord.onResponse: response.body != null. Фильтруем полученный результат по параметрам и вызываем метод onSuccess");
//
//                                GameRecord gameRecord = findGameRecord(result, level, ascending);
//
//                                if (gameRecord != null) {
//                                    int count = gameRecord.getRecord();
//                                    Log.d(TAG, "loadRecord.onResponse: запись найдена. Рекорд равен " + count + ". Возвращаем его через колбэк");
//                                    onSuccessListener.onSuccess(count);
//                                } else {
//                                    Log.d(TAG, "loadRecord.onResponse: запись не найдена. Возвращаем через колбэк значение -1");
//                                    onSuccessListener.onSuccess(-1);
//                                }
//                            } else {
//                                Log.d(TAG, "loadRecord.onResponse: response.body == null");
//                                onErrorListener.onError(new Throwable("response == null"));
//                            }
//                        } else {
//                            int code = response.code();
//                            Log.d(TAG, "loadRecord.onResponse: ответ неуспешный. Код: " + code);
//                            onErrorListener.onError(new Throwable("Api error " + response.code()));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GameResponse> call, Throwable t) {
//                        Log.d(TAG, "loadRecord.onFailure: ошибка при загрузке данных");
//                        t.printStackTrace();
//                        onErrorListener.onError(t);
//                    }
//                });
    }
//
    @Override
    public void saveRecord(int level,
                           boolean ascending,
                           int count,
                           OnSuccessListener onSuccessListener,
                           OnErrorListener onErrorListener) {
//        // Как и в методе saveRecord локального репозитория, здесь нам
//        // сначала нужно найти на сервере запись с такими параметрами, и если
//        // она найдётся, обновить её, а если нет - создать новую. Для этого
//        // мы запрашиваем все рекорды с сервера и фильтруем их, точно так же,
//        // как мы это делали в методе loadRecord.
//
//        Log.d(TAG, "saveRecord: level = " + level + ", ascending = " + ascending);
//        Log.d(TAG, "saveRecord: сначала загружаем все игры из личного кабинета пользователя");
//
//        gameService
//                .getGames().enqueue(new Callback<GameResponse>() {
//                    @Override
//                    public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
//                        Log.d(TAG, "saveRecord.onResponse: поступил ответ");
//
//                        if (response.isSuccessful()) {
//
//                            Log.d(TAG, "saveRecord.onResponse: ответ успешный");
//
//                            GameResponse result = response.body();
//
//                            if (result != null) {
//                                Log.d(TAG, "saveRecord.onResponse: response.body != null. Фильтруем полученный результат по параметрам и вызываем метод onSuccess");
//
//                                GameRecord gameRecord = findGameRecord(result, level, ascending);
//
//                                if (gameRecord != null) {
//                                    int previousCount = gameRecord.getRecord();
//                                    Log.d(TAG, "saveRecord.onResponse: запись найдена. Рекорд равен " + previousCount + ". Обновляем существующую запись на сервере");
//                                    updateGameRecord(gameRecord, count, onSuccessListener, onErrorListener);
//                                } else {
//                                    Log.d(TAG, "saveRecord.onResponse: запись не найдена. Создаём новую запись на сервере.");
//                                    gameRecord = new GameRecord(NAME_SCHULTE, String.valueOf(level), count, new Extra(false, ascending));
//                                    insertGameRecord(gameRecord, onSuccessListener, onErrorListener);
//                                }
//                            } else {
//                                Log.d(TAG, "saveRecord.onResponse: response.body == null");
//                                onErrorListener.onError(new Throwable("response == null"));
//                            }
//                        } else {
//                            int code = response.code();
//                            Log.d(TAG, "saveRecord.onResponse: ответ неуспешный. Код: " + code);
//                            onErrorListener.onError(new Throwable("Api error " + response.code()));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GameResponse> call, Throwable t) {
//                        Log.d(TAG, "saveRecord.onFailure: ошибка при загрузке данных");
//                        t.printStackTrace();
//                        onErrorListener.onError(t);
//                    }
//                });
    }
//
//    @Nullable
//    private GameRecord findGameRecord(GameResponse result, int level, boolean ascending) {
//        return result.results
//                .stream()
//                .filter(x -> x.getName() != null && x.getName().equals(NAME_SCHULTE) &&
//                        x.getLevelName() != null && x.getLevelName().equals(String.valueOf(level)) &&
//                        x.getExtra() != null && x.getExtra().isAscending() == ascending)
//                .findFirst()
//                .orElse(null);
//    }
//
//    private void insertGameRecord(GameRecord gameRecord, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
//        Log.d(TAG, "insertGameRecord. Добавляем новый рекорд на сервер");
//
//        gameService.addGame(gameRecord).enqueue(new Callback<GameRecord>() {
//            @Override
//            public void onResponse(Call<GameRecord> call, Response<GameRecord> response) {
//                Log.d(TAG, "insertGameRecord.onResponse: пришёл ответ");
//                if (response.isSuccessful()) {
//                    GameRecord result = response.body();
//                    String id = result.getId();
//                    Log.d(TAG, "insertGameRecord.onResponse: ответ успешный, ID = " + id + ", возвращаем рекорд через колбэк в вызывающий метод");
//                    onSuccessListener.onSuccess(result.getRecord());
//                } else {
//                    int code = response.code();
//                    Log.d(TAG, "insertGameRecord.onResponse: ответ неуспешный, код " + code);
//                    onErrorListener.onError(new Throwable("Api error " + response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GameRecord> call, Throwable t) {
//                Log.d(TAG, "insertGameRecord.onFailure: ошибка при обращении к серверу");
//                t.printStackTrace();
//                onErrorListener.onError(t);
//            }
//        });
//    }
//
//    private void updateGameRecord(GameRecord gameRecord, int count, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
//        Log.d(TAG, "updateGameRecord: ID = " + gameRecord.getId() + ", count = " + count + ". Обновляем рекорд на сервере");
//        gameRecord.setRecord(count);
//
//        gameService.updateGame(gameRecord.getId(), gameRecord).enqueue(new Callback<GameRecord>() {
//            @Override
//            public void onResponse(Call<GameRecord> call, Response<GameRecord> response) {
//                Log.d(TAG, "updateGameRecord.onResponse: пришёл ответ");
//                if (response.isSuccessful()) {
//                    GameRecord result = response.body();
//                    String id = result.getId();
//                    Log.d(TAG, "updateGameRecord.onResponse: ответ успешный, ID = " + id + ", возвращаем рекорд через колбэк в вызывающий метод"); // TODO: Зачем?
//                    onSuccessListener.onSuccess(result.getRecord());
//                } else {
//                    int code = response.code();
//                    Log.d(TAG, "updateGameRecord.onResponse: ответ неуспешный, код " + code);
//                    onErrorListener.onError(new Throwable("Api error " + response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GameRecord> call, Throwable t) {
//                Log.d(TAG, "updateGameRecord.onFailure: ошибка при обращении к серверу");
//                t.printStackTrace();
//                onErrorListener.onError(t);
//            }
//        });
//    }
}
