package com.ping4.database.tables

import java.sql.Timestamp
import java.util.UUID

/**
  * @author Sean N. Roy
  * @param oldId
  * @param name
  * @param apiKey
  * @param contactName
  * @param contactEmail
  * @param contactPhone
  * @param createdAt
  * @param updatedAt
  * @param deleted
  * @param deletedTime
  * @param id
  * @param settings
  * @param deletedBy
  * @param enabled
  * @param emailDomains
  * @param pin
  */
case class Customer(oldId: Int, name: String, apiKey: String, contactName: String, contactEmail: String,
                    contactPhone: Option[String] = None, createdAt: Timestamp, updatedAt: Timestamp,
                    deleted: Boolean = false, var deletedTime: Option[Timestamp] = None, id: UUID,
                    settings: Option[Map[String, String]], var deletedBy: Option[Int] = None, enabled: Boolean = true,
                    emailDomains: Option[List[String]] = None,
                    pin: Int = 1 /* TODO UNCOMMENT! com.ping4.models.Customers.generateCustomerPin*/) extends BaseEntity {

}


/**
  * @author Sean N. Roy
  */
  import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._

  class Customers(tag: Tag) extends BaseTable[Customer](tag, None, "customers") {
    def * = (oldId, name, apiKey, contactName, contactEmail, contactPhone, createdAt, updatedAt, deleted, deletedTime, id, settings, deletedBy, enabled, emailDomains, pin) <> (Customer.tupled, Customer.unapply)

    /** Database column old_id DBType(serial), AutoInc */
    val oldId: Rep[Int] = column[Int]("old_id", O.AutoInc)
    /** Database column name DBType(text), Length(2147483647,true) */
    val name: Rep[String] = column[String]("name", O.Length(2147483647,varying=true))
    /** Database column api_key DBType(text), Length(2147483647,true) */
    val apiKey: Rep[String] = column[String]("api_key", O.Length(2147483647,varying=true))
    /** Database column contact_name DBType(text), Length(2147483647,true) */
    val contactName: Rep[String] = column[String]("contact_name", O.Length(2147483647,varying=true))
    /** Database column contact_email DBType(text), Length(2147483647,true) */
    val contactEmail: Rep[String] = column[String]("contact_email", O.Length(2147483647,varying=true))
    /** Database column contact_phone DBType(text), Length(2147483647,true), Default(None) */
    val contactPhone: Rep[Option[String]] = column[Option[String]]("contact_phone", O.Length(2147483647,varying=true), O.Default(None))
    /** Database column created_at DBType(timestamp) */
    val createdAt: Rep[Timestamp] = column[Timestamp]("created_at")
    /** Database column updated_at DBType(timestamp) */
    val updatedAt: Rep[Timestamp] = column[Timestamp]("updated_at")
    /** Database column deleted DBType(bool), Default(false) */
    override val deleted: Rep[Boolean] = column[Boolean]("deleted", O.Default(false))
    /** Database column deleted_time DBType(timestamp), Default(None) */
    val deletedTime: Rep[Option[Timestamp]] = column[Option[Timestamp]]("deleted_time", O.Default(None))
    /** Database column id DBType(uuid), PrimaryKey, Length(2147483647,false) */
    override val id: Rep[UUID] = column[UUID]("id", O.PrimaryKey, O.Length(2147483647,varying=false))
    /** Database column settings DBType(hstore), Length(2147483647,false) */
    val settings: Rep[Option[Map[String, String]]] = column[Option[Map[String, String]]]("settings", O.Length(2147483647,varying=false))
    /** Database column deleted_by DBType(int4), Default(None) */
    val deletedBy: Rep[Option[Int]] = column[Option[Int]]("deleted_by", O.Default(None))
    /** Database column enabled DBType(bool), Default(true) */
    val enabled: Rep[Boolean] = column[Boolean]("enabled", O.Default(true))
    /** Database column email_domain DBType(text), Length(2147483647,true), Default(None) */
    val emailDomains: Rep[Option[List[String]]] = column[Option[List[String]]]("email_domains", O.Length(2147483647,varying=false), O.Default(None))
    val pin: Rep[Int] = column[Int]("pin")

    /** Foreign key referencing Users (database name customers_deleted_by_fkey) */
    lazy val usersFk = foreignKey("customers_deleted_by_fkey", deletedBy, TableQuery[Users])(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (apiKey) (database name customers_api_key_key) */
    val index1 = index("customers_api_key_key", apiKey, unique=true)
  }

