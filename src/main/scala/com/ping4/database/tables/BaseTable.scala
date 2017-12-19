package com.ping4.database.tables

import java.util.UUID

import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._

import scala.reflect._

trait BaseEntity {
  val id: UUID
  val deleted: Boolean
}

abstract class BaseTable[E: ClassTag](tag: Tag, schemaName: Option[String], tableName: String)
  extends Table[E](tag, schemaName, tableName) {
  val classOfEntity = classTag[E].runtimeClass
  val id: Rep[UUID] = column[UUID]("id", O.PrimaryKey, O.Length(2147483647,varying=false))
  val deleted: Rep[Boolean] = column[Boolean]("deleted", O.Default(false))
}
