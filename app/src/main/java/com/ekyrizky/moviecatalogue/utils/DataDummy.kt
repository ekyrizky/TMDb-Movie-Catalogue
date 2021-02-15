package com.ekyrizky.moviecatalogue.utils

import com.ekyrizky.moviecatalogue.data.source.local.entity.ContentEntity
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse

object DataDummy {
    fun generateRemoteDummyMovies(): List<MovieResponse> {
        val movies = ArrayList<MovieResponse>()

        movies.add(
            MovieResponse(
            1,
            "A Star Is Born",
            "",
            "2018",
            136,
            7.5,
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg")
        )

        movies.add(
            MovieResponse(
            2,
            "Alita: Battle Angle",
            "An angel falls. A warrior rises.",
            "2019",
            122,
            7.1,
            "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
            "/xRWht48C2V8XNfzvPehyClOvDni.jpg",
            "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg")
        )

        movies.add(
            MovieResponse(
            3,
            "How to Train Your Dragon: The Hidden World",
            "The friendship of a lifetime",
            "2019",
            104,
            7.8,
            "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
            "/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg",
            "/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg")
        )

        movies.add(
            MovieResponse(
            4,
            "Bohemian Rhapsody",
            "Fearless lives forever",
            "2018",
            135,
            8.0,
            "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
            "/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
            "/jNUpYq2jRSwQM89vST9yQZelMSu.jpg")
        )


        movies.add(
            MovieResponse(
            5,
            "Cold Pursuit",
            "Meet Nels Coxman. Citizen of the Year.",
            "2019",
            119,
            5.6,
            "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
            "/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
            "/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg")
        )

        movies.add(
            MovieResponse(
            6,
            "Aquaman",
            "Home Is Calling",
            "2018",
            144,
            6.9,
            "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
            "/5Kg76ldv7VxeX9YlcQXiowHgdX6.jpg",
            "/9QusGjxcYvfPD1THg6oW3RLeNn7.jpg")
        )

        movies.add(
            MovieResponse(
            7,
            "Spider-Main: Into the Spider-Verse",
            "More Than One Wears the Mask",
            "2018",
            117,
            8.4,
            "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension.",
            "/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
            "/7d6EY00g1c39SGZOoCJ5Py9nNth.jpg")
        )

        movies.add(
            MovieResponse(
            8,
            "Ralph Breaks the Internet",
            "Who Broke the Internet?",
            "2018",
            117,
            7.2,
            "Video game bad guy Ralph and fellow misfit Vanellope von Schweetz must risk it all by traveling to the World Wide Web in search of a replacement part to save Vanellope's video game, Sugar Rush. In way over their heads, Ralph and Vanellope rely on the citizens of the internet — the netizens — to help navigate their way, including an entrepreneur named Yesss, who is the head algorithm and the heart and soul of trend-making site BuzzzTube.",
            "/qEnH5meR381iMpmCumAIMswcQw2.jpg",
            "/qDQEQbgP3v7B9IYLAUcYexNrVYP.jpg")
        )

        movies.add(
            MovieResponse(
            9,
            "Robin Hood",
            "The legend you know. The story you don't.",
            "2018",
            116,
            5.9,
            "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
            "/AiRfixFcfTkNbn2A73qVJPlpkUo.jpg",
            "/zSJT1sKGRKcmP4I9b8dIOuepw6I.jpg")
        )

        movies.add(
            MovieResponse(
            10,
            "Avengers: Infinity War",
            "An entire universe. Once and for all.",
            "2018",
            149,
            8.3,
            "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg")
        )

        return movies
    }

    fun generateRemoteDummyTvShows():List<TvShowResponse> {
        val tvShows = ArrayList<TvShowResponse>()

        tvShows.add(
            TvShowResponse(
            1,
            "The Arrow",
            "Heroes fall. Legends rise.",
            "2012",
            listOf(42),
            6.6,
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg")
        )

        tvShows.add(
            TvShowResponse(
            2,
            "Doom Patrol",
            "",
            "2019",
            listOf(49),
            7.6,
            "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
            "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
            "/sAzw6I1G9JUxm86KokIDdQeWtaq.jpg")
        )

        tvShows.add(
            TvShowResponse(
            3,
            "Dragon Ball",
            "",
            "1986",
            listOf(25),
            8.1,
            "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish.",
            "/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
            "/yXggMemopUDHwPgmi6X9Wh2BQra.jpg")
        )

        tvShows.add(
            TvShowResponse(
            4,
            "Family Guy",
            "Parental Discretion Advised, that's how you know it's good",
            "1999",
            listOf(22),
            6.9,
            "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
            "/qlClTwL0GSGZUH7xwJU5PER5wnJ.jpg",
            "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg")
        )

        tvShows.add(
            TvShowResponse(
            5,
            "The Flash",
            "The fastest man alive.",
            "2014",
            listOf(44),
            7.6,
            "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
            "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg")
        )

        tvShows.add(
            TvShowResponse(
            6,
            "Gotham",
            "Before Batman, there was Gotham.",
            "2014",
            listOf(43),
            7.5,
            "Everyone knows the name Commissioner Gordon. He is one of the crime world's greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon's story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world's most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?",
            "/4XddcRDtnNjYmLRMYpbrhFxsbuq.jpg",
            "/7QSM3AsgWXctWBm7OFov9dGdZgt.jpg")
        )

        tvShows.add(
            TvShowResponse(
            7,
            "Grey's Anatomy",
            "The life you save may be your own.",
            "2005",
            listOf(43),
            8.2,
            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
            "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
            "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg")
        )

        tvShows.add(
            TvShowResponse(
            8,
            "Hanna",
            "",
            "2019",
            listOf(50),
            7.5,
            "This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film.",
            "/iYUtjx1EN4SVTgxd2TB4cZTGSQb.jpg",
            "/6Fzyuwn8KeMCSqRILfdrDmCvmod.jpg")
        )

        tvShows.add(
            TvShowResponse(
            9,
            "Naruto Shippuden",
            "",
            "2007",
            listOf(25),
            8.6,
            "Naruto Shippuuden is the continuation of the original animated TV series Naruto.The story revolves around an older and slightly more matured Uzumaki Naruto and his quest to save his friend Uchiha Sasuke from the grips of the snake-like Shinobi, Orochimaru. After 2 and a half years Naruto finally returns to his village of Konoha, and sets about putting his ambitions to work, though it will not be easy, as He has amassed a few (more dangerous) enemies, in the likes of the shinobi organization; Akatsuki.",
            "/hKkY4Hf5tDKCwVzzeo42vp1RxPQ.jpg",
            "/y1E6d7x8ZGujIdbf9oiE0hlxtQ3.jpg")
        )

        tvShows.add(
            TvShowResponse(
            10,
            "NCIS",
            "",
            "2003",
            listOf(45),
            7.3,
            "From murder and espionage to terrorism and stolen submarines, a team of special agents investigates any crime that has a shred of evidence connected to Navy and Marine Corps personnel, regardless of rank or position.",
            "/fi8EvaWtL5CvoielOjjVvTr7ux3.jpg",
            "/uZy2abTIozTa3Fr3TC4SVAMOT1h.jpg")
        )

        return tvShows
    }

    fun generateDummyMovies(): ArrayList<ContentEntity> {
        val movies = ArrayList<ContentEntity>()

        movies.add(
            ContentEntity(
            1,
            "A Star Is Born",
            "",
            "2018",
            136,
            7.5,
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            "/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg",
            "/mnDvPokXpvsdPcWSjNRPhiiLOKu.jpg")
        )

        movies.add(
            ContentEntity(
            2,
            "Alita: Battle Angle",
            "An angel falls. A warrior rises.",
            "2019",
            122,
            7.1,
            "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
            "/xRWht48C2V8XNfzvPehyClOvDni.jpg",
            "/8RKBHHRqOMOLh5qW3sS6TSFTd8h.jpg")
        )

        movies.add(
            ContentEntity(
            3,
            "How to Train Your Dragon: The Hidden World",
            "The friendship of a lifetime",
            "2019",
            104,
            7.8,
            "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
            "/xvx4Yhf0DVH8G4LzNISpMfFBDy2.jpg",
            "/lFwykSz3Ykj1Q3JXJURnGUTNf1o.jpg")
        )

        movies.add(
            ContentEntity(
            4,
            "Bohemian Rhapsody",
            "Fearless lives forever",
            "2018",
            135,
            8.0,
            "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
            "/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg",
            "/jNUpYq2jRSwQM89vST9yQZelMSu.jpg")
        )


        movies.add(
            ContentEntity(
            5,
            "Cold Pursuit",
            "Meet Nels Coxman. Citizen of the Year.",
            "2019",
            119,
            5.6,
            "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
            "/hXgmWPd1SuujRZ4QnKLzrj79PAw.jpg",
            "/aiM3XxYE2JvW1vJ4AC6cI1RjAoT.jpg")
        )

        movies.add(
            ContentEntity(
            6,
            "Aquaman",
            "Home Is Calling",
            "2018",
            144,
            6.9,
            "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
            "/5Kg76ldv7VxeX9YlcQXiowHgdX6.jpg",
            "/9QusGjxcYvfPD1THg6oW3RLeNn7.jpg")
        )

        movies.add(
            ContentEntity(
            7,
            "Spider-Main: Into the Spider-Verse",
            "More Than One Wears the Mask",
            "2018",
            117,
            8.4,
            "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension.",
            "/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
            "/7d6EY00g1c39SGZOoCJ5Py9nNth.jpg")
        )

        movies.add(
            ContentEntity(
            8,
            "Ralph Breaks the Internet",
            "Who Broke the Internet?",
            "2018",
            117,
            7.2,
            "Video game bad guy Ralph and fellow misfit Vanellope von Schweetz must risk it all by traveling to the World Wide Web in search of a replacement part to save Vanellope's video game, Sugar Rush. In way over their heads, Ralph and Vanellope rely on the citizens of the internet — the netizens — to help navigate their way, including an entrepreneur named Yesss, who is the head algorithm and the heart and soul of trend-making site BuzzzTube.",
            "/qEnH5meR381iMpmCumAIMswcQw2.jpg",
            "/qDQEQbgP3v7B9IYLAUcYexNrVYP.jpg")
        )

        movies.add(
            ContentEntity(
            9,
            "Robin Hood",
            "The legend you know. The story you don't.",
            "2018",
            116,
            5.9,
            "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
            "/AiRfixFcfTkNbn2A73qVJPlpkUo.jpg",
            "/zSJT1sKGRKcmP4I9b8dIOuepw6I.jpg")
        )

        movies.add(
            ContentEntity(
            10,
            "Avengers: Infinity War",
            "An entire universe. Once and for all.",
            "2018",
            149,
            8.3,
            "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg")
        )

        return movies
    }

    fun generateDummyTvShows():ArrayList<ContentEntity> {
        val tvShows =ArrayList<ContentEntity>()

        tvShows.add(
            ContentEntity(
            1,
            "The Arrow",
            "Heroes fall. Legends rise.",
            "2012",
            42,
            6.6,
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg",
            "/elbLQbocvW9vwrHRjYTSjXr5BX5.jpg")
        )

        tvShows.add(
            ContentEntity(
            2,
            "Doom Patrol",
            "",
            "2019",
            49,
            7.6,
            "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
            "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
            "/sAzw6I1G9JUxm86KokIDdQeWtaq.jpg")
        )

        tvShows.add(
            ContentEntity(
            3,
            "Dragon Ball",
            "",
            "1986",
            25,
            8.1,
            "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the mystical Dragon Balls brought her to Goku's home. Together, they set off to find all seven and to grant her wish.",
            "/tZ0jXOeYBWPZ0OWzUhTlYvMF7YR.jpg",
            "/yXggMemopUDHwPgmi6X9Wh2BQra.jpg")
        )

        tvShows.add(
            ContentEntity(
            4,
            "Family Guy",
            "Parental Discretion Advised, that's how you know it's good",
            "1999",
            22,
            6.9,
            "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
            "/qlClTwL0GSGZUH7xwJU5PER5wnJ.jpg",
            "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg")
        )

        tvShows.add(
            ContentEntity(
            5,
            "The Flash",
            "The fastest man alive.",
            "2014",
            44,
            7.6,
            "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
            "/jC1KqsFx8ZyqJyQa2Ohi7xgL7XC.jpg")
        )

        tvShows.add(
            ContentEntity(
            6,
            "Gotham",
            "Before Batman, there was Gotham.",
            "2014",
            43,
            7.5,
            "Everyone knows the name Commissioner Gordon. He is one of the crime world's greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon's story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world's most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?",
            "/4XddcRDtnNjYmLRMYpbrhFxsbuq.jpg",
            "/7QSM3AsgWXctWBm7OFov9dGdZgt.jpg")
        )

        tvShows.add(
            ContentEntity(
            7,
            "Grey's Anatomy",
            "The life you save may be your own.",
            "2005",
            43,
            8.2,
            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
            "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
            "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg")
        )

        tvShows.add(
            ContentEntity(
            8,
            "Hanna",
            "",
            "2019",
            50,
            7.5,
            "This thriller and coming-of-age drama follows the journey of an extraordinary young girl as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is. Based on the 2011 Joe Wright film.",
            "/iYUtjx1EN4SVTgxd2TB4cZTGSQb.jpg",
            "/6Fzyuwn8KeMCSqRILfdrDmCvmod.jpg")
        )

        tvShows.add(
            ContentEntity(
            9,
            "Naruto Shippuden",
            "",
            "2007",
            25,
            8.6,
            "Naruto Shippuuden is the continuation of the original animated TV series Naruto.The story revolves around an older and slightly more matured Uzumaki Naruto and his quest to save his friend Uchiha Sasuke from the grips of the snake-like Shinobi, Orochimaru. After 2 and a half years Naruto finally returns to his village of Konoha, and sets about putting his ambitions to work, though it will not be easy, as He has amassed a few (more dangerous) enemies, in the likes of the shinobi organization; Akatsuki.",
            "/hKkY4Hf5tDKCwVzzeo42vp1RxPQ.jpg",
            "/y1E6d7x8ZGujIdbf9oiE0hlxtQ3.jpg")
        )

        tvShows.add(
            ContentEntity(
            10,
            "NCIS",
            "",
            "2003",
            45,
            7.3,
            "From murder and espionage to terrorism and stolen submarines, a team of special agents investigates any crime that has a shred of evidence connected to Navy and Marine Corps personnel, regardless of rank or position.",
            "/fi8EvaWtL5CvoielOjjVvTr7ux3.jpg",
            "/uZy2abTIozTa3Fr3TC4SVAMOT1h.jpg")
        )

        return tvShows
    }
}