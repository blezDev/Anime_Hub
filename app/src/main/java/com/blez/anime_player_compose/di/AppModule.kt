package com.blez.anime_player_compose.di

import android.content.Context
import com.blez.anime_player_compose.common.util.Constants
import com.blez.anime_player_compose.feature_dashboard.data.remote.DashboardAPI
import com.blez.anime_player_compose.feature_dashboard.data.repository.DashboardRepositoryImpl
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.DashboardUseCases
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.MoviesAddedUseCase
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.PopularAnimeUseCase
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.RecentReleaseUseCase
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.TopAiringUseCase
import com.blez.anime_player_compose.feature_detail_info.data.remote.InfoAPI
import com.blez.anime_player_compose.feature_detail_info.data.repository.InfoRepositoryImpl
import com.blez.anime_player_compose.feature_detail_info.domain.repository.InfoRepository
import com.blez.anime_player_compose.feature_detail_info.domain.use_cases.DetailsUseCases
import com.blez.anime_player_compose.feature_detail_info.domain.use_cases.InfoDetails
import com.blez.anime_player_compose.feature_search.data.remote.SearchAPI
import com.blez.anime_player_compose.feature_search.data.repositorry.SearchRepositoryImpl
import com.blez.anime_player_compose.feature_search.domain.repository.SearchRepository
import com.blez.anime_player_compose.feature_search.domain.use_cases.SearchUseCase
import com.blez.anime_player_compose.feature_video.data.remote.WatchAPI
import com.blez.anime_player_compose.feature_video.data.repository.VideoRepositoryImpl
import com.blez.anime_player_compose.feature_video.domain.repository.VideoRepository
import com.blez.anime_player_compose.feature_video.domain.use_cases.VideoLinksUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .build()
    }

    @Singleton
    @Provides
    fun applicationContext(@ApplicationContext context: Context): Context = context


    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Singleton
    @Provides
    fun providesDashboardAPI(retrofit: Retrofit): DashboardAPI {
        return retrofit
            .create(DashboardAPI::class.java)
    }


    @Singleton
    @Provides
    fun providesInfoAPI(retrofit: Retrofit): InfoAPI {
        return retrofit
            .create(InfoAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesWatchAPI(retrofit: Retrofit): WatchAPI {
        return retrofit
            .create(WatchAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesSearchAPI(retrofit: Retrofit): SearchAPI {
        return retrofit.create(SearchAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesDashboardRepository(
        context: Context,
        dashboardAPI: DashboardAPI
    ): DashboardRepository {
        return DashboardRepositoryImpl(context = context, dashboardAPI = dashboardAPI)
    }

    @Singleton
    @Provides
    fun providesInfoRepository(context: Context, infoAPI: InfoAPI): InfoRepository {
        return InfoRepositoryImpl(context = context, infoAPI = infoAPI)
    }

    @Singleton
    @Provides
    fun providesWatchVideoRepository(context: Context, watchAPI: WatchAPI): VideoRepository {
        return VideoRepositoryImpl(context = context, api = watchAPI)
    }


    @Singleton
    @Provides
    fun providesSearchRepository(context: Context, searchAPI: SearchAPI): SearchRepository {
        return SearchRepositoryImpl(context = context, api = searchAPI)
    }

    @Singleton
    @Provides
    fun providesRecentReleaseUseCase(repository: DashboardRepository): RecentReleaseUseCase {
        return RecentReleaseUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesTopAiringUseCase(repository: DashboardRepository): TopAiringUseCase {
        return TopAiringUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesInfoUseCase(repository: InfoRepository): InfoDetails {
        return InfoDetails(repository)
    }

    @Singleton
    @Provides
    fun providesRecentAddedUseCase(repository: DashboardRepository): MoviesAddedUseCase {
        return MoviesAddedUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesCompletedAnimeUseCase(repository: DashboardRepository): PopularAnimeUseCase {
        return PopularAnimeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideVideoLink(repository: VideoRepository): VideoLinksUseCases {
        return VideoLinksUseCases(repository)
    }

    @Singleton
    @Provides
    fun providesDashboardUseCases(
        releaseUseCase: RecentReleaseUseCase,
        topAiringUseCase: TopAiringUseCase,
        infoDetails: InfoDetails,
        moviesAddedUseCase: MoviesAddedUseCase,
        popularAnimeUseCase: PopularAnimeUseCase,
        videoLinksUseCases: VideoLinksUseCases
    ): DashboardUseCases {
        return DashboardUseCases(
            releaseUseCase,
            topAiringUseCase,
            infoDetails,
            moviesAddedUseCase,
            popularAnimeUseCase,
            videoLinksUseCases
        )
    }

    @Singleton
    @Provides
    fun providesDetailsUseCases(infoDetails: InfoDetails): DetailsUseCases {
        return DetailsUseCases(infoDetails)

    }

    @Singleton
    @Provides
    fun providesSearchUseCases(repository: SearchRepository): SearchUseCase {
        return SearchUseCase(repository)

    }


}