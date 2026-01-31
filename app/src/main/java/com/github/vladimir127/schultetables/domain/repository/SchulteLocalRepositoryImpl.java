package com.github.vladimir127.schultetables.domain.repository;

import static com.github.vladimir127.schultetables.utils.Constants.NAME_SCHULTE;

import android.util.Log;

import com.github.vladimir127.schultetables.domain.repository.room.GameRecordDao;
import com.github.vladimir127.schultetables.domain.repository.room.GameRecordEntity;

import java.util.List;

public class SchulteLocalRepositoryImpl implements SchulteRepository {
    private final String TAG = "SchulteLocalRepo";

    private GameRecordDao dao;

    public SchulteLocalRepositoryImpl(GameRecordDao dao) {
        this.dao = dao;
    }

    @Override
    public void loadRecord(int level,
                           boolean ascending,
                           OnSuccessListener onSuccessListener,
                           OnErrorListener onErrorListener) {

        // Запрашиваем запись из базы. Если запись равна null, возвращаем -1.
        // Таким образом CombinedRepository поймёт, что в локальном
        // репозитории нет данных, и надо обращаться к серверу.

        Log.d(TAG, "loadRecord: level = " + level + ", ascending = " + ascending);
        Log.d(TAG, "loadRecord: пытаемся получить запись из DAO");
        GameRecordEntity entity = dao.getSchulteRecord(String.valueOf(level), ascending);

        // При использовании одного только локального репозитория значение count должно быть равно 0, а вместе с комбинированным - минус 1.
        //int count = 0;
        int count = -1;

        if (entity != null) {
            count = entity.getRecord();
            Log.d(TAG, "loadRecord: entity != null, рекорд равен = " + count + ", возвращаем его через Callback");
        } else {
            Log.d(TAG, "loadRecord: entity == null, возвращаем -1");
        }

        onSuccessListener.onSuccess(count);
    }

    @Override
    public void saveRecord(int level,
                           boolean ascending,
                           int count,
                           OnSuccessListener onSuccessListener,
                           OnErrorListener onErrorListener) {
        // При сохранении рекорда сначала необходимо выяснить, есть ли запись
        // с такими параметрами в базе данных, чтобы в зависимости от этого
        // решить, добавлять новую запись или обновлять существующую

        Log.d(TAG, "saveRecord: level = " + level + ", ascending = " + ascending + ", count = " + count);
        Log.d(TAG, "saveRecord: пытаемся получить запись из DAO");
        GameRecordEntity entity = dao.getSchulteRecord(String.valueOf(level), ascending);

        if (entity == null) {
            Log.d(TAG, "saveRecord: entity == null, создаём новую запись");

            // TODO: В будущем таблицы для всех игр тоже надо разделить, чтобы не передавать сюда лишне параметры
            entity = new GameRecordEntity("", NAME_SCHULTE, String.valueOf(level), count, false, ascending);

            // Сразу же устанавливаем значение свойства synchronized в true. Это
            // свойство указывает, записано ли на сервер данное значение из базы
            // данных. Поэтому при обновлении существующего рекорда со стороны
            // пользователя мы устанавливаем этому свойству значение false, чтобы
            // после этого (сразу же или при появлении интернета) записать это
            // значение и на сервер. И в случае успешной записи на сервер мы снова
            // отыскиваем данную запись и устанавливаем ей значение synchronized
            // в true. Но при обновлении рекорда со стороны пользователя всегда
            // будет происходить не создание новой записи, а обновление уже
            // существующей, поскольку новая запись в любом случае создастся при
            // первом обращении к серверу: если рекорд на сервере нашёлся, в базу
            // будет записано значение этого рекорда, а если не нашёлся - в базе
            // будет создана запись со значением 0, чтобы приложение больше не
            // лазило на сервер в поисках этой записи. Поэтому в этой ветке мы в
            // любом случае получаем данные с сервера, а не от пользователя, а
            // значит, можем сразу установить свойству synchronized значение true.

            // Обновлено: это правило не работает для случая, когда пользователь
            // впервые сыграл в игру при выключенном интернете. В этом случае рекорд
            // сохраняется в локальный репозиторий, но не на сервер. И именно записывается
            // впервые, а не обновляется уже существующая запись. При появлении
            // интернета должна выполниться синхронизация, но если сразу установить
            // значение synchronized в true, то программа просто не находит несинхронизированные
            // записи и ничего не обновляет на сервере. Поэтому всё-таки устанавливаем
            // synchronized в false.
            entity.setSynchronized(false);
            long id = dao.insertGameRecord(entity);
        } else {
            Log.d(TAG, "saveRecord: entity != null, старый рекорд равен " + entity.getRecord() + ", обновляем существующую запись");
            entity.setSynchronized(false);
            entity.setRecord(count);
            dao.updateGameRecord(entity);
        }

        // Вызываем onSuccessListener для того, чтобы CombinedRepository
        // среагировал на успешную запись в базу данных и выполнил запись на сервер
        onSuccessListener.onSuccess(count);
    }

    public void synchronize(int level, boolean ascending) {
        Log.d(TAG, "synchronize: получаем из базы данных запись о рекорде. level = " + level + ", ascending = " + ascending);
        GameRecordEntity recordEntity = dao.getSchulteRecord(String.valueOf(level), ascending);

        if (recordEntity != null) {
            Log.d(TAG, "synchronize: запись найдена. Устанавливаем ей ");
            recordEntity.setSynchronized(true);
            dao.updateGameRecord(recordEntity);
        } else {
            Log.d(TAG, "synchronize: запись не найдена");
        }
    }

    public List<GameRecordEntity> getUnsyncronizedRecords() {
        List<GameRecordEntity> unsyncRecords = dao.getUnsynchronizedSchulteRecords();
        int count = unsyncRecords.size();
        return unsyncRecords;
    }

    // TODO: Методы синхронизации
}
