package com.ping4.database.repositories.implementations

import java.util.UUID

import com.ping4.services.providers.postgres.PostgresInit
import slick.lifted.{CanBeQueryCondition, Rep}
import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._
import com.ping4.database.tables.{User, Users}

/**
  * Users requires its own special concrete repository because unlike every other table
  * in the data model, it has an Int for an id instead of an  UUID.
  * @author Sean N. Roy
  */
class UsersRepository {
  val query: com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api.TableQuery[Users] = TableQuery[Users]
  val db = PostgresInit.db

  def getById(id: Int) = {
    query.filter(_.id === id).filter(_.deleted === false)
  }

  def getAllQuery = {
    query.filter(_.deleted === false)
  }

  def filterQuery[C <: Rep[_]](expr: Users => C)(implicit wt: CanBeQueryCondition[C]) = {
    query.filter(expr).filter(_.deleted === false)
  }

  def saveQuery(row: User) = {
    (query returning query) += row
  }

  def hardDeleteQuery(id: Int) = {
    query.filter(_.id === id ).delete
  }
}
