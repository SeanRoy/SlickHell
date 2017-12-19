package com.ping4.database.repositories.implementations


import com.ping4.database.drivers.PostgresDriverWithPostGisSupport.api._
import com.ping4.database.repositories.BaseRepository
import com.ping4.database.tables.{Customer, Customers}

class CustomersRepository extends BaseRepository[Customers, Customer](TableQuery[Customers])