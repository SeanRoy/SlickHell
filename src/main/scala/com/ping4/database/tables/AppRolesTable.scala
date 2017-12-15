package com.ping4.database.tables

import java.sql.Timestamp
import java.util.UUID

/**
  * @author Sean N. Roy
  */
trait AppRolesTable extends CustomersTable
                    with UsersTable {
  import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._

  case class AppRole(id: UUID, name: Option[String] = None, createdAt: Option[Timestamp] = None,
                     updatedAt: Option[Timestamp] = None, createdBy: Option[Int] = None, updatedBy: Option[Int] = None,
                     customerId: Option[UUID] = None)


  class AppRoles(tag: Tag) extends Table[AppRole](tag, "AppRoles") {
    def * = (id, name, createdAt, updatedAt, createdBy, updatedBy, customerId) <> (AppRole.tupled, AppRole.unapply)

    /** Database column id DBType(uuid), PrimaryKey, Length(2147483647,false) */
    val id: Rep[UUID] = column[UUID]("id", O.PrimaryKey, O.Length(2147483647,varying=false))
    /** Database column name DBType(text), Length(2147483647,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(2147483647,varying=true), O.Default(None))
    /** Database column created_at DBType(timestamp), Default(None) */
    val createdAt: Rep[Option[Timestamp]] = column[Option[Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at DBType(timestamp), Default(None) */
    val updatedAt: Rep[Option[Timestamp]] = column[Option[Timestamp]]("updated_at", O.Default(None))
    /** Database column created_by DBType(int4), Default(None) */
    val createdBy: Rep[Option[Int]] = column[Option[Int]]("created_by", O.Default(None))
    /** Database column updated_by DBType(int4), Default(None) */
    val updatedBy: Rep[Option[Int]] = column[Option[Int]]("updated_by", O.Default(None))
    /** Database column customer_id DBType(uuid), Length(2147483647,false), Default(None) */
    val customerId: Rep[Option[UUID]] = column[Option[UUID]]("customer_id", O.Length(2147483647,varying=false), O.Default(None))

    /** Foreign key referencing Customers (database name app_roles_customer_id_fkey) */
    lazy val customersFk = foreignKey("app_roles_customer_id_fkey", customerId, customers)(_.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name app_roles_created_by_fkey) */
    lazy val usersFk2 = foreignKey("app_roles_created_by_fkey", createdBy, users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name app_roles_updated_by_fkey) */
    lazy val usersFk3 = foreignKey("app_roles_updated_by_fkey", updatedBy, users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (name) (database name app_roles_name_key) */
    val index1 = index("app_roles_name_key", name, unique=true)
  }

  val appRoles = TableQuery[AppRoles]
}
