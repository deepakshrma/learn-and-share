package com.dbs.replsdk.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MessageDao {
    @Query("select * from tbl_message")
    Flowable<List<Message>> getAllMessage();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message message);

    @Query("select * from tbl_message")
    List<Message> getAll();

    @Query("delete from tbl_message")
    void deleteAll();
}
