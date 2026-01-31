package com.github.vladimir127.schultetables.domain.repository;

import android.util.Log;

import com.github.vladimir127.schultetables.domain.repository.room.GameRecordDao;
import com.github.vladimir127.schultetables.domain.repository.room.GameRecordEntity;

import java.util.List;

public class SchulteCombinedRepositoryImpl implements SchulteRepository {
    private SchulteLocalRepositoryImpl localRepo;
    private SchulteWebRepositoryImpl webRepo;

    private final String TAG = "SchulteCombinedRepo";

    public SchulteCombinedRepositoryImpl(GameRecordDao dao) {
        webRepo = new SchulteWebRepositoryImpl();
        localRepo = new SchulteLocalRepositoryImpl(dao);
    }

    @Override
    public void loadRecord(int level, boolean ascending, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
        Log.d(TAG, "loadRecord: level = " + level + ", ascending = " + ascending + ". Пытаемся загрузить рекорд из локального хранилища");

        // Сначала пытаемся загрузить рекорд из локального хранилища
        localRepo.loadRecord(level, ascending,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int count) {
                        Log.d(TAG, "loadRecord.onSuccess: ответ от локального репозитория успешный");

                        // Если в локальном хранилище не нашлось рекорда для этой
                        // игры, пытаемся загрузить его из веб-репозитория
                        if (count == -1) {
                            Log.d(TAG, "loadRecord.onSuccess: count == -1, в локальном хранилище не нашлось рекорда для этой игры, пытаемся загрузить его из веб-репозитория");

                            loadRecordFromServer(level, ascending, onSuccessListener, onErrorListener);
                        }

                        // Если же в локальном хранилище рекорд нашёлся, пробрасываем
                        // его в вызывающий метод через onSuccess
                        else {
                            Log.d(TAG, "loadRecord.onSuccess: count == " + count + ", в локальном хранилище нашёлся рекорд, пробрасываем его в колбэк onSuccess");
                            onSuccessListener.onSuccess(count);
                        }
                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "loadRecord.onError: Не удалось загрузить рекорд из локального репозитория");
                        t.printStackTrace();
                        onErrorListener.onError(t);
                    }
                });
    }

    @Override
    public void saveRecord(int level, boolean ascending, int count, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
        Log.d(TAG, "saveRecord: level = " + level + ", ascending = " + ascending + ", count = " + count + ". Сначала записываем рекорд в локальный репозиторий");

        // Сначала записываем рекорд в локальный репозиторий
        localRepo.saveRecord(level, ascending, count,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int count) {
                        Log.d(TAG, "saveRecord.onSuccess: успешная запись в локальный репозиторий. Выполняем запись в веб-репозиторий");

                        // В случае успешной записи в локальный репозиторий выполняем запись в веб-репозиторий
                        saveRecordToServer(level, ascending, count, onSuccessListener, onErrorListener);
                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "saveRecord.onError: неуспешная запись в локальный репозиторий");
                        t.printStackTrace();
                        onErrorListener.onError(t);
                    }
                }
        );
    }

    private void loadRecordFromServer(int level, boolean ascending, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
        webRepo.loadRecord(level, ascending,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int count) {
                        Log.d(TAG, "loadRecordFromServer.onSuccess: ответ от веб-репозитория успешный");

                        // Если рекорд не нашёлся и в веб-репозитории, создаём
                        // новый рекорд со значением 0. Он будет сохранён в
                        // локальном репозитории, и программа больше не будет
                        // лезть на сервер в поисках обновлённых данных, как в
                        // изначальном случае, если в локальном хранилище не
                        // нашлось нужной записи, возвращалось значение -1
                        if (count == -1) {
                            Log.d(TAG, "loadRecordFromServer.onSuccess: count == -1, значит, на сервере данных нет. Присваиваем ему значение 0, чтобы больше не лазить на сервер в поисках этих данных");
                            count = 0;
                        } else {
                            Log.d(TAG, "loadRecordFromServer.onSuccess: count == " + count);
                        }

                        // Пробрасываем эти данные во фрагмент, заодно сохраняя их в кэш
                        Log.d(TAG, "loadRecordFromServer.onSuccess: сохраняем рекорд, полученный с сервера, в локальный репозиторий");
                        localRepo.saveRecord(level, ascending, count,
                                onSuccessListener,
                                onErrorListener);
                        onSuccessListener.onSuccess(count);
                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "loadRecordFromServer.onError: ответ от веб-репозитория неуспешный");
                        t.printStackTrace();
                        onErrorListener.onError(t);
                    }
                });
    }

    private void saveRecordToServer(int level, boolean ascending, int count, OnSuccessListener onSuccessListener, OnErrorListener onErrorListener) {
        webRepo.saveRecord(level, ascending, count,
                new OnSuccessListener() {
                    @Override
                    public void onSuccess(int count) {
                        Log.d(TAG, "saveRecordToServer.onSuccess: успешная запись в веб-репозиторий.");
                        Log.d(TAG, "saveRecordToServer.onSuccess: Вызываем в локальном репозитории метод synchronize");
                        localRepo.synchronize(level, ascending);
                        onSuccessListener.onSuccess(count);
                    }
                },

                new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "saveRecordToServer.onError: неуспешная запись в веб-репозиторий");
                        t.printStackTrace();
                        onErrorListener.onError(t);
                    }
                });
    }

    public void synchronizeRecords() {
        List<GameRecordEntity> unsyncRecords = localRepo.getUnsyncronizedRecords();

        for (GameRecordEntity entity : unsyncRecords) {

            // Так как при синхронизации нам понадобится сравнивать рекорд с сервера
            // с рекордом из БД, получааем рекорд с сервера (рекорд из БД у нас уже есть)
            webRepo.loadRecord(Integer.parseInt(entity.getLevelName()), entity.isAscending(),
                    new OnSuccessListener() {
                        @Override
                        public void onSuccess(int count) {
                            int localCount = entity.getRecord();

                            // Если количество равно -1, значит, на сервере нет
                            // рекорда, и мы просто записываем рекорд из базы на
                            // сервер, как это делалось раньше.
                            if (count == -1) {
                                webRepo.saveRecord(Integer.parseInt(entity.getLevelName()), entity.isAscending(), entity.getRecord(),
                                        new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(int count) {
                                                localRepo.synchronize(Integer.parseInt(entity.getLevelName()), entity.isAscending());
                                            }
                                        },

                                        new OnErrorListener() {
                                            @Override
                                            public void onError(Throwable t) {
                                                Log.d(TAG, "saveRecordToServer.onError: неуспешная запись в веб-репозиторий");
                                                t.printStackTrace();
                                            }
                                        });
                            } else {
                                // Если же на сервере есть рекорд, сравниваем рекорд из базы данных с рекордом с сервера

                                if (localCount == 0 || localCount < count) {
                                    // Если рекорд из базы побивает рекорд с сервера (время в базе меньше чем время на сервере), также записываем его на сервер
                                    webRepo.saveRecord(Integer.parseInt(entity.getLevelName()), entity.isAscending(), entity.getRecord(),
                                            new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(int count) {
                                                    localRepo.synchronize(Integer.parseInt(entity.getLevelName()), entity.isAscending());
                                                }
                                            },

                                            new OnErrorListener() {
                                                @Override
                                                public void onError(Throwable t) {
                                                    Log.d(TAG, "saveRecordToServer.onError: неуспешная запись в веб-репозиторий");
                                                    t.printStackTrace();
                                                }
                                            });
                                } else {
                                    // Если же рекорд с сервера побивает рекорд из базы, тогда, наоборот, нужно записать новое значение в локальном репозитории
                                    localRepo.saveRecord(Integer.parseInt(entity.getLevelName()), entity.isAscending(), count,
                                            new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(int count) {
                                                    localRepo.synchronize(Integer.parseInt(entity.getLevelName()), entity.isAscending());

                                                    // В случае успешной записи в локальный репозиторий выполняем запись в веб-репозиторий
                                                    //saveRecordToServer(level, ascending, count, onSuccessListener, onErrorListener);
                                                }
                                            },

                                            new OnErrorListener() {
                                                @Override
                                                public void onError(Throwable t) {
                                                    Log.d(TAG, "saveRecord.onError: неуспешная запись в локальный репозиторий");
                                                    t.printStackTrace();
                                                    //onErrorListener.onError(t);
                                                }
                                            }
                                    );
                                }
                            }
                        }
                    },

                    new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            Log.d(TAG, "loadRecordFromServer.onError: ответ от веб-репозитория неуспешный");
                            t.printStackTrace();
                            //onErrorListener.onError(t);
                        }
                    }
            );
        }
    }
}
