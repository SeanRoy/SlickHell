package com.ping4.services

import com.ping4.database.repositories.BaseRepository
import com.ping4.database.repositories.implementations.CustomersRepository
import com.ping4.services.providers.postgres.PostgresInit
import com.typesafe.config.Config

/**
  * Specific implementations of services go here. This is the heart of dependency injection.  All endpoints, models
  * and business objects may only access services through this interface. This is accomplished via the Cake Pattern
  * which effectively hides all implementation details from the rest of the product.
  */
trait ComponentRegistry
{
  // Repositories
  lazy val customersRepo = new CustomersRepository

  // Services
}

object ComponentRegistry extends ComponentRegistry {
  def init(config: Config) = {
    PostgresInit.initializePostgres()
  }
}
