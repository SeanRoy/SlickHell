package com.ping4.database.repositories

import java.util.UUID

import com.ping4.database.tables.{BaseEntity, BaseTable}
import slick.lifted.{CanBeQueryCondition, Rep, TableQuery}
import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._

/**
  * @author Sean N. Roy
  * @tparam T
  * @tparam E
  */
trait BaseRepositoryQuery[T <: BaseTable[E], E <: BaseEntity] {

  val query: com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api.type#TableQuery[T]

  def getByIdQuery(id: UUID) = {
    query.filter(_.id === id)
  }

  def getAllQuery = {
    query.filter(_.deleted === false)
  }

  def getAllUnfilteredQuery = {
    query
  }

  def filterQuery[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]) = {
    query.filter(expr).filter(_.deleted === false)
  }

  def saveQuery(row: E) = {
    (query returning query) += row
  }

  def hardDeleteQuery(id: UUID) = {
    query.filter(_.id === id ).delete
  }

  def softDeleteQuery(id: UUID) = {
    getByIdQuery(id).map(_.deleted).update(true)
  }
}
