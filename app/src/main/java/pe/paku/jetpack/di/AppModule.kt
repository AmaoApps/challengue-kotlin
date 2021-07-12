package pe.paku.jetpack.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.paku.jetpack.api.BreakingBadService
import pe.paku.jetpack.db.SerieDatabase
import pe.paku.jetpack.db.dao.SerieCharacterDao
import pe.paku.jetpack.repositories.MainRepository
import pe.paku.jetpack.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://www.breakingbadapi.com/api/"

    @Singleton
    @Provides
    fun provideSerieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SerieDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideSerieCharacterDao(db: SerieDatabase) = db.getSerieCharacterDAO()


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BreakingBadService = retrofit.create(
        BreakingBadService::class.java)

    @Singleton
    @Provides
    fun providesRepository(daoCharacter: SerieCharacterDao, apiService: BreakingBadService) = MainRepository(daoCharacter, apiService)
}