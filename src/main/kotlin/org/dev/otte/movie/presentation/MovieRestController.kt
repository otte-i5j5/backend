package org.dev.otte.movie.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.dev.otte.common.annotation.LimitRequest
import org.dev.otte.common.presentation.dto.DataResult
import org.dev.otte.common.security.AuthenticationFacade
import org.dev.otte.movie.command.application.MovieService
import org.dev.otte.movie.command.application.dto.MovieSaveCommandResponse
import org.dev.otte.movie.presentation.dto.FolderingRequest
import org.dev.otte.movie.presentation.dto.MovieRecommendRequest
import org.dev.otte.movie.presentation.dto.MovieSaveRequest
import org.dev.otte.movie.query.MovieQueryService
import org.dev.otte.movie.query.MovieRecommendQueryService
import org.dev.otte.movie.query.dto.MovieQueryResponse
import org.dev.otte.movie.query.dto.MovieRecommendQueryResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Movie")
@RestController
@RequestMapping("/api/v1/movie")
class MovieRestController(
    private val movieRecommendQueryService: MovieRecommendQueryService,
    private val movieService: MovieService,
    private val movieQueryService: MovieQueryService,
    private val facade: AuthenticationFacade,
) {
    @PostMapping("/recommended")
//    @LimitRequest
    @Operation(summary = "Find Recommended Movie")
    fun recommend(@RequestBody @Valid request: MovieRecommendRequest): ResponseEntity<DataResult<List<MovieRecommendQueryResponse>>> {
        val response = movieRecommendQueryService.recommend(request.toCondition(facade.getUserIdOrNull()))
        return ResponseEntity.ok(DataResult(response))
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BASIC')")
    @Operation(summary = "Save Recommended Movie")
    fun save(@RequestBody request: MovieSaveRequest): ResponseEntity<MovieSaveCommandResponse> {
        return ResponseEntity.ok(movieService.save(request.toCommand(facade.getUserId())))
    }

    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('ROLE_BASIC')")
    @Operation(summary = "Delete Recommended Movie")
    fun delete(@PathVariable movieId: Long): ResponseEntity<Any> {
        movieService.delete(movieId)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_BASIC')")
    @Operation(summary = "Find All Saved User's Movie")
    fun findAll(): ResponseEntity<DataResult<List<MovieQueryResponse>>> {
        return ResponseEntity.ok(DataResult(movieQueryService.findAll(facade.getUserId())))
    }

    @PostMapping("/{movieId}/foldering")
    @PreAuthorize("hasRole('ROLE_BASIC')")
    @Operation(summary = "Add movie to folder")
    fun foldering(
        @PathVariable movieId: Long,
        @RequestBody request: FolderingRequest
    ): ResponseEntity<Any> {
        movieService.foldering(movieId, request.folderId)
        return ResponseEntity.ok().build()
    }
}
