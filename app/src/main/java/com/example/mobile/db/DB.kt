package com.example.mobile.db

import android.content.Context
import android.graphics.Path.Op
import android.util.Log
import androidx.room.*
import com.example.mobile.DTO.Article
import com.example.mobile.DTO.ArticleData
import com.example.mobile.helpful.PreferencesHelper

@Entity
class Archive(val name : String, val serverId : Int, val type : String, val user : String, @PrimaryKey(autoGenerate = true) val id: Int = 0){
    @Ignore
    val data : ArrayList<ArchiveData> = ArrayList()
}

@Entity
class ArchiveData( var text : String?, val image : ByteArray?, val number : Int, var owner : Int, val serverId : Int, @PrimaryKey(autoGenerate = true) val id: Int = 0)

@Dao
interface ArchiveDao{
    @Insert
    fun insert(obj : Archive) : Long

    @Update
    fun update(obj : Archive)

    @Delete
    fun delete(obj : Archive)

    @Query("SELECT * FROM Archive WHERE user LIKE :user")
    fun getAll(user : String): List<Archive>

    @Query("SELECT * FROM Archive WHERE user LIKE :user AND type LIKE 'add'")
    fun getNew(user : String): Archive

    @Query("SELECT * FROM Archive WHERE id LIKE :id")
    fun get(id : Int): Archive
}

@Dao
interface ArchiveDataDao{
    @Insert
    fun insert(obj : ArchiveData) : Long

    @Update
    fun update(obj : ArchiveData)

    @Delete
    fun delete(obj : ArchiveData)

    @Query("SELECT * FROM ArchiveData WHERE owner LIKE :owner")
    fun getAll(owner : Int): List<ArchiveData>
}

@Database(
    entities = [Archive::class, ArchiveData::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun archive(): ArchiveDao
    abstract fun data(): ArchiveDataDao
}

object DB {
    private lateinit var db : AppDatabase
    private lateinit var archive : ArchiveDao
    private lateinit var data : ArchiveDataDao

    fun setup(context: Context){
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "archiveDB"
        ).allowMainThreadQueries().build()
        archive = db.archive()
        data = db.data()
    }

    fun getAll(user : String) = archive.getAll(user = user)

    fun getNew(user : String) = archive.getNew(user = user)

    fun get(id : Int): Archive{
        val archive = archive.get(id)
        val data = when (archive.type){
            "add" -> data.getAll(-archive.id)
            else -> data.getAll(archive.serverId)
        }
        for (d in data){
            archive.data.add(d)
        }
        return archive
    }

    fun insert(a : Archive){
        val id = archive.insert(a)
        for (d in a.data){
            d.owner = when (a.type){
                "add" -> -id.toInt()
                else -> a.serverId
            }
            data.insert(d)
        }
    }

    fun delete(a: Archive){
        archive.delete(a)
        val aData = when (a.type){
            "add" -> data.getAll(-a.id)
            else -> data.getAll(a.serverId)
        }
        for (d in aData){
            data.delete(d)
        }
    }
}