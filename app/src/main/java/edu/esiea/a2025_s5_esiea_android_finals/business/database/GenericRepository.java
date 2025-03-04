package edu.esiea.a2025_s5_esiea_android_finals.business.database;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.esiea.a2025_s5_esiea_android_finals.business.DAO.interfaces.IDAO;

public class GenericRepository<T> {
    private final IDAO<T> dao;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public GenericRepository(IDAO<T> dao) {
        this.dao = dao;
    }

    public void insert(T entity) {
        executorService.execute(() -> dao.insert(entity));
    }

    public void update(T entity) {
        executorService.execute(() -> dao.update(entity));
    }

    public void delete(T entity) {
        executorService.execute(() -> dao.delete(entity));
    }

    public LiveData<List<T>> getAll() {
        return dao.getAll();
    }

    public LiveData<T> getById(int id) {
        return dao.getById(id);
    }
}
