package com.ping4.models

import java.util.UUID

import com.ping4.AppConfig
import com.ping4.database.tables.Customer
import com.ping4.services.ComponentRegistry
import com.ping4.services.providers.postgres.PostgresInit
import com.ping4.utils.TimeUtils
import com.typesafe.config.ConfigFactory
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.ExecutionContext.Implicits.global

class CustomersTest extends AsyncFlatSpec
  with BeforeAndAfterAll
  with ComponentRegistry {

  val c1 = new Customer(1, "TestCustomer_1", "abcdef", "test1", "test1@mailinator.org", None, TimeUtils.now,TimeUtils.now,
    false, None, UUID.randomUUID, None, None, true, None, 1)
  val c2 = new Customer(2, "TestCustomer_2", "ghijkl", "test2", "test2@mailinator.org", None, TimeUtils.now,TimeUtils.now,
    false, None, UUID.randomUUID, None, None, true, None, 2)
  val c3 = new Customer(3, "TestCustomer_3", "mnopqr", "test3", "test3@mailinator.org", None, TimeUtils.now,TimeUtils.now,
    false, None, UUID.randomUUID, None, None, true, None, 3)

  override def beforeAll() = {
    super.beforeAll()
    Option(ConfigFactory.load("v3")) foreach { config => {
      AppConfig.setAppConfig(new AppConfig(config))
      ComponentRegistry.init(config)
    } }
  }

  override def afterAll() = {
    // Compose the delete queries together so we can do them in order and chain
    // database shutdown to their completion.
    customersRepo.db.run(customersRepo.hardDeleteQuery(c1.id)
      .andThen(customersRepo.hardDeleteQuery(c2.id))
      .andThen(customersRepo.hardDeleteQuery(c3.id))).onComplete { result =>
     PostgresInit.uninitializePostgres()
     super.afterAll()
   }
  }

  "CustomersRepository" should "successfully create a Customer" in {
    // This map maps the Future[Customer] to a Future[Assert] which is necessary when
    // testing asynchronous calls.
    customersRepo.save(c1).map { x =>
      assert(x.name === c1.name)
    }
  }

  it should "soft delete a customer correctly" in {
    customersRepo.softDelete(c1.id).flatMap { x =>
      customersRepo.getById(c1.id).map { y =>
        assert(y.get.deleted === true)
      }
    }
  }

}
