package org.dev.otte.common.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

private const val DAILY_CALL_LIMIT_COUNT = 3

@Entity
@Table(name = "ip_limit")
class IpLimit(
    @Column
    val ip: String
) : BaseEntity() {

    @Column
    var totalCallCount: Int = 0
        protected set

    @Column
    var remain: Int = DAILY_CALL_LIMIT_COUNT
        protected set

    fun call() {
        remain--
    }

    fun totalCountUp() {
        totalCallCount++
    }

    fun isRunOut(): Boolean {
        return remain < 1
    }

    fun reset() {
        remain = DAILY_CALL_LIMIT_COUNT
    }
}