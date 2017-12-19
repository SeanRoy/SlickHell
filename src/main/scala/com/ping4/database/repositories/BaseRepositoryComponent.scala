package com.ping4.database.repositories

import java.util.UUID

import com.ping4.database.tables.{BaseEntity, BaseTable}
import slick.lifted.{CanBeQueryCondition, Rep}

import scala.concurrent.Future

trait BaseRepositoryComponent [T <: BaseTable[E], E <: BaseEntity] {
  def getById(id: UUID) : Future[Option[E]]
  def getAll : Future[Seq[E]]
  def getAllUnfiltered : Future[Seq[E]]
  def filter[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]): Future[Seq[E]]
  def save(row: E) : Future[E]
  def hardDelete(id: UUID) : Future [Int]
  def softDelete(id: UUID) : Future [Int]
}
