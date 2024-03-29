package org.dev.otte.movie.command.domain

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.dev.otte.common.util.StringListConverter

@Entity
@Table(name = "clova_studio_engine_setting")
class ClovaStudioEngineSetting(
    @Column(length = 2000)
    val text: String, // 프롬프트 + 질문

    @Column
    val start: String,

    @Column
    val restart: String,

    @Column
    val includeTokens: Boolean,

    @Column
    val topP: Double,

    @Column
    val topK: Int,

    @Column
    val maxTokens: Int,

    @Column
    val temperature: Double,

    @Column
    val repeatPenalty: Double,

    @Column
    @Convert(converter = StringListConverter::class)
    val stopBefore: List<String> = listOf(),

    @Column
    val includeAiFilters: Boolean
) {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L
}