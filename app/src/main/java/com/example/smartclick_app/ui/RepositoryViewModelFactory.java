package com.example.smartclick_app.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;

public class RepositoryViewModelFactory<R> implements ViewModelProvider.Factory  {

    private final Class<R> repositoryClass;
    private final R repository;

    public RepositoryViewModelFactory(Class<R> repositoryClass, R repository) {
        this.repositoryClass = repositoryClass;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!RepositoryViewModel.class.isAssignableFrom(modelClass))
            throw new IllegalArgumentException("Unknown class " + modelClass);
        try {
            Constructor<T> constructor = modelClass.getConstructor(repositoryClass);
            return constructor.newInstance(repository);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an instance of class " + modelClass, e);
        }
    }
}
