package com.ping4.services.providers.postgres

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.ping4.AppConfig
import slick.jdbc.JdbcBackend.Database

object PostgresInit {

  val pool = new ComboPooledDataSource()
  var db: Database = _

  def initializePostgres() = {

    val x = AppConfig.postgresConnection
    pool.setDriverClass("org.postgresql.Driver")
    pool.setJdbcUrl(AppConfig.postgresConnection)
    pool.setUser(AppConfig.postgresUsername)
    pool.setPassword(AppConfig.postgresPassword)

    pool.setMinPoolSize(AppConfig.postgresMinPoolSize)
    pool.setMaxPoolSize(AppConfig.postgresMaxPoolSize)
    pool.setAcquireIncrement(AppConfig.postgresAcquireIncrement)

    // connections = ((core_count * 2) + effective_spindle_count)

    db = Database.forDataSource(pool, Option(20))

  }

  def uninitializePostgres() = {
    pool.close()
    db.close()
  }
}