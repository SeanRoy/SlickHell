package com.ping4.database.tables

import java.sql.Timestamp
import java.util.UUID
import slick.lifted.Tag

case class User(id: Option[Int], customerId: UUID, var name: String, email: String, encryptedPassword: String,
                var rolesMask: Option[Int], createdAt: Timestamp, createdBy: Option[Int],
                var updatedAt: Timestamp, var updatedBy: Option[Int], var deleted: Option[Boolean],
                var deletedTime: Option[Timestamp], var deletedBy: Option[Int], apiKey: String,
                var validationKey: Option[String],
                var validated: Boolean,
                var validatedAt: Option[Timestamp],
                var validationSentAt: Option[Timestamp],
                var enabled: Option[Boolean] = Option(true))

/**
  * @author Sean N. Roy
  */
  import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._

  class Users(tag: Tag) extends Table[User](tag, None, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def customerId = column[UUID]("customer_id")
    def name = column[String]("name")
    def email = column[String]("email")
    def encryptedPassword = column[String]("encrypted_password")
    def rolesMask = column[Option[Int]]("roles_mask", O.Default(None))
    def createdAt = column[Timestamp]("created_at")
    def createdBy = column[Option[Int]]("created_by", O.Default(None))
    def updatedAt = column[Timestamp]("updated_at")
    def updatedBy = column[Option[Int]]("updated_by", O.Default(None))
    def deleted = column[Option[Boolean]]("deleted", O.Default(Option(false)))
    def deletedTime = column[Option[Timestamp]]("deleted_time", O.Default(None))
    def deletedBy = column[Option[Int]]("deleted_by", O.Default(None))
    def apiKey = column[String]("api_key")
    def validationKey: Rep[Option[String]] = column[Option[String]]("validation_key", O.Length(2147483647,varying=true), O.Default(None))
    def validated: Rep[Boolean] = column[Boolean]("validated", O.Default(false))
    def validatedAt: Rep[Option[Timestamp]] = column[Option[Timestamp]]("validated_at", O.Default(None))
    def validationSentAt: Rep[Option[Timestamp]] = column[Option[Timestamp]]("validation_sent_at", O.Default(None))
    def enabled = column[Option[Boolean]]("enabled", O.Default(Option(false)))

    def * = (id.?, customerId, name, email, encryptedPassword, rolesMask, createdAt, createdBy, updatedAt, updatedBy,
      deleted, deletedTime, deletedBy, apiKey, validationKey, validated, validatedAt, validationSentAt, enabled) <> (User.tupled, User.unapply)
  }

