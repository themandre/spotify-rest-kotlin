package com.elpan.domain.service.impl

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.SpotifyCredentials
import com.adamratzman.spotify.models.Artist
import com.adamratzman.spotify.models.PagingObject
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.models.SpotifySearchResult
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market
import com.elpan.domain.service.SearchService

class SearchServiceImpl() : SearchService {
    private var api: SpotifyAppApi? = null

    override suspend fun buildSearchAPI(credentials: SpotifyCredentials) {
        api = spotifyAppApi(credentials.clientId.toString(), credentials.clientSecret.toString()).build()
    }

    override suspend fun userSearch(credentials: SpotifyCredentials, userQuery: String): SpotifyPublicUser? {
        buildSearchAPI(credentials)
        return api!!.users.getProfileRestAction(userQuery).complete()
    }

    override suspend fun trackSearch(credentials: SpotifyCredentials, searchQuery: String): SpotifySearchResult {
        buildSearchAPI(credentials)
        return api!!.search.searchAllTypesRestAction(searchQuery, 50, 1, market = Market.CO).complete()
    }

    override suspend fun artistSearch(credentials: SpotifyCredentials, artistQuery: String): PagingObject<Artist> {
        buildSearchAPI(credentials)
        return api!!.search.searchArtistRestAction(artistQuery, 50, 1, market = Market.CO).complete()
    }
}