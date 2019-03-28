package com.bge.model.dao;

import com.bge.exception.DBException;

public interface DAOInterface<T> {
	int count() throws DBException;
	T[] getAll() throws DBException;
	T find(int id) throws DBException;
	void insert(T element) throws DBException;
	void delete(int id) throws DBException;
	void delete(T element) throws DBException;
	void update(T element) throws DBException;
	void close() throws DBException;
}
