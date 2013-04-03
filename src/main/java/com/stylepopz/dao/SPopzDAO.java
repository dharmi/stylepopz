package com.stylepopz.dao;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.stylepopz.common.exception.ApplicationException;
import com.stylepopz.model.DataObject;

@Repository
public class SPopzDAO {

	@Autowired
	MongoTemplate mongoTemplate;
	MongoOperations mongoOperation = null;

	@PostConstruct
	public void init(){
		mongoOperation = (MongoOperations) mongoTemplate;
	}

	public void insertData(DataObject obj){
		try{
			if(!mongoOperation.collectionExists(obj.getClass()))
				mongoOperation.createCollection(obj.getClass());
			mongoOperation.save(obj);
		}catch(DataAccessResourceFailureException ex){
			throw new ApplicationException(ex.getLocalizedMessage());
		}
	}
}
