package com.ekyrizky.moviecatalogue.core.utils

import com.ekyrizky.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.ekyrizky.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.MovieDetailResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.movie.PopularMoviesResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.PopularTvShowsResponse
import com.ekyrizky.moviecatalogue.core.data.source.remote.response.tvshow.TvShowDetailResponse
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDomain
import com.ekyrizky.moviecatalogue.core.domain.model.tvshow.TvShowDomain

object DataDummy {
    fun generateRemoteDummyMovies(): List<PopularMoviesResponse> {
        val movies = ArrayList<PopularMoviesResponse>()

        movies.add(
            PopularMoviesResponse(
            1,
            "A Star Is Born",
            "2018",
            7.5,
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg")
        )

        movies.add(
            PopularMoviesResponse(
            2,
            "Alita: Battle Angle",
            "2019",
            7.1,
            "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
            "/xRWht48C2V8XNfzvPehyClOvDni.jpg",
            "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg")
        )

        movies.add(
            PopularMoviesResponse(
            3,
            "How to Train Your Dragon: The Hidden World",
            "2019",
            7.8,
            "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
            "/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg",
            "/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg")
        )

        movies.add(
            PopularMoviesResponse(
            4,
            "Bohemian Rhapsody",
            "2018",
            8.0,
            "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
            "/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
            "/jNUpYq2jRSwQM89vST9yQZelMSu.jpg")
        )

        movies.add(
            PopularMoviesResponse(
            5,
            "Cold Pursuit",
            "2019",
            5.6,
            "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
            "/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
            "/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg")
        )

        return movies
    }

    fun generateRemoteDummyMovieDetail(): MovieDetailResponse {
        return  MovieDetailResponse(
            1,
            "A Star Is Born",
            "",
            "2018",
            136,
            7.5,
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg"
        )
    }

    fun generateRemoteDummyTvShows(): List<PopularTvShowsResponse> {
        val tvShows = ArrayList<PopularTvShowsResponse>()

        tvShows.add(
            PopularTvShowsResponse(
            1,
            "The Arrow",
            "2012",
            6.6,
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg")
        )

        tvShows.add(
            PopularTvShowsResponse(
            2,
            "Doom Patrol",
            "2019",
            7.6,
            "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
            "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
            "/sAzw6I1G9JUxm86KokIDdQeWtaq.jpg")
        )

        tvShows.add(
            PopularTvShowsResponse(
            3,
            "Dragon Ball",
            "1986",
            8.1,
            "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish.",
            "/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
            "/yXggMemopUDHwPgmi6X9Wh2BQra.jpg")
        )

        tvShows.add(
            PopularTvShowsResponse(
            4,
            "Family Guy",
            "1999",
            6.9,
            "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
            "/qlClTwL0GSGZUH7xwJU5PER5wnJ.jpg",
            "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg")
        )

        tvShows.add(
            PopularTvShowsResponse(
            5,
            "The Flash",
            "2014",
            7.6,
            "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
            "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg")
        )

        return tvShows
    }

    fun generateRemoteDummyTvShowDetail(): TvShowDetailResponse {
        return TvShowDetailResponse(
            1,
            "The Arrow",
            "Heroes fall. Legends rise.",
            "2012",
            listOf(42),
            6.6,
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg"
        )
    }

    fun generateDummyMovies(): ArrayList<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(
            MovieEntity(
                    1,
                    "A Star Is Born",
                    "",
                    "2018",
                    136,
                    7.5,
                    "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                    "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
                    "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg",
                    false
            )
        )

        movies.add(
            MovieEntity(
                    2,
                    "Alita: Battle Angle",
                    "An angel falls. A warrior rises.",
                    "2019",
                    122,
                    7.1,
                    "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                    "/xRWht48C2V8XNfzvPehyClOvDni.jpg",
                    "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg",
                    false
            )
        )

        movies.add(
            MovieEntity(
                    3,
                    "How to Train Your Dragon: The Hidden World",
                    "The friendship of a lifetime",
                    "2019",
                    104,
                    7.8,
                    "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
                    "/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg",
                    "/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg",
                    false
            )
        )

        movies.add(
            MovieEntity(
                    4,
                    "Bohemian Rhapsody",
                    "Fearless lives forever",
                    "2018",
                    135,
                    8.0,
                    "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                    "/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
                    "/jNUpYq2jRSwQM89vST9yQZelMSu.jpg",
                    false
            )
        )


        movies.add(
            MovieEntity(
                    5,
                    "Cold Pursuit",
                    "Meet Nels Coxman. Citizen of the Year.",
                    "2019",
                    119,
                    5.6,
                    "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
                    "/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
                    "/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg",
                    false
            )
        )

        return movies
    }

    fun generateDummyMovieDetail(): MovieEntity {
        return MovieEntity(
            1,
            "A Star Is Born",
            "",
            "2018",
            136,
            7.5,
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg",
            false
        )
    }

    fun generateDummyMovieDomain(): MovieDomain {
        return MovieDomain(
                1,
                "A Star Is Born",
                "",
                "2018",
                136,
                7.5,
                "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
                "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg",
                false
        )
    }

    fun generateDummyTvShows():ArrayList<TvShowEntity> {
        val tvShows =ArrayList<TvShowEntity>()

        tvShows.add(
                TvShowEntity(
                    1,
                    "The Arrow",
                    "Heroes fall. Legends rise.",
                    "2012",
                    42,
                    6.6,
                    "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                    "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
                    "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg",
                    false
            )
        )

        tvShows.add(
                TvShowEntity(
                    2,
                    "Doom Patrol",
                    "",
                    "2019",
                    49,
                    7.6,
                    "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
                    "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
                    "/sAzw6I1G9JUxm86KokIDdQeWtaq.jpg",
                    false
            )
        )

        tvShows.add(
            TvShowEntity(
                3,
                "Dragon Ball",
                "",
                "1986",
                25,
                8.1,
                "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish.",
                "/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
                "/yXggMemopUDHwPgmi6X9Wh2BQra.jpg",
                false
            )
        )

        tvShows.add(
            TvShowEntity(
                4,
                "Family Guy",
                "Parental Discretion Advised, that's how you know it's good",
                "1999",
                22,
                6.9,
                "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
                "/qlClTwL0GSGZUH7xwJU5PER5wnJ.jpg",
                "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg",
                 false
            )
        )

        tvShows.add(
            TvShowEntity(
                5,
                "The Flash",
                "The fastest man alive.",
                "2014",
                44,
                7.6,
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
                "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg",
                false
            )
        )

        return tvShows
    }

    fun generateDummyTvShowDetail(): TvShowEntity {
        return TvShowEntity(
            1,
            "The Arrow",
            "Heroes fall. Legends rise.",
            "2012",
            42,
            6.6,
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg",
            false
        )
    }

    fun generateDummyTvShowDomain(): TvShowDomain {
        return TvShowDomain(
                1,
                "The Arrow",
                "Heroes fall. Legends rise.",
                "2012",
                42,
                6.6,
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
                "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg",
                false
        )
    }
}