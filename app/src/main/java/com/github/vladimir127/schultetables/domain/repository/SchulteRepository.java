package com.github.vladimir127.schultetables.domain.repository;

public interface SchulteRepository {
    void loadRecord(int level,
                   boolean ascending,
                   OnSuccessListener onSuccessListener,
                   OnErrorListener onErrorListener);

    void saveRecord(int level,
                    boolean ascending,
                    int count,
                    OnSuccessListener onSuccessListener,
                    OnErrorListener onErrorListener);
}
