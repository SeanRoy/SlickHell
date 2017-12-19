package com.ping4.models

import com.ping4.database.tables.User
import com.ping4.services.providers.postgres.PostgresInit
import org.scalatest.{BeforeAndAfterEach, FlatSpec}

class UsersTest extends FlatSpec
  with BeforeAndAfterEach {

  override def beforeEach() = {
  }

  "UserRepository" should "successfully create a User" in {

  }

}
