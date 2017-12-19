package com.ping4.database.repositories

import java.util.UUID

import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._
import com.ping4.database.tables.{BaseEntity, BaseTable}
import com.ping4.services.providers.postgres.PostgresInit
import slick.lifted.CanBeQueryCondition

import scala.concurrent.Future
import scala.reflect._

abstract class BaseRepository[T <: BaseTable[E], E <: BaseEntity : ClassTag](clazz: TableQuery[T])
  extends BaseRepositoryQuery[T, E]
    with BaseRepositoryComponent[T,E] {

  // TODO This is lame, fix it. The db object cannot be publicly available
  // the way it is now.
  val db = PostgresInit.db
  val clazzTable: TableQuery[T] = clazz
  lazy val clazzEntity = classTag[E].runtimeClass
  val query: com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api.type#TableQuery[T] = clazz

  def getAll: Future[Seq[E]] = {
    db.run(getAllQuery.result)
  }

  def getAllUnfiltered: Future[Seq[E]] = {
    db.run(getAllUnfilteredQuery.result)
  }

  def getById(id: UUID): Future[Option[E]] = {
    db.run(getByIdQuery(id).result.headOption)
  }

  def filter[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]) = {
    db.run(filterQuery(expr).result)
  }

  def save(row: E) = {
    db.run(saveQuery(row))
  }

  def hardDelete(id: UUID) : Future[Int] = {
    db.run(hardDeleteQuery(id))
  }

  def softDelete(id: UUID) : Future[Int] = {
    db.run(softDeleteQuery(id))
  }
}
