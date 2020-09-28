package com.supinfo.suppictures.dao;

public abstract class DAO<T> {

    public abstract T create(T obj);


    public abstract Boolean delete(T obj);


    public abstract T update(T obj);


    public abstract T find(Long id);

}

