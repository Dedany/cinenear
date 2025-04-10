package com.dedany.cinenear.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dedany.cinenear.data.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {


    @Query("SELECT * FROM Movie")
     fun fetchPopularMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findMovieById(id: Int):Flow<Movie?>

    @Query("SELECT COUNT(*) FROM Movie")
    suspend fun countMovies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)


}