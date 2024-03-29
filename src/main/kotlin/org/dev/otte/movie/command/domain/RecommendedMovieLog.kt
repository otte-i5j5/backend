package org.dev.otte.movie.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.dev.otte.common.domain.BaseRootEntity
import org.dev.otte.common.util.StringListConverter

@Entity
@Table(name = "recommended_movie_log")
class RecommendedMovieLog(
    @Column
    val movieName: String,

    @Column
    @Convert(converter = StringListConverter::class)
    val keywords: List<String>,

    @Column
    val posterImageUrl: String?,

    @Column
    val releaseDate: String?
) : BaseRootEntity<RecommendedMovieLog>()